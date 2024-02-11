import {ApiPaths, defaultParamsWithTag, HTTP_HEADER, HttpStatus} from "./utils/constants.js";
import {
    bookStockDto,
    borrowDto,
    extractUUIDFromResponseHeader,
    getBaseUrl,
    randomisedItemDto,
    randomisedMemberDto
} from "./utils/utils.js";
import http from 'k6/http';
import {check, group, sleep} from 'k6';
import {randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
    systemTags: ['status','group','error','error_code'],
    stages: [
        {duration: '15s', target: 15},
        {duration: '40s', target: 15},
        {duration: '15s', target: 0}
    ]
}


export function setup() {
    return {
        itemsPath: `${getBaseUrl()}${ApiPaths.ITEMS}`,
        stocksPath: `${getBaseUrl()}${ApiPaths.ITEM_STOCK}`,
        membersPath: `${getBaseUrl()}${ApiPaths.MEMBERS}`,
        borrowingsPath: `${getBaseUrl()}${ApiPaths.BORROWINGS}`
    }
}

export default function (setupData) {

    let bookId;
    let stockId;
    let memberId;
    let borrowingId;

    group('Create library data', () => {
        const createBookResponse = http.post(setupData.itemsPath, randomisedItemDto(), defaultParamsWithTag("create_book"))
        checkPostResponse(createBookResponse)
        bookId = extractUUIDFromResponseHeader(createBookResponse)
        // sleepBetween1to3sec()

        console.debug("STEP 2: Creat stock for book")
        const createStockResponse = http.post(setupData.stocksPath, bookStockDto(bookId), defaultParamsWithTag("create_stock"))
        checkPostResponse(createStockResponse)
        stockId = extractUUIDFromResponseHeader(createStockResponse)
        // sleepBetween1to3sec()

        console.debug("STEP 3: Create new library member")
        const createMemberResponse = http.post(setupData.membersPath, randomisedMemberDto(), defaultParamsWithTag("create_member"))
        checkPostResponse(createMemberResponse)
        memberId = extractUUIDFromResponseHeader(createMemberResponse)
        // sleepBetween1to3sec()

    })

    group('Borrow and return book', () => {
        console.debug("STEP 4: Check book stock")
        const bookStockResponse = http.get(`${setupData.stocksPath}/${stockId}`, defaultParamsWithTag("check_stock"))
        checkBookStock(bookStockResponse, 2, 0)
        // sleepBetween1to3sec()

        console.debug("STEP 5: Borrow book")
        const borrowingResponse = http.post(setupData.borrowingsPath, borrowDto(memberId, bookId), defaultParamsWithTag("borrow_book"))
        checkPostResponse(borrowingResponse)
        borrowingId = extractUUIDFromResponseHeader(borrowingResponse)
        // sleepBetween1to3sec()

        console.debug("STEP 6: Check book stock")
        const bookStockResponseAfterBorrow = http.get(`${setupData.stocksPath}/${stockId}`, defaultParamsWithTag("check_stock"))
        checkBookStock(bookStockResponseAfterBorrow, 2, 1)
        // sleepBetween1to3sec()

        console.debug("STEP 7: Return Book")
        const borrowingRemovalResponse = http.del(`${setupData.borrowingsPath}/${borrowingId}`, null, defaultParamsWithTag("return_book"))
        checkDeleteResponse(borrowingRemovalResponse)
        // sleepBetween1to3sec()

        console.debug("STEP 8: Check book stock")
        const bookStockResponseAfterReturn = http.get(`${setupData.stocksPath}/${stockId}`, defaultParamsWithTag("check_stock"))
        checkBookStock(bookStockResponseAfterReturn, 2, 0)
        // sleepBetween1to3sec()
    })

    group('Cleanup library data', () => {
        console.debug("STEP 9: Remove book stock")
        const bookStockRemovalResponse = http.del(`${setupData.borrowingsPath}/${borrowingId}`, null, defaultParamsWithTag("remove_stock"))
        checkDeleteResponse(bookStockRemovalResponse)
        // sleepBetween1to3sec()

        console.debug("STEP 10: Remove book")
        const bookRemovalResponse = http.del(`${setupData.itemsPath}/${bookId}`, null, defaultParamsWithTag("remove_book"))
        checkDeleteResponse(bookRemovalResponse)
        // sleepBetween1to3sec()

        console.debug("STEP 11: Remove Member")
        const memberRemovalResponse = http.del(`${setupData.membersPath}/${memberId}`, null, defaultParamsWithTag("remove_member"))
        checkDeleteResponse(memberRemovalResponse)
        // sleepBetween1to3sec()

        console.debug("STEP 12: Check member doesn't exist")
        const memberFetchResponse = http.get(`${setupData.membersPath}/${memberId}`, defaultParamsWithTag("check_member"))
        check(memberFetchResponse, {
            'Has status 404': res => res.status === HttpStatus.NOT_FOUND
        }, {
            "test_tag": "member_not_found_check"
        })

    })

}

function checkPostResponse(response) {
    check(response, {
            'Has status 201': res => res.status === HttpStatus.CREATED,
            'Has location header with id': res => res.headers[HTTP_HEADER.LOCATION].length !== 0
        },
        {
            test_tag: 'post_check'
        })
}

function checkBookStock(bookResponse, itemCount, borrowedCount) {
    check(bookResponse, {
            'Has status 200': res => res.status === HttpStatus.OK,
            'Book has expected item count': res => JSON.parse(res.body).itemCount === itemCount,
            'Book has expected count of borrowed items': res => JSON.parse(res.body).borrowedItemsCount === borrowedCount
        },
        {
            test_tag: 'stock_check'
        })
}

function checkDeleteResponse(response) {
    check(response, {
        'Has status 204': res => res.status === HttpStatus.NO_CONTENT,
    }, {
        test_tag: 'delete_check'
    })
}

// eslint-disable-next-line no-unused-vars
function sleepBetween1to3sec() {
    sleep(randomIntBetween(1, 3));
}

