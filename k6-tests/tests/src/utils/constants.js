export const DEFAULT_LOCAL_URL = 'http://localhost:8069'

export const ApiPaths = {
    ITEMS: '/api/items',
    ITEM_STOCK: '/api/stocks',
    MEMBERS: '/api/members',
    BORROWINGS: '/api/borrowings',
    SORTERS: '/api/sorters/sort'
}

export const ContentType = {
    APPLICATION_JSON: 'application/json'
}

export const HttpStatus = {
    OK: 200,
    NO_CONTENT: 204,
    CREATED: 201,
    NOT_FOUND: 404
}

export const HTTP_HEADER = {
    LOCATION: 'Location'
}

export const DEFAULT_PARAMS = {
    headers:{
        'Content-Type': ContentType.APPLICATION_JSON,
        'Accept': ContentType.APPLICATION_JSON
    },
    responseType: 'text'
}
