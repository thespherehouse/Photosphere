'use strict';

const Models = require('../../models');
const User = Models.User;
const Session = Models.Session;
const Response = require('../../response');
const Utils = require('../../utils');

module.exports.checkEmail = function () {

    return function (req, res) {

        User.checkEmail(req.params.email, function (err, user) {

            if (err) {
                console.log(err.message);
                return Response.sendError(res, Response.Errors.Internal);
            }

            if (!user)
                return Response.sendError(res, Response.Errors.NotFound);

            Response.send(res, user.toObject());

        });

    }

}

module.exports.login = function () {

    return function (req, res) {

        new Promise(function (resolve, reject) {

            if (!req.body.email || !req.body.password)
                return reject(Response.Errors.Incomplete);

            resolve({ email: req.body.email, password: req.body.password });

        }).then(function (obj) {

            return new Promise(function (resolve, reject) {

                User.checkEmail(obj.email, function (err, user) {

                    if (err) {
                        console.log(err.message);
                        return reject(Response.Errors.Internal);
                    }

                    if (!user)
                        return reject(Response.Errors.EmailUnregistered);

                    resolve({ user, password: obj.password });

                });

            });

        }).then(function (obj) {

            return new Promise(function (resolve, reject) {

                obj.user.checkPassword(obj.password, function (res) {

                    if (!res) {
                        console.log('Password mismatch');
                        return reject(Response.Errors.Password);
                    }

                    resolve(obj.user);

                });

            });

        }).then(function (user) {

            return new Promise(function (resolve, reject) {

                Session.createSession(user._id, req.deviceId, function (err, session) {

                    if (err || !session) {
                        console.log(err.message);
                        return reject(Response.Errors.Internal);
                    }

                    if (!session.isNew)
                        session.refreshToken();

                    resolve({ user, session });

                });

            });

        }).then(function (obj) {

            return new Promise(function (resolve, reject) {
                Response.sendWithToken(res, obj.session.token, obj.user.toObject());
                resolve();
            });

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode);
        });

    }

}

module.exports.register = function () {

    return function (req, res) {

        new Promise(function (resolve, reject) {

            if (!req.body.name || !req.body.email || !req.body.password)
                return reject(Response.Errors.Incomplete);

            resolve({ name: req.body.name, email: req.body.email, password: req.body.password });

        }).then(function (obj) {

            return new Promise(function (resolve, reject) {

                User.checkEmail(obj.email, function (err, user) {

                    if (err)
                        return reject(Response.Errors.Internal);

                    if (user)
                        return reject(Response.Errors.EmailTaken);

                    resolve(obj);

                });

            });

        }).then(function (obj) {

            return new Promise(function (resolve, reject) {

                User.register(obj.name, obj.email, obj.password, function (err, user) {

                    if (err || !user) {
                        console.log(err.message);
                        return reject(Response.Errors.Internal);
                    }

                    resolve(user);
                })

            });

        }).then(function (user) {

            return new Promise(function (resolve, reject) {

                Session.createSession(user._id, req.deviceId,
                    function (err, session) {

                        if (err || !session)
                            return reject(Response.Errors.Internal);

                        resolve({ user, session });

                    });

            });

        }).then(function (obj) {

            return new Promise(function (resolve, reject) {
                Response.sendWithToken(res, obj.session.toObject().token, obj.user.toObject());
                resolve();
            });

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode);
        })

    }

}

module.exports.forgotPassword = function () {

    return function (req, res) {

        new Promise(function (resolve, reject) {

            if (!req.body.email)
                return reject(Response.Errors.Incomplete);

            resolve(req.body.email);

        }).then(function (email) {

            return new Promise(function (resolve, reject) {

                User.findOneAndUpdate({ email: email }, {
                    otp: Utils.genOtp()
                }, { 'new': true }, function (err, user) {

                    if (err) {
                        console.log(err.message);
                        return reject(Response.Errors.Internal);
                    }

                    if (!user)
                        return reject(Response.Errors.NotFound);

                    Utils.emailOtp(user.email, user.otp);

                    Response.send(res);
                    resolve();

                });

            });

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode);
        });

    }

}

module.exports.checkOtp = function () {

    return function (req, res) {

        new Promise(function (resolve, reject) {

            if (!req.body.email || !req.body.otp || isNan(req.body.otp))
                return reject(Response.Errors.Incomplete);

            resolve({ email: req.body.email, otp: parseInt(req.body.otp) });

        }).then(function (obj) {

            return new Promise(function (resolve, reject) {

                User.findOne({ email: obj.email, otp: obj.otp }, function (err, user) {

                    if (err) {
                        console.log(err.message);
                        return reject(Response.Errors.Internal);
                    }

                    if (!user)
                        return reject(Response.Errors.NotFound);

                    Response.send(res);
                    resolve();

                });

            });

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode);
        })

    }

}

module.exports.resetPassword = function () {

    return function (req, res) {

        new Promise(function (resolve, reject) {

            if (!req.body.email)
                return reject(Response.Errors.Incomplete);

            resolve(req.body.email);

        }).then(function (email) {

            return new Promise(function (resolve, reject) {

                User.findOneAndUpdate({ email: req.body.email },
                    {
                        password: req.body.password,
                        otp: 0
                    }, { 'new': true }, function (err, user) {

                        if (err) {
                            console.log(err.message);
                            return reject(Response.Errors.Internal);
                        }

                        if (!user)
                            return reject(Response.Errors.NotFound);

                        Response.send(res);
                        resolve();

                    });

            });

        }).catch(function (errorCode) {
            Response.sendError(res, errorCode);
        });

    }

}

module.exports.silentLogin = function () {

    return function (req, res) {

        Session.findOne({
            user: req.user._id,
            deviceId: req.deviceId
        }, function (err, session) {

            if (err) {
                console.log(err.message);
                return Response.sendError(res, Response.Errors.Internal);
            }

            if (!session)
                return Response.sendError(res, Response.Errors.NotFound);

            Response.send(res);

        });

    }

}

module.exports.logout = function () {

    return function (req, res) {

        Session.findOneAndRemove({
            user: req.user._id,
            deviceId: req.deviceId
        }, function (err, session) {

            if (err) {
                console.log(err.message);
                return Response.sendError(res, Response.Errors.Internal);
            }

            if (!session)
                return Response.sendError(res, Response.Errors.NotFound);

            Response.send(res);

        });

    }

}