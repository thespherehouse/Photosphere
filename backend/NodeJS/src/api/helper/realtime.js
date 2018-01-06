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
        const userId = session.user._id
        if (isValid) {
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

export const emitNewPost = (postId, userId, userName) => {
    emit(Domains.Post, Events.Create, { postId, userId, userName })
}

export const emitLike = (postId, userId, userName) => {
    emit(Domains.Like, Events.Create, { postId, userId, userName })
}

export const emitUnlike = (postId, userId, userName) => {
    emit(Domains.Like, Events.Delete, { postId, userId, userName })
}

export const emitCreateComment = (postId, userId, commentId, userName, comment) => {
    emit(Domains.Comment, Events.Create, { postId, userId, commentId, userName, comment })
}

export const emitEditComment = (postId, userId, comment, userName) => {
    emit(Domains.Comment, Events.Update, { postId, userId, comment, userName })
}

export const emitDeleteComment = (postId, userId, commentId, userName) => {
    emit(Domains.Comment, Events.Delete, { postId, userId, userName })
}


// const emit = (domain, event, data, isVolatile, targetUserId) => {
//     if (!io)
//         return

//     if (!domain || !event || !data)
//         return

//     if (!isVolatile || typeof isVolatile !== 'boolean')
//         isVolatile = false

//     if (!targetUserId || typeof targetUserId !== 'string')
//         targetUserId = null

//     let IO = io

//     if (isVolatile) {
//         console.log('Volatile event')
//         IO = IO.volatile
//     }

//     if (targetUserId) {
//         console.log('To specific user')
//         IO = IO.to(targetUserId)
//     }

//     console.log('Emitted')

//     IO.emit(event, {
//         domain,
//         data
//     })
// }

// const generateId = (req) => {
//     console.log(`${req.session.user.name} just connected`)
//     return req.session.user._id
// }

// const allowRequest = (req, cb) => {
//     const query = URL.parse(req.url, true).query
//     if (!query || !query.token)
//         return cb('Thou shall not pass', false)

//     Utils.checkSessionByToken(query.token, (isValid, session) => {
//         if (isValid) {
//             req.session = session
//             cb(null, true)
//         } else {
//             cb('Thou shall not pass', false)
//         }
//     })
// }

// export const init = (server) => {
//     io = Server(server, {
//         path: '/realtime',
//         serveClient: false,
//         pingInterval: 10000,
//         pingTimeout: 5000,
//         cookie: false,
//         allowRequest: allowRequest
//     })
//     io.engine.generateId = generateId
// }

// export const emitNewPost = (postId, userId, userName) => {
//     emit(Domains.Post, Events.Create, { postId, userId, userName })
// }

// export const emitLike = (postId, userId, userName) => {
//     emit(Domains.Like, Events.Create, { postId, userId, userName })
// }

// export const emitUnlike = (postId, userId, userName) => {
//     emit(Domains.Like, Events.Delete, { postId, userId, userName })
// }

// export const emitCreateComment = (postId, userId, commentId, userName, comment) => {
//     emit(Domains.Comment, Events.Create, { postId, userId, commentId, userName, comment })
// }

// export const emitEditComment = (postId, userId, comment, userName) => {
//     emit(Domains.Comment, Events.Update, { postId, userId, comment, userName })
// }

// export const emitDeleteComment = (postId, userId, commentId, userName) => {
//     emit(Domains.Comment, Events.Delete, { postId, userId, userName })
// }

