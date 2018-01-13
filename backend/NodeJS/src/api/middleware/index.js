import secure from './secure'
import storage from './storage'
import all from './all'

const paginator = (def, max) => {
    return (req, res, next) => {
        let skip = 0, limit = def

        if (req.query.skip)
            skip = Math.max(0, req.query.skip)

        if (req.query.limit)
            limit = Math.min(Math.max(1, req.query.limit), max)

        req.query.skip = skip
        req.query.limit = limit
        next()
    }
}

export default { secure, storage, all, paginator }
