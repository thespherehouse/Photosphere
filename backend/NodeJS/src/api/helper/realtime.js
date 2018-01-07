import WebSocket from 'ws'
import * as URL from 'url'
import * as Utils from './utils'

let wss = null
let clients = {}

export const Domains = {
    Post: 'post',
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

export const init = (server) => {

    wss = new WebSocket.Server({
        server,
        path: '/realtime'
    })

    wss.on('connection', processNewConnection)
}

const processNewConnection = (ws, req) => {
    const location = URL.parse(req.url, true)
    const token = location.query.token

    if (!token || typeof token !== 'string') {
        ws.terminate()
        return
    }

    Utils.checkSessionByToken(token, (isValid, session) => {
        if (isValid) {
            const userId = session.user._id
            console.log(session.user.name + ' connected')
            clients[userId] = ws
            ws.on('close', () => {
                delete clients[`${userId}`]
            })
        } else {
            ws.terminate()
        }
    })
}

const emit = (domain, event, data, targetUserId) => {

    if (!wss)
        return

    if (!data || !event)
        return

    if (!data)
        data = null

    const message = JSON.stringify({ domain, event, data })

    if (targetUserId && clients[targetUserId]) {
        clients[targetUserId].send(message)
    } else {
        for (const client in clients) {
            if (client)
                clients[client].send(message)
        }
    }
}

export const emitNewPost = (post) => {
    emit(Domains.Post, Events.Create, { post })
}

export const emitLike = (like) => {
    emit(Domains.Like, Events.Create, { like })
}

export const emitUnlike = (like) => {
    emit(Domains.Like, Events.Delete, { like })
}

export const emitCreateComment = (comment) => {
    emit(Domains.Comment, Events.Create, { comment })
}

export const emitEditComment = (comment) => {
    emit(Domains.Comment, Events.Update, { comment })
}

export const emitDeleteComment = (comment) => {
    emit(Domains.Comment, Events.Delete, { comment })
}
