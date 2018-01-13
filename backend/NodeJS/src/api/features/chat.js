import { Message } from '../models'
import { Response, Errors, Utils } from '../helper'
import * as Realtime from './realtime'

export const Validator = {

    createMessage() {
        return (req, res, next) => {
            if (!req.body.groupId || !req.body.message)
                return Response.sendError(Errors.Incomplete)
            next()
        }
    }

}

export const Endpoint = {

    createMessage() {
        return (req, res) => {
            Message.createMessage(req.body.groupId, req.user._id, req.user.name, req.body.message, (err, message) => {
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

    getMessages() {
        return (req, res) => {
            Message.getMessages(req.user._id, req.query.skip, req.query.limit, (err, messages) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!messages || messages.length === 0)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, messages)

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
