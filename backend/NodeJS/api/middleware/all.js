import { Response, Errors } from '../helper'

export default function (req, res, next) {

    if (!req.headers['device-id'] || !req.headers['device-type']) {
        Response.sendError(res, Errors.Header);
    } else {
        req.deviceId = req.headers['device-id'];
        req.deviceType = req.headers['device-type'];
        next();
    }

};
