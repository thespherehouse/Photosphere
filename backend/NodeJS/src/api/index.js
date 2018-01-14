import morgan from 'morgan'
import express from 'express'
import * as Features from './features'

export function init(server, app, baseUrl) {
    app.use(`/${baseUrl}/api/v1`, morgan('combined'))
    app.use(`/${baseUrl}/api/v1`, express.urlencoded({ extended: true }))
    app.use(`/${baseUrl}/api/v1`, express.json())
    app.use(`/${baseUrl}/api/v1`, Features.route())
    Features.realtime().init(server)
}
