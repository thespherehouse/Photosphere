import Router from 'express'
import Controller from './controller'
import multer from 'multer'
import { Storage } from '../../helper'

const router = Router()

router.post('/', Storage.uploader, Controller.createPost);
router.get('/', Controller.getAllPosts);
router.get('/:postId', Controller.getPost);
router.get('/:postId/likes', Controller.getLikes);
router.put('/:postId', Controller.editPost);
router.put('/:postId/like', Controller.likePost);
router.put('/:postId/unlike', Controller.unlikePost);
router.delete('/:postId', Controller.removePost);

export default router
