import { Router } from 'express'
import * as Auth from './auth'
import * as Posts from './posts'

const router = Router()

router.use('/auth', Auth.default)
router.use('/posts', Posts.default)

export default router
