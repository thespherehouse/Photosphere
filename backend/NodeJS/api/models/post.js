import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    title: { type: String, required: true },
    description: { type: String, required: true },
    url: { type: String, required: true },
    createdAt: { type: Date, default: new Date() },
    updatedAt: { type: Date, default: new Date() },
    likes: [{
        user: { type: Schema.ObjectId, ref: 'User' },
        createdAt: { type: Date, default: new Date() }
    }],
    likesCount: { type: Number, default: 0 },
    owner: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    ownerName: { type: String, required: true }
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

schema.statics.createPost = function (userId, name, title, description, url, cb) {
    return this.create({
        owner: userId,
        ownerName: name,
        title,
        description,
        url
    }, cb);
}

schema.statics.getAllPosts = function (userId, skip, limit, cb) {
    return this.aggregate([
        {
            '$skip': skip
        },
        {
            '$limit': limit
        },
        {
            '$project': {
                owner: 1,
                ownerName: 1,
                title: 1,
                likesCount: 1,
                createdAt: 1,
                updatedAt: 1,
                isLikedByMe: {
                    '$in': [userId, '$likes.user']
                }
            }
        }
    ], cb);
}

schema.statics.getPost = function (postId, userId, cb) {
    return this.aggregate([
        {
            '$match': { _id: mongoose.Types.ObjectId(postId) }
        },
        {
            '$limit': 1
        },
        {
            '$project': {
                owner: 1,
                ownerName: 1,
                title: 1,
                description: 1,
                likesCount: 1,
                createdAt: 1,
                updatedAt: 1,
                isLikedByMe: {
                    '$in': [userId, '$likes.user']
                }
            }
        }
    ], cb);
}

schema.statics.editPostTitle = function (postId, userId, title, cb) {
    return this.findOneAndUpdate(
        { _id: postId, owner: userId },
        {
            title,
            updatedAt: new Date()
        },
        { 'new': true },
        cb);
}

schema.statics.editPostDescription = function (postId, userId, description, cb) {
    return this.findOneAndUpdate(
        { _id: postId, owner: userId },
        {
            description,
            updatedAt: new Date()
        },
        { 'new': true },
        cb);
}

schema.statics.editPostUrl = function (postId, userId, url, cb) {
    return this.findOneAndUpdate(
        { _id: postId, owner: userId },
        {
            url,
            updatedAt: new Date()
        },
        { 'new': true },
        cb);
}

schema.statics.deletePost = function (postId, userId, cb) {
    return this.findOneAndRemove({ _id: postId, user: userId }, cb);
}

schema.statics.getLikes = function (postId, skip, limit, cb) {
    return this.findById(postId,
        {
            'likes': { '$slice': [skip, limit] }
        })
        .populate('likes.user', 'name')
        .exec(function (err, story) {

            if (err || !story)
                cb(err, null);
            else
                cb(err, story.likes);

        });
}

schema.statics.like = function (postId, userId, cb) {
    return this.findOneAndUpdate(
        {
            _id: postId,
            'likes.user': { '$ne': userId }
        },
        {
            '$push': { likes: { user: userId } },
            '$inc': { likesCount: 1 }
        },
        { 'new': true },
        cb);
}

schema.statics.unlike = function (postId, userId, cb) {
    return this.findOneAndUpdate(
        {
            _id: postId,
            'likes.user': { '$eq': userId }
        },
        {
            '$pull': { likes: { user: userId } },
            '$inc': { likesCount: -1 }
        },
        { 'new': true },
        cb);
}

export default mongoose.model('Post', schema);
