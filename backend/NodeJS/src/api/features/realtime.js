import WS from 'ws'
import * as URL from 'url'
import * as Utils from '../helper/utils'

let wss = null
let clients = {}

export const Domains = {
    Post: 'post',
    Like: 'like',
    Comment: 'comment',
    Chat: 'chat'
}

export const Events = {
    Create: 'create',
    Read: 'read',
    Update: 'update',
    Delete: 'delete'
}

function validateDomain(message) {
    if (!message.domain || typeof message.domain !== 'string')
        return false
    return /(post|like|comment|chat)\b/.test(message.domain)
}

function validateEvent(message) {
    if (!message.event || typeof message.event !== 'string')
        return false
    return /(create|read|update|delete)\b/.test(message.event)
}

function processNewConnection(ws, req) {
    clients[req.user._id] = { ws, user: req.user }
    console.log(`${req.user.name} connected`)
    ws.on('message', createNewMessageHandler(req.user))
    ws.on('close', createConnectionCloseHandler(req.user))
    ws.on('error', (err) => {
        console.log('Socket error')
        console.log(err.message)
    })
}

function createNewMessageHandler(user) {
    return (data) => {
        const message = JSON.parse(data)
        if (!validateDomain(message) || !validateEvent(message))
            return

        const domain = handlers[message.domain]
        if (!domain || typeof domain !== 'object')
            return

        const event = domain[message.event]
        if (!event || typeof event !== 'function')
            return

        event(user, message)
    }
}

function createConnectionCloseHandler(user) {
    return (code, reason) => {
        delete clients[`${user._id}`]
        console.log(`${user.name} disconnected`)
    }
}

const handlers = {

    like: {

    },

    comment: {

    },

    chat: {

        create(user, message) {
            if (message.groupId && typeof message.groupId === 'string' && message.message.message && typeof message.message === 'string')
                Chat.WS.createMessage(message.groupId, user._id, user.name, message.message)
        }

    }

}

function emit(domain, event, data, targetUserId) {

    if (!wss)
        return

    if (!data || !event)
        return

    if (!data)
        data = null

    const message = JSON.stringify({ domain, event, data })

    if (targetUserId && clients[targetUserId]) {
        clients[targetUserId].ws.send(message)
    } else {
        for (const client in clients) {
            if (client)
                clients[client].ws.send(message)
        }
    }
}

export const init = (server) => {
    wss = new WS.Server({
        server,
        path: '/realtime',
        verifyClient: (info, cb) => {
            const location = URL.parse(info.req.url, true)
            const token = location.query.token

            if (!token || typeof token !== 'string') {
                cb(false, 4000, 'Missing token')
                return
            }

            Utils.checkSessionByToken(token, (isValid, session) => {
                if (isValid) {
                    info.req.user = session.user
                    cb(true)
                } else
                    cb(false, 4001, 'Invalid token')
            })
        }
    })
    wss.on('connection', processNewConnection)
    wss.on('error', (err) => {
        console.log('Socket server error')
        console.log(err.message)
    })
}

export const emitNewPost = (post) => {
    emit(Domains.Post, Events.Create, post)
}

export const emitLike = (postId, like) => {
    emit(Domains.Like, Events.Create, { postId, like })
}

export const emitUnlike = (postId, like) => {
    emit(Domains.Like, Events.Delete, { postId, like })
}

export const emitCreateComment = (postId, comment) => {
    emit(Domains.Comment, Events.Create, { postId, comment })
}

export const emitEditComment = (postId, comment) => {
    emit(Domains.Comment, Events.Update, { postId, comment })
}

export const emitDeleteComment = (postId, comment) => {
    emit(Domains.Comment, Events.Delete, { postId, comment })
}

export const emitChatMessage = (message) => {
    emit(Domains.Message, Events.Create, message)
}