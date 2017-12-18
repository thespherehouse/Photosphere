import { Router } from 'express'
import * as Auth from './auth'
import * as Posts from './posts'
import Middleware from '../middleware'

const router = Router()

// Auth
router.post('/auth/register', Auth.Validator.register(), Auth.Endpoint.register())

router.get('/auth/checkEmail/:email', Auth.Endpoint.checkEmail())

router.put('/auth/login', Auth.Endpoint.login())
router.put('/auth/socialLogin', Auth.Validator.socialLogin(), Auth.Endpoint.socialLogin())
router.put('/auth/silentLogin', Middleware.secure(), Auth.Endpoint.silentLogin())
router.put('/auth/logout', Middleware.secure(), Auth.Endpoint.logout())
router.put('/auth/fcm', Middleware.secure(), Auth.Validator.fcm(), Auth.Endpoint.fcm())

// Posts
router.post('/posts', Middleware.secure(), Posts.Validator.createPost(), Middleware.storage(), Posts.Endpoint.createPost())
router.post('/posts/:postId/comments', Middleware.secure(), Posts.Validator.createComment(), Posts.Endpoint.createComment())
router.post('/posts/:postId/like', Middleware.secure(), Posts.Endpoint.likePost())

router.get('/posts', Middleware.secure(), Posts.Validator.getAllPosts(), Posts.Endpoint.getAllPosts())
router.get('/posts/profile', Middleware.secure(), Posts.Validator.getMyPosts(), Posts.Endpoint.getMyPosts())
router.get('/posts/by/:userId', Middleware.secure(), Posts.Validator.getAllPostsByUser(), Posts.Endpoint.getAllPostsByUser())
router.get('/posts/:postId', Middleware.secure(), Posts.Endpoint.getPost())
router.get('/posts/:postId/likes', Middleware.secure(), Posts.Validator.getLikes(), Posts.Endpoint.getLikes())
router.get('/posts/:postId/comments', Middleware.secure(), Posts.Validator.getComments(), Posts.Endpoint.getComments())

router.put('/posts/:postId/title', Middleware.secure(), Posts.Validator.editPostTitle(), Posts.Endpoint.editPostTitle())
router.put('/posts/:postId/description', Middleware.secure(), Posts.Validator.editPostDescription(), Posts.Endpoint.editPostDescription())
router.put('/posts/:postId/comments/:commentId', Middleware.secure(), Posts.Validator.editComment(), Posts.Endpoint.editComment())

router.delete('/posts/:postId', Middleware.secure(), Posts.Endpoint.deletePost())
router.delete('/posts/:postId/unlike', Middleware.secure(), Posts.Endpoint.unlikePost())
router.delete('/posts/:postId/comments/:commentId', Middleware.secure(), Posts.Endpoint.deleteComment())

export function route() {
    return router
}
