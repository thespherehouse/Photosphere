import uuid from 'uuid/v1'
import { Response, Errors } from '../../helper'
import { Post } from '../../models'
import { Utils } from '../../helper'

export function createPost() {

    return (req, res) => {
        new Promise((resolve, reject) => {

            if (!req.body.title || !req.body.description) {
                console.log(req.body)
                return reject(Errors.Incomplete)
            }

            resolve({ title: req.body.title, desc: req.body.description })

        }).then((obj) => {

            return new Promise((resolve, reject) => {

                Post.createPost(req.user._id, req.user.name, obj.title, obj.desc, req.file.key, (err, post) => {

                    if (err || !post) {
                        if (err)
                            console.log(err.message)
                        return reject(Errors.Internal)
                    }

                    resolve(post)

                })

            })

        }).then((post) => {

            Response.send(res, post.toObject())

        }).catch((errorCode) => {
            Response.sendError(res, errorCode)
        })
    }

}

export function getAllPosts() {

    return (req, res) => {

        let skip = 0, limit = 10

        if (req.query.skip)
            skip = Math.max(0, req.query.skip)

        if (req.query.limit)
            limit = Math.min(Math.max(1, req.query.limit), 10)

        Post.getAllPosts(req.user._id, skip, limit, (err, posts) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            Response.send(res, posts)

        })

    }

}

export function getAllPostsByUser() {

    return (req, res) => {

        let skip = 0, limit = 10

        if (req.query.skip)
            skip = Math.max(0, req.query.skip)

        if (req.query.limit)
            limit = Math.min(Math.max(1, req.query.limit), 10)

        Post.getAllPosts(req.user._id, skip, limit, (err, posts) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            Response.send(res, posts)

        })

    }

}

export function getPost() {

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

}

export function editPostTitle() {

    return (req, res) => {

        if (!req.body.title)
            return Response.sendError(res, Errors.Incomplete)

        Post.editPostTitle(req.user._id, req.params.postId, req.body.title, (err, post) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!post)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res, post.toObject())

        })

    }

}

export function editPostDescription() {

    return (req, res) => {

        if (!req.body.description)
            return Response.sendError(res, Errors.Incomplete)

        Post.editPostDescription(req.user._id, req.params.postId, req.body.description, (err, post) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!post)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res, post.toObject())

        })

    }

}

export function deletePost() {

    return (req, res) => {

        Post.deletePost(req.user._id, req.params.postId, (err, post) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!post)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res)

        })

    }

}

export function getLikes() {

    return (req, res) => {

        let skip = 0, limit = 50

        if (req.query.skip)
            skip = Math.max(0, req.query.skip)

        if (req.query.limit)
            limit = Math.min(Math.max(1, req.query.limit), 50)

        Post.getLikes(req.user._id, skip, limit, (err, likes) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!likes)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res, likes)

        })

    }

}

export function likePost() {

    return (req, res) => {

        Post.like(req.user._id, req.params.postId, (err, post) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!post)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res)

        })

    }

}

export function unlikePost() {

    return (req, res) => {

        Post.unlike(req.user._id, req.params.postId, (err, post) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!post)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res)

        })

    }

}

export function createComment() {

    return (req, res) => {

        if (!req.body.comment)
            return Response.sendError(res, Errors.Incomplete)

        Post.createComment(req.user._id, req.params.postId, req.body.comment, (err, post) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!post)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res)

        })

    }

}

export function getComments() {

    return (req, res) => {

        let skip = 0, limit = 10

        if (req.query.skip)
            skip = Math.max(0, req.query.skip)

        if (req.query.limit)
            limit = Math.min(Math.max(1, req.query.limit), 10)

        Post.getComments(req.params.postId, skip, limit, (err, comments) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!comments)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res, comments)

        })

    }

}

export function editComment() {

    return (req, res) => {

        if (!req.body.comment)
            return Response.sendError(res, Errors.Incomplete)

        Post.editComment(req.user._id, req.params.postId, req.params.commentId, req.body.comment, (err, post) => {

            if (err) {
                console.log(err.message)
                return Response.sendError(res, Errors.Internal)
            }

            if (!post)
                return Response.sendError(res, Errors.NotFound)

            Response.send(res)

        })

    }

}

export function deleteComment() {

    return (req, res) => {

        new Promise((resolve, reject) => {

            Post.deleteComment(req.user._id, req.params.postId, req.params.commentId, (err, post) => {

                if (err) {
                    console.log(err.message)
                    return reject(Errors.Internal)
                }

                if (!post)
                    return reject(Errors.NotFound)

                console.log(post)
                resolve(post)

            })


        }).then((post) => {

            Utils.deleteObjectS3('thespherehouse', post.url, (err, data) => {

                if (err) {
                    console.log(err.message)
                    return reject(Errors.Internal)
                }

                resolve()

            })

        }).then(() => {
            Response.send(res)
        }).catch((errorCode) => {
            Response.sendError(res, errorCode)
        })
    }

}
