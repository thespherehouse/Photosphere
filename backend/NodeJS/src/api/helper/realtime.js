import Server from 'socket.io'
import * as URL from 'url'
import * as Utils from './utils'

let io = null

export const Domains = {
    Default: '',
    Like: 'like',
    Comment: 'comment',
    Message: 'message'
}

export const Events = {
    Create: 'create',
    Read: 'read',
    Update: 'update',
    Delete: 'delete'
}

const emit = (domain, event, data, isVolatile, targetUserId) => {
    if (!io)
        return

    if (!domain || !event || !data)
        return

    if (!isVolatile || typeof isVolatile === 'boolean')
        isVolatile = false

    if (targetUserId && typeof targetUserId !== 'string')
        targetUserId = null

    let IO = io

    if (isVolatile)
        IO = IO.volatile

    IO = IO.of(`/${domain}`)

    if (targetUserId)
        IO = IO.to(targetUserId)

    IO.emit(event, data)
}

const generateId = (req, cb) => {
    const query = URL.parse(req.url, true).query
    if (!query || !query.token)
        return cb(new Error('Token Not Found'))

    Utils.checkSessionByToken(query.token, (isValid, session) => {
        if (isValid)
            return cb(new Error('User Not Found'))
        cb(null, session.user._id)
    })
}

const allowRequest = (req, cb) => {
    const query = URL.parse(req.url, true).query
    if (!query || !query.token)
        return cb('Thou shall not pass', false)

    Utils.checkSessionByToken(query.token, (isValid, session) => {
        cb('Thou shall not pass', isValid)
    })
}

export const init = server => {
    io = Server(server, {
        path: '/realtime',
        serveClient: false,
        pingInterval: 10000,
        pingTimeout: 5000,
        cookie: false,
        allowRequest: allowRequest
    })
    io.engine.generateId = generateId
}

export const emitLike = (postId, userId) => {
    emit(Domains.Like, Events.Create, { postId, userId }, true)
}

export const emitUnlike = (postId, userId) => {
    emit(Domains.Like, Events.Delete, { postId, userId }, true)
}

export const emitCreateComment = (postId, userId, commentId, comment) => {
    emit(Domains.Comment, Events.Create, { postId, userId, commentId, comment }, true)
}

export const emitEditComment = (postId, userId, comment) => {
    emit(Domains.Comment, Events.Update, { postId, userId, comment }, true)
}

export const emitDeleteComment = (postId, userId, commentId) => {
    emit(Domains.Comment, Events.Delete, { postId, userId }, true)
}

