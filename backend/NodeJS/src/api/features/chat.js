import { Message, Group } from '../models'
import { Response, Errors, Utils } from '../helper'
import * as Realtime from './realtime'
import { isPrimitive } from 'util';

export const Validator = {

    createGroup() {
        return (req, res, next) => {
            if (!req.body.name)
                return Response.sendError(res, Errors.Incomplete)
            if (!req.body.isPrivate)
                req.body.isPrivate = false

            req.body.isPrivate = (req.body.isPrivate === 'true')

            next()
        }
    },

    createMessage() {
        return (req, res, next) => {
            if (!req.body.message)
                return Response.sendError(res, Errors.Incomplete)
            next()
        }
    }

}

export const Endpoint = {

    createGroup() {
        return (req, res) => {
            if (req.body.isPrivate) {
                Group.createPrivateGroup(req.user._id, req.body.name, (err, group) => {

                    if (err) {
                        console.log(err.message)
                        return Response.sendError(res, Errors.Internal)
                    }

                    if (!group)
                        return Response.sendError(res, Errors.AlreadyPresent)

                    Response.send(res, group.toObject())
                })
            } else {
                Group.createPublicGroup(req.user._id, req.body.name, (err, group) => {

                    if (err) {
                        console.log(err.message)
                        return Response.sendError(res, Errors.Internal)
                    }

                    if (!group)
                        return Response.sendError(res, Errors.AlreadyPresent)

                    Response.send(res, group.toObject())
                })
            }
        }
    },

    createMessage() {
        return (req, res) => {
            Message.createMessage(req.params.groupId, req.user._id, req.user.name, req.body.message, (err, message) => {
                if (err || !message) {
                    if (err)
                        console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                Response.send(res)
                Realtime.emitChatMessage(message)

            })
        }
    },

    getGroups() {
        return (req, res) => {
            Group.getGroups(req.user._id, req.query.skip, req.query.limit, (err, groups) => {

                if (err) {
                    console.log(err.message)
                    Response.sendError(res, Errors.Internal)
                }

                if (!groups)
                    return Response.sendError(res, Errros.NotFound)

                Response.send(res, groups)
            })
        }
    },

    getMessages() {
        return (req, res) => {
            Message.getMessages(req.params.groupId, req.user._id, req.query.skip, req.query.limit, (err, messages) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!messages || messages.length === 0)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, messages)

            })
        }
    },

    deleteGroup() {
        return (req, res) => {
            new Promise((resolve, reject) => {

                Group.deleteGroup(req.params.groupId, req.user._id, (err, group) => {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    resolve()
                })

            }).then(() => {

                return new Promise((resolve, reject) => {

                    Message.deleteMessagesByGroup(req.params.groupId, (err, messages) => {

                        if (err) {
                            console.log(err.message)
                            return reject(Errors.Internal)
                        }

                        Response.send(res)
                        resolve()

                    })

                })

            }).catch((errorCode) => {
                console.log(errorCode)
                Response.sendError(res, errorCode)
            })
        }
    }

}

export const WS = {

    createMessage(groupId, userId, userName, message) {
        return (message) => {
            Message.createMessage(groupId, userId, userName, message, (err, message) => {
                if (err || !message) {
                    if (err)
                        console.log(err.message)
                    return
                }

                Realtime.emitChatMessage(message)
            })
        }
    }

}
