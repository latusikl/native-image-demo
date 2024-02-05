import {ApiPaths, HTTP_HEADER, HttpStatus} from "../api/constants.js";
import {
    bookStockDto, borrowDto,
    extractUUIDFromResponseHeader,
    getBaseUrl,
    randomisedItemDto,
    randomisedMemberDto
} from "../api/utils.js";
import {DEFAULT_PARAMS} from "../api/constants.js";
import http from 'k6/http';
import {check, sleep} from 'k6';
import {randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';


export const options = {
    vus: 1,
    // duration: '10s',
    iterations: 1,
    // scenarios: {
    //     my_api_scenario: {
    //         // arbitrary scenario name
    //         executor: 'ramping-vus',
    //         startVUs: 0,
    //         stages: [
    //             { duration: '5s', target: 100 },
    //             { duration: '5s', target: 0 },
    //         ],
    //         gracefulRampDown: '10s',
    //         env: { MYVAR: 'example' },
    //         tags: { my_tag: 'example' },
    //     },
    // },
}


export function setup(){
    return {
        itemsPath: `${getBaseUrl()}${ApiPaths.ITEMS}`,
        stocksPath: `${getBaseUrl()}${ApiPaths.ITEM_STOCK}`,
        membersPath: `${getBaseUrl()}${ApiPaths.MEMBERS}`,
        borrowingsPath: `${getBaseUrl()}${ApiPaths.BORROWINGS}`
    }
}
export default function (setupData) {

    console.debug("STEP 1: Create book");
    const createBookResponse = http.post(setupData.itemsPath, randomisedItemDto(), DEFAULT_PARAMS)
    checkPostResponse(createBookResponse)
    const bookId = extractUUIDFromResponseHeader(createBookResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 2: Creat stock for book")
    const createStockResponse = http.post(setupData.stocksPath, bookStockDto(bookId), DEFAULT_PARAMS)
    checkPostResponse(createStockResponse)
    const stockId = extractUUIDFromResponseHeader(createStockResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 3: Create new library member")
    const createMemberResponse = http.post(setupData.membersPath, randomisedMemberDto(), DEFAULT_PARAMS)
    checkPostResponse(createMemberResponse)
    const memberId = extractUUIDFromResponseHeader(createMemberResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 4: Check book stock")
    const bookStockResponse = http.get(`${setupData.stocksPath}/${stockId}`, DEFAULT_PARAMS)
    checkBookStock(bookStockResponse,2,0)
    // sleepBetween1to3sec()

    console.debug("STEP 5: Borrow book")
    const borrowingResponse = http.post(setupData.borrowingsPath,borrowDto(memberId,bookId),DEFAULT_PARAMS)
    checkPostResponse(borrowingResponse)
    const borrowingId = extractUUIDFromResponseHeader(borrowingResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 6: Check book stock")
    const bookStockResponseAfterBorrow = http.get(`${setupData.stocksPath}/${stockId}`, DEFAULT_PARAMS)
    checkBookStock(bookStockResponseAfterBorrow,2,1)
    // sleepBetween1to3sec()

    console.debug("STEP 7: Return Book")
    const borrowingRemovalResponse = http.del(`${setupData.borrowingsPath}/${borrowingId}`,null,DEFAULT_PARAMS)
    checkDeleteResponse(borrowingRemovalResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 8: Check book stock")
    const bookStockResponseAfterReturn = http.get(`${setupData.stocksPath}/${stockId}`, DEFAULT_PARAMS)
    checkBookStock(bookStockResponseAfterReturn,2,0)
    // sleepBetween1to3sec()

    console.debug("STEP 9: Remove book stock")
    const bookStockRemovalResponse = http.del(`${setupData.borrowingsPath}/${borrowingId}`,null,DEFAULT_PARAMS)
    checkDeleteResponse(bookStockRemovalResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 10: Remove book")
    const bookRemovalResponse = http.del(`${setupData.itemsPath}/${bookId}`,null,DEFAULT_PARAMS)
    checkDeleteResponse(bookRemovalResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 11: Remove Member")
    const memberRemovalResponse = http.del(`${setupData.membersPath}/${memberId}`,null, DEFAULT_PARAMS)
    checkDeleteResponse(memberRemovalResponse)
    // sleepBetween1to3sec()

    console.debug("STEP 12: Check member does exist")
    const memberFetchResponse = http.get(`${setupData.membersPath}/${memberId}`,DEFAULT_PARAMS)
    check(memberFetchResponse,{
        'Has status 404': res => res.status === HttpStatus.NOT_FOUND
    })
}

function checkPostResponse(response) {
    check(response, {
        'Has status 201': res => res.status === HttpStatus.CREATED,
        'Has location header with id': res => res.headers[HTTP_HEADER.LOCATION].length !== 0
    })
}

function checkBookStock(bookResponse, itemCount, borrowedCount) {
    check(bookResponse, {
        'Has status 200': res => res.status === HttpStatus.OK,
        'Book has expected item count': res => JSON.parse(res.body).itemCount === itemCount,
        'Book has expected count of borrowed items': res => JSON.parse(res.body).borrowedItemsCount === borrowedCount
    })
}

function checkDeleteResponse(response){
    check(response, {
        'Has status 204': res => res.status === HttpStatus.NO_CONTENT,
    })
}

// eslint-disable-next-line no-unused-vars
function sleepBetween1to3sec() {
    sleep(randomIntBetween(1, 3));
}

