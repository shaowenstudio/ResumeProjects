var express = require('express');
var router = express.Router();

var Comment = require('../models/comment');
var Message = require('../models/message');

router.get('/', function (req, res, next) {
    console.log('--------comment get /');
    Comment.find()
        .exec(function (err, messages) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Success',
                obj: messages
            });
        });
});

router.post('/', function (req, res, next) {
    console.log('--------post /');
    var message = new Message({
        inputContent: req.body.inputComment,
        created: (new Date()).toLocaleString('en-US', { timeZone: 'America/Chicago' }),
        // created: Date.now,
    });
    message.save(function (err, result) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        res.status(201).json({
            message: 'Saved message',
            obj: result
        });
    });
});

router.post('/:id', function (req, res, next) {
    console.log('--------post /:id');
    var message = new Message({
        inputContent: req.body.inputComment,
        created: (new Date()).toLocaleString('en-US', { timeZone: 'America/Chicago' }),
        // created: Date.now,
    });
    message.save(function (err, result) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        res.status(201).json({
            message: 'Saved message',
            obj: result
        });
    });
});

router.post('/message/', function (req, res, next) {
    console.log('--------post /message/');
    var message = new Message({
        inputContent: req.body.inputComment,
        created: (new Date()).toLocaleString('en-US', { timeZone: 'America/Chicago' }),
        // created: Date.now,
    });
    message.save(function (err, result) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        res.status(201).json({
            message: 'Saved message',
            obj: result
        });
    });
});

router.post('/message/:id', function (req, res, next) {
    console.log('--------post /message/:id');
    var message = new Comment({
        inputContent: req.body.inputComment,
        created: (new Date()).toLocaleString('en-US', { timeZone: 'America/Chicago' }),
    });
    message.save(function (err, result) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        res.status(201).json({
            message: 'Saved message',
            obj: result
        });
    });
});

module.exports = router;



