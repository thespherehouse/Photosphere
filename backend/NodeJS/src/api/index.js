import morgan from 'morgan'
import express from 'express'
import * as Router from './router'

export function init(app) {
    app.use(morgan('combined'))
    app.use(express.urlencoded({ extended: true }))
    app.use(express.json())
    Router.init(app)
}
