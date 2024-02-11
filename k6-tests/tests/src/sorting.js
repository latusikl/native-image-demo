import http from 'k6/http';
import {check} from 'k6';
import {SharedArray} from 'k6/data';
import {randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import { URL } from 'https://jslib.k6.io/url/1.0.0/index.js';
import {getBaseUrl, sortingRequestBody} from "./utils/utils.js";
import {ApiPaths, DEFAULT_PARAMS, HttpStatus} from "./utils/constants.js";

export const options = {
    stages:[
        {duration: '20s', target: 100},
        {duration: '40s', target: 100},
        {duration: '20s', target: 0}
    ]
}

const totalDataExamples = 1000
const dataSetArray = new SharedArray('Sorting Test Data Set', () => JSON.parse(open('./sorting-data.json')))

export function setup() {
    return {
        sortersPath: `${getBaseUrl()}${ApiPaths.SORTERS}`
    }
}

export default function (setupData) {
    //Get single dataset
    const dataSetNumber = randomIntBetween(0, totalDataExamples - 1)
    const dataSet = dataSetArray[dataSetNumber]

    //Add direction param
    const url = new URL(setupData.sortersPath)
    url.searchParams.append('direction', dataSet.directionRandom)

    //Execute request
    const sortingRequestResponse = http.post(url.toString(), sortingRequestBody(dataSet), DEFAULT_PARAMS)

    //Verify result
    check(sortingRequestResponse, {
        'Has status 200': res => res.status === HttpStatus.OK,
        'Data are sorted': res => JSON.stringify(JSON.parse(res.body).sortedData) === JSON.stringify(dataSet.expectedResult)
    })

}
