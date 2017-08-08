var mongoose = require("mongoose");
var Message = require("./message");
var Comment   = require("./comment");

var data = [
    {
        author: "Govern Nelson State Park", 
        image: 
            "http://shaowenstudio.com/images/volleballSky.jpg",
        content: "好玩的：Mendota 西侧的一个小公园 20min车程，人比较少，适合烧烤、野餐、结伴玩游戏。门票$8,年票$24(WI State Park通用)",
        created: (new Date()).toLocaleString(),        
    },
    {
        
		author: 
            "愿世界和平", 
        image: 
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTECPBgJGs91xof0QwCZpLsuN2UOB0bGFm6PCZu7zbH8mCgGnUSwg",
        content: 
            "随意发帖、评论，have fun！作一个祖国的良好公民",
        created: (new Date()).toLocaleString(),
    },
    {
        
		author:
            "自定义你的图片", 
        image: 
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQR5KH3FVRpW0nBJm0bITPbdfefRcDQAEX27ymVpEXjmgE5Ofu4Qw",
        content: 
            "暂时不能上传图片 :(，内存扛不住， 不过可以用 “鼠标右键点击网页上的图片 -> 选择 复制图片地址 -> 粘贴至ImanageUrl框里” 的方式半自定义图片",
        created: (new Date()).toLocaleString(),
    },
    {
        author: 
            "UW-M Terrace", 
        image:
            "https://union.wisc.edu/assets/Uploads/ImageSlides/rsz-wi-union-4723-mg-1857-58-59-60-61-fused.jpg",
        content: 
            "代表了麦屯最惬意的夏天，喝酒、游泳、划船、聊天到日落，还有每晚的志愿乐队和周一晚上的电影.",
        created: (new Date()).toLocaleString(),
    },
]

function seedDB(){
   //Remove all comments
   Comment.remove({}, function(err){
       if(err){
            console.log(err);
        }
   });
   //Remove all messages
   Message.remove({}, function(err){
        if(err){
            console.log(err);
        }
         //add a few posters
        data.forEach(function(seed){
            Message.create(seed, function(err, message){
                if(err){
                    console.log(err)
                } else {
                    //create a comment
                    Comment.create(
                        {
                            inputContent: "小明： 好想说话啊，但想不起来说什么",
                            created: (new Date()).toLocaleString(),
                            theMessage: message._id
                        }, function(err, comment){
                            if(err){
                                console.log(err);
                            } else {
                                message.comments.push(comment);
                                message.save();
                            }
                        });
                }
            });
        });
    }); 
}

module.exports = seedDB;
