import morgan from 'morgan'
import express from 'express'
import * as Router from './router'

export function init(app, baseUrl) {
    app.use(`/${baseUrl}`, morgan('combined'))
    app.use(`/${baseUrl}`, express.urlencoded({ extended: true }))
    app.use(`/${baseUrl}`, express.json())
    Router.init(app, baseUrl)
}
