import { Router } from 'express'
import {
    createPost, getAllPosts, getPost, editPostTitle, editPostDescription,
    getLikes, createComment, getComments, likePost, unlikePost, editComment,
    deletePost, deleteComment, getAllPostsByMe
} from './controller'
import multer from 'multer'
import Middleware from '../../middleware'

const router = Router()

router.post('/secure/posts', Middleware.storage(), createPost())
router.post('/secure/posts/:postId/comments', createComment())
router.post('/secure/posts/:postId/like', likePost())

router.get('/secure/posts', getAllPosts())
router.get('/secure/posts/profile', getAllPostsByMe())
router.get('/secure/posts/:postId', getPost())
router.get('/secure/posts/:postId/likes', getLikes())
router.get('/secure/posts/:postId/comments', getComments())

router.put('/secure/posts/:postId/title', editPostTitle())
router.put('/secure/posts/:postId/description', editPostDescription())
router.put('/secure/posts/:postId/comments/:commentId', editComment())

router.delete('/secure/posts/:postId', deletePost())
router.delete('/secure/posts/:postId/unlike', unlikePost())
router.delete('/secure/posts/:postId/comments/:commentId', deleteComment())

export { router }
