import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    name: { type: String, required: true, unique: true },
    email: { type: String, required: true },
    password: { type: String, required: true },
    fcm: String
}, {
        toObject: {
            transform: function (doc, ret) {
                delete ret.__v;
                delete ret.password;
            }
        },
        toJSON: {
            transform: function (doc, ret) {
                delete ret.__v;
                delete ret.password;
            }
        }
    });

schema.statics.checkEmail = function (email, cb) {
    return this.findOne({ email }, cb);
};

schema.statics.register = function (name, email, password, cb) {
    return this.create({
        name,
        email,
        password: Utils.hashPasswordSync(password)
    }, cb);
}

schema.statics.findByEmail = function (email, cb) {
    return this.findOne({
        email
    }, cb);
}

schema.statics.updateName = function (userId, name, cb) {
    return this.findByIdAndUpdate(userId,
        {
            name
        },
        {
            'new': true
        },
        cb);
}

schema.statics.updateEmail = function (userId, email, cb) {
    return this.findByIdAndUpdate(userId,
        {
            email
        },
        {
            'new': true
        },
        cb);
}

schema.statics.updatePassword = function (userId, password, cb) {
    return this.findByIdAndUpdate(userId,
        {
            password: Utils.hashPasswordSync(password)
        },
        {
            'new': true
        },
        cb);
}

schema.statics.updateFCM = function (userId, fcm, cb) {
    return this.findByIdAndUpdate(userId,
        {
            fcm
        },
        {
            'new': true
        },
        cb);
}

schema.methods.checkPassword = function (password, cb) {
    Utils.checkPassword(password, this.password, cb);
}

export default mongoose.model('User', schema);
