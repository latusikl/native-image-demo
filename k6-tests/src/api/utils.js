import {DEFAULT_LOCAL_URL, HTTP_HEADER} from "./constants.js";
import {uuidv4, randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

export function getBaseUrl() {
    // eslint-disable-next-line no-undef
    const remoteUrl = __ENV.REMOTE_URL
    return remoteUrl ? remoteUrl : DEFAULT_LOCAL_URL;
}

export function extractUUIDFromResponseHeader(response) {
    const splitUrl = response.headers[HTTP_HEADER.LOCATION].split('/');
    return splitUrl[splitUrl.length - 1]
}


//Test Related DTOs
export function randomisedItemDto() {
    return JSON.stringify({
        name: `CleanCode-${uuidv4()}`,
        authorName: `Robert-${uuidv4()}`,
        authorSurname: `Martin-${uuidv4()}`,
        publicationYear: randomIntBetween(1990, 2023)
    });
}

export function bookStockDto(uuid) {
    return JSON.stringify({
        itemId: uuid,
        itemCount: 2
    });
}

export function randomisedMemberDto() {
    return JSON.stringify({
        name: `John-${uuidv4()}`,
        surname: `Doe-${uuidv4()}`,
        email: `john.doe-${uuidv4()}@test.com`,
        idCardKey: `ID-${uuidv4()}`
    })
}

export function borrowDto(memberId, itemId){
    return JSON.stringify({
        memberId: memberId,
        itemId: itemId,
    })
}
