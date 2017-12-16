import { User, Session } from '../../models'
import { Response, Errors, Utils } from '../../helper'

export function checkEmail() {

    return (req, res) => {
        User.getUserByEmail(req.params.email, function (err, user) {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!user)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res, user.toObject())

        })
    }

}

export function register() {

    return (req, res) => {
        new Promise((resolve, reject) => {

            if (!req.body.name || !req.body.email || !req.body.password)
                return reject(Errors.Incomplete)

            resolve({ name: req.body.name, email: req.body.email, password: req.body.password })

        }).then(function (obj) {

            return new Promise((resolve, reject) => {

                User.getUserByEmail(obj.email, function (err, user) {

                    if (err)
                        return reject(Errors.Internal)

                    if (user)
                        return reject(Errors.EmailTaken)

                    resolve(obj)

                })

            })

        }).then(function (obj) {

            return new Promise((resolve, reject) => {

                User.createUser(obj.name, obj.email, obj.password, function (err, user) {

                    if (err || !user) {
                        if (err)
                            console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    resolve(user)
                })

            })

        }).then(function (user) {

            return new Promise((resolve, reject) => {

                Session.createSession(user._id, req.deviceId,
                    function (err, session) {

                        if (err || !session)
                            return reject(Errors.Internal)

                        resolve({ user, session })

                    })

            })

        }).then(function (obj) {
            Response.sendWithToken(res, obj.session.toObject().token, obj.user.toObject())
        }).catch(function (errorCode) {
            Response.sendError(res, errorCode)
        })
    }
}

export function registerSocial() {

    return (req, res) => {
        new Promise((resolve, reject) => {

            if (!req.body.name || !req.body.email || !req.body.socialId || !req.body.loginType)
                return reject(Errors.Incomplete)

            resolve({ name: req.body.name, email: req.body.email, socialId: req.body.socialId, loginType: req.body.loginType })

        }).then(function (obj) {

            return new Promise((resolve, reject) => {

                User.createSocialUser(obj.name, obj.email, obj.socialId, obj.loginType, function (err, user) {

                    if (err || !user) {
                        if (err)
                            console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    resolve(user)
                })

            })

        }).then(function (user) {

            return new Promise((resolve, reject) => {

                Session.createSession(user._id, req.deviceId,
                    function (err, session) {

                        if (err || !session)
                            return reject(Errors.Internal)

                        resolve({ user, session })

                    })

            })

        }).then(function (obj) {
            Response.sendWithToken(res, obj.session.toObject().token, obj.user.toObject())
        }).catch(function (errorCode) {
            Response.sendError(res, errorCode)
        })
    }

}

export function login() {

    return (req, res) => {
        new Promise((resolve, reject) => {

            if (!req.body.email || !req.body.password)
                return reject(Errors.Incomplete)

            resolve({ email: req.body.email, password: req.body.password })

        }).then((obj) => {

            return new Promise((resolve, reject) => {

                User.getUserByEmail(obj.email, function (err, user) {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    if (!user)
                        return reject(Errors.EmailUnregistered)

                    resolve({ user, password: obj.password })

                })

            })

        }).then(function (obj) {

            return new Promise((resolve, reject) => {

                obj.user.matchPasswordSync(obj.password, function (result) {

                    if (!result) {
                        console.log('Password mismatch')
                        return reject(Errors.Password)
                    }

                    resolve(obj.user)

                })

            })

        }).then(function (user) {

            return new Promise((resolve, reject) => {

                Session.createSession(user._id, req.deviceId, function (err, session) {

                    if (err || !session) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    console.log(session)

                    resolve({ user, session })

                })

            })

        }).then(function (obj) {

            return new Promise((resolve, reject) => {
                Response.sendWithToken(res, obj.session.token, obj.user.toObject())
                resolve()
            })

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode)
        })
    }

}

export function forgotPassword() {

    return (req, res) => {
        new Promise((resolve, reject) => {

            if (!req.body.email)
                return reject(Errors.Incomplete)

            resolve(req.body.email)

        }).then(function (email) {

            return new Promise((resolve, reject) => {

                User.findOneAndUpdate({ email: email }, {
                    otp: Utils.genOtp()
                }, { 'new': true }, function (err, user) {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    if (!user)
                        return reject(Errors.NotFound)

                    Utils.emailOtp(user.email, user.otp)

                    Response.send(res)
                    resolve()

                })

            })

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode)
        })
    }

}

export function checkOtp() {

    return (req, res) => {
        new Promise((resolve, reject) => {

            if (!req.body.email || !req.body.otp || isNan(req.body.otp))
                return reject(Errors.Incomplete)

            resolve({ email: req.body.email, otp: parseInt(req.body.otp) })

        }).then(function (obj) {

            return new Promise((resolve, reject) => {

                User.findOne({ email: obj.email, otp: obj.otp }, function (err, user) {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    if (!user)
                        return reject(Errors.NotFound)

                    Response.send(res)
                    resolve()

                })

            })

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode)
        })
    }

}

export function resetPassword() {

    return (req, res) => {
        new Promise((resolve, reject) => {

            if (!req.body.email)
                return reject(Errors.Incomplete)

            resolve(req.body.email)

        }).then(function (email) {

            return new Promise((resolve, reject) => {

                User.findOneAndUpdate({ email: req.body.email },
                    {
                        password: req.body.password,
                        otp: 0
                    }, { 'new': true }, function (err, user) {

                        if (err) {
                            console.log(err.message)
                            return reject(Errors.Internal)
                        }

                        if (!user)
                            return reject(Errors.NotFound)

                        Response.send(res)
                        resolve()

                    })

            })

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode)
        })
    }

}

export function silentLogin() {
    return (req, res) => {
        Response.send(res, Errors.None)
    }
}

export function logout() {

    return (req, res) => {
        Session.deleteSession(req.user._id, req.deviceId, (err, session) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!session)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res)

        })
    }

}

export function fcm() {

    return (req, res) => {
        if (!req.body.fcm) {
            Response.sendError(Errors.Incomplete)
        } else {

            User.updateFCM(req.user._id, req.body.fcm, function (err, user) {

                if (err || !user) {
                    if (err)
                        console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                Response.send(res)

            })

        }
    }

}
