import { Router } from 'express'
import {
    createPost, getAllPosts, getPost, editPostTitle, editPostDescription,
    getLikes, createComment, getComments, likePost, unlikePost, editComment,
    deletePost, deleteComment, getAllPostsByMe
} from './controller'
import multer from 'multer'
import Middleware from '../../middleware'

const router = Router()

router.post('/', Middleware.storage(), createPost());
router.post('/:postId/comments', createComment())
router.get('/', getAllPosts());
router.get('/profile', getAllPostsByMe());
router.get('/:postId', getPost());
router.get('/:postId/likes', getLikes());
router.get('/:postId/comments', getComments())
router.put('/:postId/title', editPostTitle());
router.put('/:postId/description', editPostDescription());
router.put('/:postId/like', likePost());
router.put('/:postId/unlike', unlikePost());
router.put('/:postId/comments/:commentId', editComment())
router.delete('/:postId', deletePost())
router.delete('/:postId/comments/:commentId', deleteComment())

export { router }
