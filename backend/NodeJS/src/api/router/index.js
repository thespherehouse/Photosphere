import Middleware from '../middleware'
import { router } from '../features'

export function init(app) {
    app.use(Middleware.all())
    app.use('/secure', Middleware.secure())
    app.use('/', router)
}
