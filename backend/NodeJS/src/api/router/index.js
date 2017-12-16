import Middleware from '../middleware'
import { router } from '../features'

export function init(app, baseUrl) {
    app.use(`/${baseUrl}`, Middleware.all())
    app.use(`/${baseUrl}/secure`, Middleware.secure())
    app.use(`/${baseUrl}/`, router)
}
