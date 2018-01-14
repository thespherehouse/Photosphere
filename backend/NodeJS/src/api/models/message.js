import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    message: { type: String, required: true },
    createdAt: { type: Date, default: Date.now },
    group: { type: mongoose.Schema.Types.ObjectId, ref: 'Group', required: true },
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    userName: { type: String, required: true }
},
    {
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

schema.index({ 'group': 1 })
schema.index({ 'createdAt': -1 })

schema.statics.createMessage = function (groupId, userId, userName, message, cb) {
    return this.create({
        message,
        userName,
        group: groupId,
        user: userId
    }, cb)
}

schema.statics.getMessages = function (groupId, userId, skip, limit, cb) {
    return this.aggregate([
        {
            $match: {
                group: mongoose.Types.ObjectId(groupId)
            }
        },
        {
            $sort: {
                'createdAt': -1
            }
        },
        {
            $skip: skip
        },
        {
            $limit: limit
        },
        {
            $project: {
                message: 1,
                createdAt: 1,
                user: 1,
                userName: 1,
                isByMe: {
                    $eq: ['$user', userId]
                }
            }
        }
    ], cb)
}

schema.statics.deleteMessagesByGroup = function (groupId, cb) {
    return this.remove(
        {
            group: groupId
        }, cb)
}

export default mongoose.model('Message', schema)  
