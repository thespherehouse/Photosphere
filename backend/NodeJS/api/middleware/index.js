import { Router } from 'express'

const router = Router();

router.use(require('./all'));
router.use('/secure', require('./secure'));

export default router
