import { Session } from '../models'
import { Response, Errors } from '../helper'

export default function (req, res, next) {

    new Promise(function (resolve, reject) {

        if (!req.headers['token']) {
            return reject(Response.Errors.Incomplete);
        }

        resolve(req.headers['token']);

    }).then(function (token) {

        return new Promise(function (resolve, reject) {

            Session.getUserByToken(
                {
                    token,
                    deviceId: req.deviceId
                },

                function (user) {

                    if (!user)
                        return reject(Response.Errors.Session);

                    resolve(user);
                })
        });

    }).then(function (user) {
        req.user = user;
        next();
    }).catch(function (errorCode) {
        Response.sendError(res, errorCode);
    })

};
