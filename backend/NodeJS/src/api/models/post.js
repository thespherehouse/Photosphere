import mongoose from 'mongoose'
import { Utils } from '../helper'

const schema = new mongoose.Schema({
    owner: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    url: { type: String, required: true },
    ownerName: { type: String, required: true },
    title: { type: String, required: true },
    description: { type: String, required: true },
    aspectRatio: { type: Number, required: true },
    category: { type: mongoose.Schema.Types.ObjectId, ref: 'Category' },
    createdAt: { type: Date, default: new Date() },
    updatedAt: { type: Date, default: new Date() },
    likes: [{
        user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
        createdAt: { type: Date, default: new Date() }
    }],
    likesCount: { type: Number, default: 0, index: true },
    comments: [{
        user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
        createdAt: { type: Date, default: new Date() },
        updatedAt: { type: Date, default: new Date() },
        comment: { type: String, required: true }
    }],
    commentsCount: { type: Number, default: 0, index: true }
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

schema.index({ createdAt: -1 })
schema.index({ updatedAt: -1 })
schema.index({ 'likes.createdAt': -1 })
schema.index({ 'comments.createdAt': -1 })

schema.statics.createPost = function (userId, name, title, description, aspectRatio, url, cb) {
    return this.create({
        owner: userId,
        ownerName: name,
        title,
        description,
        aspectRatio,
        url
    }, cb)
}

schema.statics.getAllPosts = function (userId, skip, limit, orderBy, sortOrder, cb) {
    let sorter = {}
    sorter[orderBy] = sortOrder
    console.log(sorter)
    return this.aggregate([
        {
            $sort: sorter
        },
        {
            $skip: skip
        },
        {
            $limit: limit
        },
        {
            $project: {
                owner: 1,
                ownerName: 1,
                title: 1,
                description: 1,
                aspectRatio: 1,
                url: 1,
                likesCount: 1,
                commentsCount: 1,
                createdAt: 1,
                updatedAt: 1,
                isLikedByMe: {
                    $in: [userId, '$likes.user']
                },
                isCommentedByMe: {
                    $in: [userId, '$comments.user']
                }
            }
        }
    ], cb)
}

schema.statics.getAllPostsByCategory = function (userId, categoryId, skip, limit, orderBy, sortOrder, cb) {
    let sorter = {}
    sorter[orderBy] = sortOrder
    return this.aggregate([
        {
            $match: {
                category: mongoose.Types.ObjectId(categoryId)
            }
        },
        {
            $sort: sorter
        },
        {
            $skip: skip
        },
        {
            $limit: limit
        },
        {
            $project: {
                owner: 1,
                ownerName: 1,
                title: 1,
                description: 1,
                aspectRatio: 1,
                url: 1,
                likesCount: 1,
                commentsCount: 1,
                createdAt: 1,
                updatedAt: 1,
                isLikedByMe: {
                    $in: [userId, '$likes.user']
                },
                isCommentedByMe: {
                    $in: [userId, '$comments.user']
                }
            }
        }
    ], cb)
}

schema.statics.getAllPostsByUser = function (userId, targetUserId, skip, limit, orderBy, sortOrder, cb) {
    let sorter = {}
    sorter[orderBy] = sortOrder
    return this.aggregate([
        {
            $match: {
                owner: mongoose.Types.ObjectId(targetUserId)
            }
        },
        {
            $sort: sorter
        },
        {
            $skip: skip
        },
        {
            $limit: limit
        },
        {
            $project: {
                owner: 1,
                ownerName: 1,
                title: 1,
                description: 1,
                aspectRatio: 1,
                url: 1,
                likesCount: 1,
                commentsCount: 1,
                createdAt: 1,
                updatedAt: 1,
                isLikedByMe: {
                    $in: [userId, '$likes.user']
                },
                isCommentedByMe: {
                    $in: [userId, '$comments.user']
                }
            }
        }
    ], cb)
}

schema.statics.getPost = function (userId, postId, cb) {
    return this.aggregate([
        {
            $match: {
                _id: mongoose.Types.ObjectId(postId)
            }
        },
        {
            $limit: 1
        },
        {
            $project: {
                owner: 1,
                ownerName: 1,
                title: 1,
                description: 1,
                aspectRatio: 1,
                url: 1,
                likesCount: 1,
                commentsCount: 1,
                createdAt: 1,
                updatedAt: 1,
                isLikedByMe: {
                    $in: [userId, '$likes.user']
                },
                isCommentedByMe: {
                    $in: [userId, '$comments.user']
                }
            }
        }
    ], cb)
}

schema.statics.editPostTitle = function (userId, postId, title, cb) {
    return this.findOneAndUpdate(
        {
            _id: postId,
            owner: userId
        },
        {
            title,
            updatedAt: new Date()
        },
        {
            new: true
        },
        cb)
}

schema.statics.editPostDescription = function (userId, postId, description, cb) {
    return this.findOneAndUpdate(
        {
            _id: postId,
            owner: userId
        },
        {
            description,
            updatedAt: new Date()
        },
        {
            new: true
        },
        cb)
}

schema.statics.deletePost = function (userId, postId, cb) {
    return this.findOneAndRemove({ _id: postId, owner: userId }, cb)
}

schema.statics.getLikes = function (postId, skip, limit, cb) {
    return this.findById(postId,
        {
            'likes': { $slice: [skip, limit] }
        })
        .populate('likes.user', 'name')
        .exec(function (err, post) {

            if (err || !post)
                cb(err, null)
            else
                cb(err, post.likes)

        })
}

schema.statics.like = function (userId, postId, cb) {
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
        cb)
}

schema.statics.unlike = function (userId, postId, cb) {
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
        cb)
}

schema.statics.createComment = function (userId, postId, comment, cb) {
    return this.findOneAndUpdate(
        {
            _id: postId
        },
        {
            $push: {
                comments: {
                    $each: [{ user: userId, comment }],
                    $position: 0
                }
            },
            $inc: { commentsCount: 1 }
        },
        {
            new: true,
            fields: {
                'comments': { $slice: 1 }
            }
        },
        cb)
}

schema.statics.getComments = function (postId, skip, limit, cb) {
    return this.findById(postId,
        {
            'comments': { $slice: [skip, limit] }
        })
        .populate('comments.user', 'name')
        .exec(function (err, post) {

            if (err || !post)
                cb(err, null)
            else
                cb(err, post.comments)

        })
}

schema.statics.editComment = function (userId, postId, commentId, comment, cb) {
    return this.findOneAndUpdate(
        {
            _id: postId,
            'comments.owner': { $eq: userId },
            'comments._id': { $eq: commentId }
        },
        {
            'comments.$.comment': comment,
            updatedAt: new Date()
        },
        {
            new: true
        },
        cb)
}

schema.statics.deleteComment = function (userId, postId, commentId, cb) {
    return this.findOneAndUpdate(
        {
            _id: postId,
            'comments.owner': { $eq: userId }
        },
        {
            $pull: { comments: { _id: commentId } },
            $inc: { commentsCount: -1 }
        },
        {
            new: true
        },
        cb)
}

export default mongoose.model('Post', schema)
