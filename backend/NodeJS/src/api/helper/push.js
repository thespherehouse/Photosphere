import * as admin from 'firebase-admin'

const serviceAccount = require('../../photosphere')

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount.CONFIG)
})

const send = (deviceToken, data) => {

    admin.messaging().sendToDevice(deviceToken, { data })
        .then(response => console.log(response))
        .catch(err => console.log(err))

}

export const sendForLike = (deviceToken, postId, userName) => {

    if (typeof postId === 'object')
        postId = postId.toString()

    if (typeof userName === 'object')
        userName = userName.toString()

    console.log(postId, userName)

    send(deviceToken, {
        postId,
        userName
    })

}

export const sendForComment = (deviceToken, postId, userName) => {

    if (typeof postId === 'object')
        postId = postId.toString()

    if (typeof userName === 'object')
        userName = userName.toString()

    console.log(postId, userName)

    send(deviceToken, {
        postId,
        userName
    })

}
