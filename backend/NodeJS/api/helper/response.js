import Errors from './errors'

export function send(res, data) {

    if (!res)
        return;

    if (!data)
        data = null;

    res.status(200)
        .send({
            error: this.Errors.None,
            data: data
        });

};

export function sendWithToken(res, token, data) {

    if (!res || !token)
        return;

    if (data !== null && data.password)
        delete data.password;

    res.setHeader('Token', token);
    res.status(200)
        .send({
            error: this.Errors.None,
            data: data
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
