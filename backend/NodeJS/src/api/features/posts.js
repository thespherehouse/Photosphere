import uuid from 'uuid/v1'
import { Response, Errors, Utils, Push } from '../helper'
import * as Realtime from './realtime'
import { Post } from '../models'

export const Validator = {

    createPost() {
        return (req, res, next) => {
            if (!req.body.title) {
                Utils.deleteObjectsS3(req.file.key, (err, data) => {
                    if (err)
                        console.log(err.message)
                    console.log(data)
                })
                return Response.sendError(res, Errors.NoTitle)
            }

            if (!req.body.description) {
                Utils.deleteObjectsS3(req.file.key, (err, data) => {
                    if (err)
                        console.log(err.message)
                    console.log(data)
                })
                return Response.sendError(res, Errors.NoDescription)
            }

            next()
        }
    },

    createComment() {
        return (req, res, next) => {
            if (!req.body.comment)
                return Response.sendError(res, Errors.NoComment)
            next()
        }
    },

    sorter() {
        return (req, res, next) => {
            let orderBy = 'updatedAt', sortOrder = -1

            let tempOrderBy = req.query.orderBy
            let tempSortOrder = req.query.sortOrder

            if (tempSortOrder === 'asc')
                tempSortOrder = 1
            else
                tempSortOrder = -1

            if (tempOrderBy === 'createdAt' || tempOrderBy === 'updateAt') {
                // Custom created or updated date ordering. Only descending allowed as it is indexed so
                orderBy = tempOrderBy
                sortOrder = -1
            } else if (tempOrderBy === 'likesCount' || tempOrderBy === 'commentsCount') {
                // Custom, supports ascending and descending ordering
                orderBy = tempOrderBy
                sortOrder = tempSortOrder
            }

            req.query.orderBy = orderBy
            req.query.sortOrder = sortOrder
            next()
        }
    },

    editPost() {
        return (req, res, next) => {
            if (!req.body.title || !req.body.description)
                return Response.sendError(res, Errors.Incomplete)
            next()
        }
    },

    editComment() {
        return (req, res, next) => {
            if (!req.body.comment)
                return Response.sendError(res, Errors.NoComment)
            next()
        }
    }

}

export const Endpoint = {

    createPost() {
        return (req, res) => {
            Post.createPost(req.user._id, req.user.name, req.body.title, req.body.description, req.imageAspectRatio, req.file.key, (err, post) => {

                if (err || !post) {
                    if (err)
                        console.log(err.message)
                    Utils.deleteObjectsS3(req.file.key, (err, data) => {
                        if (err)
                            console.log(err.message)
                        console.log(data)
                    })
                    return Response.sendError(res, Errors.Internal)
                }

                Response.send(res, post)
                Realtime.emitNewPost(post)
            })
        }
    },

    createComment() {
        return (req, res) => {
            Post.createComment(req.user._id, req.params.postId, req.body.comment, (err, post) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!post)
                    return Response.sendError(res, Errors.NotFound)

                let postObject = post.toObject()

                Response.send(res, postObject.comments[0])
                Realtime.emitCreateComment(post._id, postObject.comments[0])

                if (req.user.fcm && req.user._id.toString() !== postObject.owner.toString())
                    Push.sendForComment(req.user.fcm, post.toObject()._id, req.user.name)

            })
        }
    },

    likePost() {
        return (req, res) => {
            Post.like(req.user._id, req.params.postId, (err, post) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!post || post.likes.length === 0)
                    return Response.sendError(res, Errors.NotFound)

                let postObject = post.toObject()

                Response.send(res)
                Realtime.emitLike(post._id, postObject.likes[0])

                if (req.user.fcm && req.user._id.toString() !== postObject.owner.toString())
                    Push.sendForLike(req.user.fcm, post._id, req.user.name)

            })
        }
    },

    getAllPosts() {
        return (req, res) => {
            Post.getAllPosts(req.user._id, req.query.skip, req.query.limit, req.query.orderBy, req.query.sortOrder, (err, posts) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!posts)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, posts)
            })
        }
    },

    getAllPostsByUser() {
        return (req, res) => {
            Post.getAllPostsByUser(req.user._id, req.query.userId, req.query.skip, req.query.limit, req.query.orderBy, req.query.sortOrder, (err, posts) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!posts)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, posts)
            })
        }
    },

    getMyPosts() {
        return (req, res) => {
            Post.getAllPosts(req.user._id, req.user._id, req.query.skip, req.query.limit, req.query.orderBy, req.query.sortOrder, (err, posts) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                Response.send(res, posts)
            })
        }
    },

    getPost() {
        return (req, res) => {
            Post.getPost(req.user._id, req.params.postId, (err, posts) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (posts.length > 0)
                    Response.send(res, posts[0])

            })
        }
    },

    getLikes() {
        return (req, res) => {
            Post.getLikes(req.params.postId, req.query.skip, req.query.limit, (err, likes) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!likes)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, likes)
            })
        }
    },

    getComments() {
        return (req, res) => {
            Post.getComments(req.params.postId, req.query.skip, req.query.limit, (err, comments) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!comments)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, comments)
            })
        }
    },

    editPost() {
        return (req, res) => {
            Post.editPost(req.user._id, req.params.postId, req.body.title, req.body.description, (err, post) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!post)
                    return Response.sendError(res, Errors.NotFound)

                Response.send(res, post.toObject())
            })
        }
    },

    editComment() {
        return (req, res) => {
            Post.editComment(req.user._id, req.params.postId, req.params.commentId, req.body.comment, (err, post) => {
                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!post)
                    return Response.sendError(res, Errors.NotFound)

                let comment = post.toObject().comments[0]

                delete comment.user
                Response.send(res)
                Realtime.emitEditComment(post._id, comment)
            })
        }
    },

    deletePost() {
        return (req, res) => {
            new Promise((resolve, reject) => {

                Post.deletePost(req.user._id, req.params.postId, (err, post) => {

                    if (err) {
                        console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    if (!post)
                        return reject(Errors.NotFound)


                    resolve(post.url)

                })

            }).then((key) => {

                return new Promise((resolve, reject) => {

                    Utils.deleteObjectsS3(key, (err, data) => {

                        if (err) {
                            console.log(err.message)
                            return reject(Errors.Internal)
                        }

                        Response.send(res)
                        resolve()
                    })

                })

            }).catch((errorCode) => {
                Response.sendError(res, errorCode)
            })
        }
    },

    unlikePost() {
        return (req, res) => {
            Post.unlike(req.user._id, req.params.postId, (err, post) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!post || post.likes.length === 0)
                    return Response.sendError(res, Errors.NotFound)

                let like = post.toObject().likes[0]
                delete like.user

                Response.send(res)
                Realtime.emitUnlike(post._id, like)
            })
        }
    },

    deleteComment() {

        return (req, res) => {

            Post.deleteComment(req.user._id, req.params.postId, req.params.commentId, (err, post) => {

                if (err) {
                    console.log(err.message)
                    return Response.sendError(res, Errors.Internal)
                }

                if (!post)
                    return Response.sendError(res, Errors.NotFound)

                let comment = post.toObject().comments[0]

                delete comment.user
                Response.send(res)
                Realtime.emitDeleteComment(post._id, comment)

            })

        }

    }

}

