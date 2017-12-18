import * as Auth from './auth'
import * as Posts from './posts'
import { Router } from 'express'

const router = Router()

router.use('/', Auth.router, Posts.router)

export { router }
