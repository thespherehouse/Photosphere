import { Response, Errors } from '../helper'

export default function () {

    return (req, res, next) => {
        if (!req.headers['device-id']) {
            Response.sendError(res, Errors.Header)
        } else {
            req.deviceId = req.headers['device-id']
            next()
        }
    }

}
