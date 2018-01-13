import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    name: { type: String, required: true, unique: true },
    active: { type: Boolean, default: true },
    createdAt: { type: Date, default: Date.now },
    admins: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true }],
    members: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true }]
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

schema.index({ 'owner': 1 })
schema.index({ 'createdAt': -1 })

schema.statics.createGroup = function (userId, name, cb) {
    return this.create({
        name,
        admins: [userId],
        members: [userId]
    }, cb)
}

schema.statics.addMember = function (groupId, userId, memberId, cb) {
    return this.findOneAndUpdate(
        {
            _id: groupId,
            admins: userId,
            members: { $ne: memberId }
        },
        {
            $push: { members: memberId }
        },
        {
            new: true
        },
        cb)
}

schema.statics.addAdmin = function (groupId, userId, adminId, cb) {
    return this.findOneAndUpdate(
        {
            _id: groupId,
            admins: userId
        },
        {
            $addToSet: { admins: adminId },
            $addToSet: { members: adminId }
        },
        {
            new: true
        },
        cb)
}

schema.statics.getGroups = function (userId, skip, limit, cb) {
    return this.aggregate([
        {
            $match: {
                members: userId,
                active: true
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
                name: 1,
                createdAt: 1,
                owner: 1,
                ownerName: 1,
                members: 1,
                amIAdmin: {
                    $eq: ['$admins', userId]
                }
            }
        }
    ]).populate('members', 'name')
        .populate('admins', 'name')
        .exec(cb)
}

schema.statics.editGroupName = function (groupId, userId, name, cb) {
    return this.findOneAndUpdate(
        {
            _id: groupId,
            admins: userId,
            active: true
        },
        {
            name
        },
        {
            new: true
        },
        cb)
}

schema.statics.removeMember = function (groupId, userId, memberId, cb) {
    return this.findOneAndUpdate(
        {
            _id: groupId,
            admins: userId,
            members: memberId,
            active: true
        },
        {
            $pull: { members: memberId }
        },
        {
            new: true
        }, cb)
}

export default mongoose.model('Group', schema)  
