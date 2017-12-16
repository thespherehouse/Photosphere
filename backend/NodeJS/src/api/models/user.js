import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    name: { type: String, required: true },
    email: { type: String, required: true, unique: true },
    password: { type: String },
    fcm: { type: String },
    loginType: { type: Number, default: 0 },
    socialId: { type: String }
},
    {
        toObject: {
            transform: function (doc, ret) {
                delete ret.__v
                delete ret.password
            }
        },
        toJSON: {
            transform: function (doc, ret) {
                delete ret.__v
                delete ret.password
            }
        }
    }
)

schema.statics.createUser = function (name, email, password, cb) {
    return this.create({
        name,
        email,
        password: Utils.hashPasswordSync(password)
    }, cb)
}

schema.statics.createSocialUser = function (name, email, socialId, loginType, cb) {
    return this.findOneAndUpdate(
        {
            socialId
        },
        {
            name,
            email,
            socialId,
            loginType
        },
        {
            upsert: true,
            new: true
        }, cb)
}

schema.statics.getUserByEmail = function (email, cb) {
    return this.findOne({ email }, cb)
}

schema.statics.editName = function (userId, name, cb) {
    return this.findByIdAndUpdate(userId,
        {
            name
        },
        {
            new: true
        },
        cb)
}

schema.statics.editEmail = function (userId, email, cb) {
    return this.findByIdAndUpdate(userId,
        {
            email
        },
        {
            new: true
        },
        cb)
}

schema.statics.editPassword = function (userId, password, cb) {
    return this.findByIdAndUpdate(userId,
        {
            password: Utils.hashPasswordSync(password)
        },
        {
            new: true
        },
        cb)
}

schema.statics.editFCM = function (userId, fcm, cb) {
    return this.findByIdAndUpdate(userId,
        {
            fcm
        },
        {
            new: true
        },
        cb)
}

schema.methods.matchPasswordSync = function (password, cb) {
    Utils.checkPassword(password, this.password, cb)
}

export default mongoose.model('User', schema)  
