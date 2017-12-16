import { Router } from 'express'
import { checkEmail, register, login, logout, fcm } from './controller'

const router = Router()

router.post('/checkEmail', checkEmail())
router.post('/register', register())
router.put('/login', login())
router.put('/logout', logout())
router.put('/fcm', fcm())

export { router }
