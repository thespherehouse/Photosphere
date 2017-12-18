import { User, Session } from '../models'
import { Response, Errors, Utils } from '../helper'

export const Validator = {

    register() {
        return (req, res, next) => {
            if (!req.body.name || !req.body.email || !req.body.password)
                return Response.sendError(res, Errors.Incomplete)
            next()
        }
    },

    forgotPassword() {
        return (req, res, next) => {
            if (!req.body.email)
                return Response.sendError(res, Errors.NoEmail)
            next()
        }
    },

    checkOtp() {
        return (req, res, next) => {
            if (!req.body.email || !req.body.otp || isNaN(req.body.otp))
                return Response.sendError(res, Errors.Incomplete)
            next()
        }
    },

    resetPassword() {
        return (req, res, next) => {
            if (!req.body.email)
                return Response.sendError(res, Errors.NoEmail)
            next()
        }
    },

    login() {
        return (req, res, next) => {
            if (!req.body.email || !req.body.password)
                return Response.sendError(res, Errors.Incomplete)
            next()
        }
    },

    socialLogin() {
        return (req, res, next) => {
            if (!req.body.name || !req.body.email || !req.body.socialId || !req.body.loginType)
                return Response.sendError(res, Errors.Incomplete)
            if (isNaN(req.body.loginType) || parseInt(req.body.loginType) < 0 || parseInt(req.body.loginType) > 2)
                return Response.sendError(res, Errors.InvalidLoginType)
            next()
        }
    },

    fcm() {
        return (req, res, next) => {
            if (!req.body.fcm) {
                Response.sendError(Errors.NoFCM)
                next()
            }
        }
    }

}

export const Endpoint = {

    checkEmail() {
        return (req, res) => {
            User.getUserByEmail(req.params.email, (err, user) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!user)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, user.toObject())
            })
        }

    },

    register() {
        return (req, res) => {
            new Promise((resolve, reject) => {
                User.getUserByEmail(req.body.email, (err, user) => {
                    if (err)
                        return reject(Errors.Internal)

                    if (user) {
                        if (user.loginType > 0)
                            return reject(Errors.SocialAuth)
                        return reject(Errors.EmailTaken)
                    }

                    resolve()
                })
            }).then(() => {
                return new Promise((resolve, reject) => {
                    User.createUser(req.body.name, req.body.email, req.body.password, (err, user) => {
                        if (err || !user) {
                            if (err)
                                console.log(err.message)
                            return reject(Errors.Internal)
                        }
                        resolve(user)
                    })
                })
            }).then((user) => {
                return new Promise((resolve, reject) => {
                    Session.createSession(user._id, req.deviceId, (err, session) => {
                        if (err || !session) {
                            if (err)
                                console.log(err.message)
                            return reject(Errors.Internal)
                        }

                        resolve(session)
                    })
                })
            }).then((session) => {
                Response.sendWithToken(res, session.token)
            }).catch((errorCode) => {
                Response.sendError(res, errorCode)
            })
        }
    },

    forgotPassword() {
        return (req, res) => {
            User.findOneAndUpdate({ email: email }, { otp: Utils.genOtp() }, { 'new': true }, (err, user) => {
                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!user)
                    return Response.sendError(res, Errors.NotFound)

                Utils.emailOtp(user.email, user.otp)

                Response.send(res)
                resolve()
            })
        }
    },

    checkOtp() {
        return (req, res) => {
            User.findOne({ email: req.body.email, otp: req.body.otp }, (err, user) => {
                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!user)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res)
                resolve()
            })
        }
    },

    resetPassword() {
        return (req, res) => {
            User.findOneAndUpdate({ email: req.body.email },
                {
                    password: req.body.password,
                    otp: 0
                }, {
                    'new': true
                }, (err, user) => {

                    if (err) {
                        console.log(err.message)
                        return Response.sendError(res, Errors.Internal)
                    }

                    if (!user)
                        return Response.sendError(res, Errors.NotFound)

                    Response.send(res)
                })
        }
    },

    login() {
        return (req, res) => {
            new Promise((resolve, reject) => {
                User.getUserByEmail(req.body.email, (err, user) => {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    if (!user)
                        return reject(Errors.EmailUnregistered)

                    resolve({ user, password: obj.password })

                })
            }).then((obj) => {

                return new Promise((resolve, reject) => {
                    obj.user.matchPasswordSync(obj.password, (result) => {

                        if (!result) {
                            console.log('Password mismatch')
                            return reject(Errors.Password)
                        }

                        resolve(obj.user)
                    })

                })

            }).then((user) => {

                return new Promise((resolve, reject) => {
                    Session.createSession(user._id, req.deviceId, (err, session) => {

                        if (err || !session) {
                            console.log(err.message)
                            return reject(Errors.Internal)
                        }

                        console.log(session)

                        resolve(session)
                    })

                })

            }).then((session) => {

                return new Promise((resolve, reject) => {
                    Response.sendWithToken(res, session.token)
                    resolve()
                })

            }).catch((errorCode) => {
                Response.sendError(res, errorCode)
            })
        }

    },

    socialLogin() {
        return (req, res) => {
            new Promise((resolve, reject) => {
                User.getUserByEmail(req.body.email, (err, user) => {
                    if (err)
                        return reject(Errors.Internal)

                    if (user && user.loginType == 0)
                        return reject(Errors.RegularAuth)

                    resolve()
                })

            }).then(() => {
                return new Promise((resolve, reject) => {
                    User.updateSocialUser(req.body.name, req.body.email, req.body.socialId, req.body.loginType, (err, user) => {
                        if (err || !user) {
                            if (err)
                                console.log(err.message)
                            return reject(Errors.Internal)
                        }
                        resolve(user)
                    })
                })

            }).then((user) => {
                return new Promise((resolve, reject) => {
                    Session.createSession(user._id, req.deviceId, (err, session) => {
                        if (err || !session)
                            return reject(Errors.Internal)
                        resolve(session)
                    })
                })

            }).then((session) => {
                Response.sendWithToken(res, session.token)
            }).catch((errorCode) => {
                Response.sendError(res, errorCode)
            })
        }
    },

    silentLogin() {
        return (req, res) => {
            Response.send(res)
        }
    },

    logout() {
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
    },

    fcm() {
        return (req, res) => {
            User.updateFCM(req.user._id, req.body.fcm, (err, user) => {
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

