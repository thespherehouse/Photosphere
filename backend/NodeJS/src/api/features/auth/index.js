import { Router } from 'express'
import { checkEmail, register, login, logout, fcm, registerSocial, silentLogin } from './controller'

const router = Router()

router.post('/auth/checkEmail', checkEmail())
router.post('/auth/register', register())
router.post('/auth/registerSocial', registerSocial())

router.get('/secure/auth/silentLogin', silentLogin())

router.put('/auth/login', login())
router.put('/secure/auth/logout', logout())
router.put('/secure/auth/fcm', fcm())

export { router }
