import crypto from 'crypto'
import bcrypt from 'bcrypt'
import rn from 'random-number'
import sendGrid from '@sendgrid/mail'

const algorithm = 'aes-256-ctr';
const password = 'd6F3Efeq';

export function encrypt(text) {
    let cipher = crypto.createCipher(algorithm, password);
    let crypted = cipher.update(text, 'utf8', 'hex');
    crypted += cipher.final('hex');
    return crypted;
};

export function decrypt(text) {
    let decipher = crypto.createDecipher(algorithm, password);
    let dec = decipher.update(text, 'hex', 'utf8');
    dec += decipher.final('utf8');
    return dec;
};

export function hashPassword(password, cb) {
    bcrypt.hash(password, 10).then(function (hash) {
        cb(hash);
    });
}

export function hashPasswordSync(password) {
    return bcrypt.hashSync(password, 10);
}

export function checkPassword(password, hash, cb) {
    bcrypt.compare(password, hash).then(function (res) {
        cb(res);
    })
}

export function token() {
    return crypto.randomBytes(48).toString('hex');
};

export function genOtp() {
    return rn({
        min: 100000,
        max: 999999,
        integer: true
    });
}

export function emailOtp(email, otp) {
    sendGrid.setApiKey(process.env.SENDGRID_API_KEY);
    sendGrid.send({
        from: 'noreply@storiesphere.com',
        to: email,
        subject: 'Here is your OTP',
        html: `<strong>${otp}</strong>`
    });
}
