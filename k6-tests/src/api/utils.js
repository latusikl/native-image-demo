import {DEFAULT_LOCAL_URL} from "./constatns.js";

export function getBaseUrl() {
    // eslint-disable-next-line no-undef
    const remoteUrl = __ENV.REMOTE_URL
    return remoteUrl ? remoteUrl : DEFAULT_LOCAL_URL;
}
