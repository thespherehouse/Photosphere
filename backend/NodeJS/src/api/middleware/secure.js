import { Session } from '../models'
import { Response, Errors } from '../helper'

export default () => {

    return (req, res, next) => {
        new Promise((resolve, reject) => {
            if (!req.headers['token']) {
                return reject(Errors.Token)
            }
            resolve(req.headers['token'])

        }).then((token) => {

            return new Promise((resolve, reject) => {

                Session.getSession(req.deviceId, token, (err, session) => {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    if (!session)
                        return reject(Errors.Session)

                    resolve(session.user)
                })
            })

        }).then((user) => {
            req.user = user
            next()
        }).catch((errorCode) => {
            Response.sendError(res, errorCode)
        })
    }

}  
