import { Session } from '../models'
import { Response, Errors } from '../helper'

export default function () {

    return (req, res, next) => {
        new Promise(function (resolve, reject) {
            if (!req.headers['token']) {
                return reject(Errors.Token)
            }
            resolve(req.headers['token'])

        }).then(function (token) {

            return new Promise(function (resolve, reject) {

                Session.getSession(req.deviceId, token, function (err, session) {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    if (!session)
                        return reject(Errors.Session)

                    resolve(session.user)
                })
            })

        }).then(function (user) {
            req.user = user
            next()
        }).catch(function (errorCode) {
            Response.sendError(res, errorCode)
        })
    }

}  
