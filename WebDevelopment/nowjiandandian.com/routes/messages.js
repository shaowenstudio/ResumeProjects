var express = require('express');
var router = express.Router();

var Message = require('../models/message');
var Comment = require('../models/comment');

router.get('/', function (req, res, next) {
    Message.find()
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
    var message = new Message({
        content: req.body.content,
        author: req.body.author,
        image: req.body.image,
        created: req.body.created,
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


router.delete('/:id', function(req, res, next) {
    // console.log('deleting message');
    Message.findById(req.params.id, function (err, message) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        if (!message) {
            return res.status(500).json({
                title: 'No Message Found!',
                error: {message: 'Message not found'}
            });
        }
        message.remove(function(err, result) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Deleted message',
                obj: result
            });
        });
    });
    // console.log('deleting comment');
    // Comment.find({"theMessage": req.params.id}, function (err, comments) {
    //     if (err) {
    //         return res.status(500).json({
    //             title: 'An error occurred',
    //             error: err
    //         });
    //     }
    //     if (!comments) {
    //         return res.status(500).json({
    //             title: 'No comments Found!',
    //             error: {message: 'comments not found'}
    //         });
    //     }
    //     for (i = 0; i < comments.length; i++) { 
    //         console.log(i + ' ----- :' + comments[i])
    //         comments[i].remove(function(err, result) {
    //             if (err) {
    //                 return res.status(500).json({
    //                     title: 'An error occurred',
    //                     error: err
    //                 });
    //             }
    //             res.status(200).json({
    //                 message: 'Deleted comments',
    //                 obj: result
    //             });
    //         });
    //     }
    // });
});

// comment routes
// router.delete('/:id', function(req, res, next) {
//     console.log('req.body.commentId: ' + req.body.commentId);
//     Comment.findById(req.body.commentId, function (err, comment) {
//         if (err) {
//             return res.status(500).json({
//                 title: 'An error occurred',
//                 error: err
//             });
//         }
//         if (!comment) {
//             return res.status(500).json({
//                 title: 'No Comment Found!',
//                 error: {comment: 'Comment not found'}
//             });
//         }
//         comment.remove(function(err, result) {
//             if (err) {
//                 return res.status(500).json({
//                     title: 'An error occurred',
//                     error: err
//                 });
//             }
//             res.status(200).json({
//                 message: 'Deleted comment',
//                 obj: result
//             });
//         });
//     });
// });

router.get('/:id', function (req, res, next) {
    Comment.find({"theMessage": req.params.id})
        .exec(function (err, comments) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Success',
                obj: comments
            });
        });
});

router.post('/:id', function (req, res, next) {
    Message.findById(req.params.id, function (err, message) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
        var comment = new Comment({
            inputContent: req.body.inputComment,
            created: req.body.created,
            theMessage: message,
        });
        comment.save(function (err, result) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            message.comments.push(result); // save comment to message
            message.save();
            res.status(201).json({
                message: 'Saved message',
                obj: result
            });
        });
    });
});

module.exports = router;