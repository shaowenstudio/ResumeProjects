var mongoose = require('mongoose');
var Schema = mongoose.Schema;
// var Message = require('./message');

var schema = new Schema({
    inputContent: {type: String, required: true},
    created: {type: String, required: false},
    theMessage: {type: Schema.Types.ObjectId, ref: 'Message'}
});

// schema.post('remove', function (message) {
//     User.findById(message.user, function (err, user) {
//         user.messages.pull(message);
//         user.save();
//     });
// });

module.exports = mongoose.model('Comment', schema);