var mongoose = require("mongoose");
var Message = require("./message");
var Comment   = require("./comment");

var data = [
    {
        
		author: 
            "愿世界和平", 
        image: 
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTECPBgJGs91xof0QwCZpLsuN2UOB0bGFm6PCZu7zbH8mCgGnUSwg",
        content: 
            "随意发帖、评论，做一个祖国的良好公民。 与其看明星八卦，不如多关注一下自己的生活，have fun！",
        created: (new Date()).toLocaleString(),
    },
    {
        
		author:
            "自定义图片", 
        image: 
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQR5KH3FVRpW0nBJm0bITPbdfefRcDQAEX27ymVpEXjmgE5Ofu4Qw",
        content: 
            "暂时不能上传图片, 可以用 “鼠标右键点击网页上的图片 -> 选择 复制图片地址 -> 粘贴至ImanageUrl框里” 的方式半自定义图片",
        created: (new Date()).toLocaleString(),
    },
    {
        author: 
            "Memorial Union Terrace", 
        image:
            "https://union.wisc.edu/assets/Uploads/ImageSlides/rsz-wi-union-4723-mg-1857-58-59-60-61-fused.jpg",
        content: 
            "代表着麦屯最惬意的夏天，喝酒、游泳、划船、聊天到日落，还有每晚的志愿乐队和周一晚9点的电影. 活动及电影信息： https://union.wisc.edu/events-and-activities/special-events/terrace-after-dark-music-and-film-series/?gclid=CjwKCAjwtdbLBRALEiwAm8pA5SlQ36262GnzdQrYvYfXXxJ7RvJnA8XYZdIGPMak_39BgBbcyhUhRxoCYskQAvD_BwE#film",
        created: (new Date()).toLocaleString(),
    },
    {
        author: "烧烤圣地 Govern Nelson State Park", 
        image: 
            "https://yakimaparks.com//assets/Gilbert-Open-Space_2.jpg",
        content: "Mendota 西侧的一个小公园 20min车程，人比较少，适合烧烤、野餐、结伴玩游戏。人少，门票$8,年票$24(WI State Park通用)",
        created: (new Date()).toLocaleString(),        
    },
    {
        author: "露营圣地 Governor Dodge State Park", 
        image: 
            "http://www.campgroundreport.com/images/10c.jpg",
        content: "找几个朋友，带上零食和帐篷，准备些烧烤的食物和工具。开着车，享受一天自然的风光。预定 compsite 一天18$: https://wisconsinstateparks.reserveamerica.com/camping/governor-dodge/r/campgroundDetails.do?contractCode=WI&parkId=60015 。 公园门票$8,年票$24(WI State Park通用)",
        created: (new Date()).toLocaleString(),        
    },
    {
        author: "露营圣地 DEVILS LAKE STATE PARK", 
        image: 
            "http://countrytimegazette.com/wp-content/uploads/2011/09/Wisconsin-Camping-Checklist.jpg",
        content: "下午爬山、划船，傍晚喝酒抽烟烧烤，晚上一起狼人杀到天亮，周末还想怎么浪。但是人多，预定 compsite 一天（3p.m- 3p.m.）20$: https://www.reserveamerica.com/camping/devils-lake-state-park/r/campgroundDetails.do?contractCode=WI&parkId=60013 。 公园门票$8,年票$24(WI State Park通用)",
        created: (new Date()).toLocaleString(),        
    },
    {
        author: "Sheboygan 商业区", 
        image: 
            "https://corporate.target.com/_media/TargetCorp/Press/Corporate%20Fact%20Sheet/press-corporate-hero.jpg?width=745&height=370&ext=.jpg",
        content: "购物：Target (综合商场), Macy's(衣服化妆品), Hilldale Shopping Center (像fresh)。餐饮：Dunpling Haus （简单的中餐，推荐小笼包（小的灌汤包），酱牛肉，牛肉面，各种包子），Muramoto （日料，推荐：前菜Seaweed salad）。电影院：Madison 6  3.5miles，公交直达，免费停车。 电影showtime：https://www.amctheatres.com/movie-theatres/madison/amc-dine-in-madison-6/showtimes",
        created: (new Date()).toLocaleString(),        
    },
    {
        author: "电影院", 
        image: 
            "http://static.lakana.com/mmm-wisctv-media-us-east-1/photo/2017/04/07/amc%20imax%20theater%20fitchburg%20movie%20generic%20LRE%201280_1491606938447_6405691_ver1.0_640_360.jpg",
        content: "麦迪逊周边有5个电影院，按照服务水平和观影质量，推荐两个：1. new vision fitchburg.  7.6 miles，免费停车，麦屯最大的电影院， imax屏幕观影，爽到不行，影片全，场次多，服务好，观影体验极佳，pizza很好吃（也可能是我当时太饿了）。Imax场：普通座椅，推荐H~L排中间座位，其他的均为沙发椅。偶尔会有中文电影。Showtime: https://www.fandango.com/newvisionfitchburg18imax_aaomj/theaterpage?date 2. Madison 6，比较小，普通座椅，服务好，观影体验还不错。位于 Sheboygan 商业区 3.5miles，公交直达，免费停车。 showtime：https://www.amctheatres.com/movie-theatres/madison/amc-dine-in-madison-6/showtimes",
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
                            inputContent: "zz： 好想评论啊，但想不起来说什么",
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
