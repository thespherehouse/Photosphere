import Router from 'express'
import * as Controllers from './controller'
import * as multer from 'multer'
import { uploader } from '../../utils/storage'

const router = Router()

router.post('/', uploader, Controllers.createPost);
router.get('/', Controllers.getAllPosts);
router.get('/:postId', Controllers.getPost);
router.get('/:postId/likes', Controllers.getLikes);
router.put('/:postId', Controllers.editPost);
router.put('/:postId/like', Controllers.likePost);
router.put('/:postId/unlike', Controllers.unlikePost);
router.delete('/:postId', Controllers.removePost);

export default router
