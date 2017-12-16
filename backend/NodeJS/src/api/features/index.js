import * as Auth from './auth'
import * as Posts from './posts'
import { Router } from 'express'

const router = Router()

router.use('/auth', Auth.router)
router.use('/secure/posts', Posts.router)

export { router }
