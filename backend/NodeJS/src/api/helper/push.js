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

    send(deviceToken, {
        postId,
        userName
    })

}

export const sendForComment = (deviceToken, postId, userName) => {

    send(deviceToken, {
        postId,
        userName
    })

}
