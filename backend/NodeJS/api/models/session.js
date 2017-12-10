'use strict';

const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Utils = require('../utils');

const schema = new Schema({
    user: { type: Schema.Types.ObjectId, ref: 'User', required: true },
    deviceId: { type: String, required: true },
    token: { type: String, default: Utils.token() }
}, {
        toObject: {
            transform: function (doc, ret) {
                delete ret.__v;
            }
        },
        toJSON: {
            transform: function (doc, ret) {
                delete ret.__v;
            }
        }
    }
);

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
            'new': true
        }, cb);
};

schema.statics.getUserByToken = function (token, deviceId, cb) {
    return this.findOne(
        {
            user: userId,
            token
        })
        .populate('user')
        .exec(function (err, session) {

            if (err || !session) {
                if (err)
                    console.log(err.message);
                cb(null);
            } else {
                cb(session.user);
            }

        });
}

schema.methods.refreshToken = function () {
    this.token = Utils.token();
    this.save();
}

module.exports = mongoose.model('Session', schema);
