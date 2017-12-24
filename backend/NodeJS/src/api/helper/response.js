import Errors from './errors'

export function send(res, data) {

    if (!res)
        return;

    if (!data)
        data = null;

    res.status(200)
        .send({
            error: Errors.None,
            data: data
        });

};

export function sendArray(res, array) {

    if (!res)
        return;

    let data

    if (!array || typeof array !== 'array')
        data = null;
    else
        data = {
            count: array.length,
            items: array
        }

    res.status(200)
        .send({
            error: Errors.None,
            data
        });

};

export function sendWithToken(res, token) {

    if (!res || !token)
        return;

    res.setHeader('Token', token);
    res.status(200)
        .send({
            error: Errors.None,
            data: null
        });

};

export function sendError(res, errorCode) {

    if (!res || !errorCode)
        return;

    res.status(200)
        .send({
            error: errorCode,
            data: {}
        });

};

export function socketSendLike(socket, storyId, likerName) {
    if (!socket)
        return;

    socket.volatile.emit('like', {
        storyId: storyId,
        name: likerName
    });
}
