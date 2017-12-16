import http from 'http'
import express from 'express'
import mongoose from 'mongoose'
import bluebird from 'bluebird'
import * as API from './api'
import * as Config from './config'

global.Promise = bluebird
mongoose.Promise = global.Promise

mongoose.connect(Config.MongoURI, { useMongoClient: true }, () => {
    console.log('MongoDB connected')
})

const app = express()
const server = http.createServer(app)

API.init(app, 'photosphere')

server.listen(Config.PORT, () => {
    console.log('Server started at ' + Config.PORT)
})  
