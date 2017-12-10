'use strict';

const crypto = require('crypto');
const bcrypt = require('bcrypt');
const rn = require('random-number');
const sendGrid = require('@sendgrid/mail');
const algorithm = 'aes-256-ctr';
const password = 'd6F3Efeq';

module.exports.encrypt = function (text) {
    var cipher = crypto.createCipher(algorithm, password);
    var crypted = cipher.update(text, 'utf8', 'hex');
    crypted += cipher.final('hex');
    return crypted;
};

module.exports.decrypt = function (text) {
    var decipher = crypto.createDecipher(algorithm, password);
    var dec = decipher.update(text, 'hex', 'utf8');
    dec += decipher.final('utf8');
    return dec;
};

module.exports.hashPassword = function (password, cb) {
    bcrypt.hash(password, 10).then(function (hash) {
        cb(hash);
    });
}

module.exports.hashPasswordSync = function (password) {
    return bcrypt.hashSync(password, 10);
}

module.exports.checkPassword = function (password, hash, cb) {
    bcrypt.compare(password, hash).then(function (res) {
        cb(res);
    })
}

module.exports.token = function () {
    return crypto.randomBytes(48).toString('hex');
};

module.exports.getWordCount = function (text) {
    return text.match(/\S+/g).length;
};

module.exports.getSnippet = function (text) {
    // return text.replace(/(([^\s]+\s\s*){20})(.*)/, "$1").trim();
    // return text.trim().split(/\s+/).slice(0, 4).join(' ');
    return text.trim().substring(0, 200);
};

module.exports.genOtp = function () {
    return rn({
        min: 100000,
        max: 999999,
        integer: true
    });
}

module.exports.emailOtp = function (email, otp) {
    sendGrid.setApiKey(process.env.SENDGRID_API_KEY);
    sendGrid.send({
        from: 'noreply@storiesphere.com',
        to: email,
        subject: 'Here is your OTP',
        html: `<strong>${otp}</strong>`
    });
}
