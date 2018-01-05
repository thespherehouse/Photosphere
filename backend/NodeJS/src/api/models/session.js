import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    deviceId: { type: String, required: true },
    token: { type: String, required: true }
}, {
        toObject: {
            transform: function (doc, ret) {
                delete ret.__v
            }
        },
        toJSON: {
            transform: function (doc, ret) {
                delete ret.__v
            }
        }
    }
)

schema.statics.createSession = function (userId, deviceId, cb) {
    return this.findOneAndUpdate(
        {
            user: userId,
            deviceId
        },
        {
            user: userId,
            deviceId,
            token: Utils.token()
        },
        {
            upsert: true,
            new: true
        }, cb)
}

schema.statics.getSession = function (deviceId, token, cb) {
    return this.findOne(
        {
            deviceId,
            token
        })
        .populate('user')
        .exec(cb)
}

schema.statics.getSessionByToken = function (token, cb) {
    return this.findOne(
        {
            token
        })
        .populate('user')
        .exec(cb)
}

schema.statics.deleteSession = function (deviceId, token, cb) {
    return this.findOneAndRemove(
        {
            deviceId,
            token
        }, cb)
}

export default mongoose.model('Session', schema)
