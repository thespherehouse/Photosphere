'use strict';

const router = require('express').Router();
const controller = require('./controller');

router.post('/checkEmail', controller.checkEmail());
router.post('/register', controller.register());
router.put('/login', controller.login());
router.put('/logout', controller.logout());