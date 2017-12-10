'use strict';

const express = require('express');
const morgan = require('morgan');
const http = require('http');
const mongoose = require('mongoose');
const bluebird = require('bluebird');
const api = require('./api');
const config = require('./config');

global.Promise = bluebird;
mongoose.Promise = global.Promise;

mongoose.connect(config.mongoUri, { useMongoClient: true }, function () {
    console.log('MongoDB connected');
});

const app = express();

// app.use(morgan('combined'));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use('/', api.Middleware);
app.use('/', api.Routes);
app.use(function (err, req, res, next) {
    console.log(err.message);
    api.Response.sendError(res, api.Response.Errors.Internal);
});

const server = http.createServer(app);

server.listen(config.port, function () {
    console.log('Server started at ' + config.port);
});
