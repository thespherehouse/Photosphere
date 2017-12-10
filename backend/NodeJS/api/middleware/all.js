'use strict';

const Response = require('../response');

module.exports = function (req, res, next) {

    if (!req.headers['device-id'] || !req.headers['device-type']) {
        Response.sendError(res, Response.Errors.Header);
    } else {
        req.deviceId = req.headers['device-id'];
        req.deviceType = req.headers['device-type'];
        next();
    }

};
