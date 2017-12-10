import { Router } from 'express'
import Controller from './controller'

const router = Router()

router.post('/checkEmail', Controller.checkEmail())
router.post('/register', Controller.register())
router.put('/login', Controller.login())
router.put('/logout', Controller.logout())
router.put('/fcm', Controller.fcm)

export default router
