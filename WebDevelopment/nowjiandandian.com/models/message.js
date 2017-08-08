var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var schema = new Schema({
    content: {type: String, required: true},
    author: {type: String, required: false},
    image: {type: String, required: false},
    // comments: {type: []},   
    comments: [
        {
            type: Schema.Types.ObjectId, 
            ref: 'Comment'
        }
    ],    
    created: {type: String},
    
});

module.exports = mongoose.model('Message', schema);