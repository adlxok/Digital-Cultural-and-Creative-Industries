<template>
    <div class="left-box">
        <div class="center">
            <!-- 故宫资讯 -->
            <div class="info">
                <!-- 文本内容 -->
                <div class="text">
                    <div class="text-content">
                        <div class="title-con-1">
                            <span>社区看点</span>
                            <h1>
                                <a href="###">
                                    <span class="tit-left"><img :src="title.titleft"></span>
                                    <span class="t1">{{title.t1}}</span>
                                    <span class="t2">{{title.t2}}</span>
                                    <span class="tit-right"><img :src="title.titright"></span>
                                </a>
                            </h1>
                            <a href="http://localhost:9876/Comhome#/comhome" class="more">
                                更多
                                <i></i>
                            </a>
                        </div>
                        <div class="con-1">
                            <div class="item1-con-1">
                                <el-image style="width: 100px; height: 100px"
                                    src="https://img.phb01.com/d/file/p/2021/08-13/57-200FQ10A4300.jpg"
                                    fit="cover"></el-image>
                                <span class="item1-span-con-1">笔墨丹青篇</span>
                                <span
                                    class="item1-span2-con-1">王羲之《兰亭序》颜真卿《祭侄文稿》苏轼《黄州寒食帖》欧阳询《仲尼梦奠帖》怀素《自叙帖》...</span>
                            </div>

                        </div>
                        <div class="con-2">
                                <div class="hotpost-con-1" v-for="post in shortPost" :key="post.id">
                                       <li> <router-link :to="{ path: '/postDetail', query: { postId: post.id } }">
                                            <span> {{ post.title }}</span>
                                        </router-link>
                                    </li>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 图片 -->
                <div class="image">
                    <a href="###">
                        <div class="img"></div>
                    </a>
                </div>
            </div>
            <!-- 数字多宝阁 -->
            <div class="num">
                <img src="https://img.dpm.org.cn/Uploads/Picture/2022/05/11/s627b4ef388b86.jpg">
            </div>
            <div class="cv" id="pos">
                <img src="https://img.dpm.org.cn/Uploads/Picture/2022/05/11/s627b4ede8e905.png" alt="" ref="cv">
                <canvas width="758" height="846" tabindex="0"
                    style="width: 379px; height: 423.2px; outline: none;"></canvas>
            </div>
        </div>
    </div>
</template>

<script>
import { hotPost } from'../../../../api/post'
export default {
    name:'LeftSection',
    props:["title","con1List","con2List"],
    data() {
        return {
        hotPost:[],
        shortPost:[]
        }
    },
    created() {
    this.fetchhotPost();
    
    },
    methods: {   
    fetchhotPost(){
	hotPost().then(response => {
	this.hotPost=response.data;
    this.shortPost = this.hotPost.slice(0, 8);
	}).catch(error => {  
        console.error('获取热帖子列表失败:', error);   
    });
  }, }
    
 
  
}

</script>

<style scoped>
.con-2 li{
    font-size: 16px;
    color: #fff;
    padding: 5px;
}
.con-2 span{
    font-size: 15px;
    color: #FFFFFF;
}
.con-2 span:hover{
    color:rgb(204, 109, 14);
	font-size: 18px;
}

.title-con-1 span{
    color: #ddd;
    vertical-align: middle;
    display: inline-block;  
    font-size:18px;
    font-weight: 550;
}
.item1-con-1{
	display: flex;;
	height: 100px;
	width: 350px;
	/* border: 1px solid #ddd; */
	
}
.item1-span-con-1{
	font-size: 16px;
	color: #ddd;
	margin-left: 10px;
	font-weight:550;
	margin-top: 15px;
}
.item1-span2-con-1{
	position: absolute;
	margin-left: 110px;
	font-size: 12px;
	margin-top: 45px;
	margin-right: 310px;
	color: #ddd;
}
.left-box{
    width: 60%;
    height: 850px;
}
.center{
    width: calc(100% - 25px);
    height: 100%;
    position: relative;
}
.info{
    width: 100%;
    height: 55%;
    display: flex;
}
.num{
    width: 100%;
    height: calc(45% - 25px);
    margin-top: 25px;
    background-color: #ccc;
}
.num img{
    vertical-align: middle;
    width: 100%;
    height: 100%;
}
.text{
    width: 57%;
    height: calc(100% - 25px);
    padding-top: 25px;
    background: url(https://img.dpm.org.cn/Public/static/themes/image/home_block1_news_bg.jpg);
    transition: opacity 0.5s ease-out 0.6s, transform 1s ease-out 0.6s;
}
.text-content{
    width: calc(100% - 50px);
    height: calc(100% - 25px);
    margin-left: 25px;
}
.title-con-1 h1{
    background: url(https://img.dpm.org.cn/Public/static/themes/image/title_dot.png) no-repeat center center;
    font-size: 0;
    display: inline-block; 
    line-height: 0;
    padding-top: 15px;
}
.title-con-1 h1 span{
    color: #fff;
    vertical-align: middle;
    display: inline-block;  
    font-size:28px;
    font-weight: 500;
}
.title-con-1 h1 .t1{
    margin-right: 0;
    margin: 0 48px 0 0;
}

.tit-left img,.tit-right img{
    width: 24px;
}
.tit-left img{
    margin-right: 10px;
}
.tit-right img{
    margin-left: 10px;
}
.title-con-1{
    position: relative;
}
.more{
    font-family: fzfysjt;
    position: absolute;
    right: 0;
    top: 0;
    color: #fff;
    line-height: 1;
    width: 1em;
    padding: 10px 17px;

}
.more i{
    position: absolute;
    left: 0;
    top: 0;
    background: url(https://img.dpm.org.cn/Public/static/themes/image/more.png) no-repeat;
    width: 48px;
    height: 100%;
}
.more i:hover{
    transform: rotate(-180deg);
    transition: all 1s;
}
.con-1{
    color: #fff;
    width: 100%;
    height: 114px;
    border-bottom: 1px solid rgba(255,255,255,0.15);   
    margin-top: 15px;
    padding-bottom: 10px;
}
.con-1-content em{
    width: 19px;
    height: 13px;
    display: block;
}
.con-1-content em img{
    width: 19px;
    height: 16px;
    padding-top: 15px;
    display: block;
}
.con-1-content span{
    width: 95%;
    color: #fff;
    display: block;
    margin-left: 25px;
    font-size: 15px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}

.con-2{
    width: 100%;
}
.con-2 ul{
    width: 100%;
    padding-top: 10px;
}

.con-2 ul li{
    padding-left: 5px;
    color: #fff;
    font-size: 12px;
    margin: 12px 0;
    position: relative;
    overflow: hidden;
    /* 显示小圆点 */
    list-style-position: inside;
}

.con-2 ul li p{
    color: #fff;
    float: right;
}
.con-2 ul li p span{
    color: #fff;
    font-size: 22px;
    line-height: 1;
}
.con-2 ul li a{
    max-width: 75%;
    display: block;
    color: #fff;
    line-height: 22px;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    position: absolute;
    top: 0;
    text-indent: 12px;
}
.hotpost ul li a:hover{
    color: #f3eb96;
}
.con-2 ul li em{
    color: #fff;
    position: absolute;
    top: 0;
    right: 20%;
    font-style: normal;
    margin-top: 2px;
    background-color: #b73736;
    color: #fff;
    display: inline-block;
    width: 20px;
    height: 20px;
    font-size: 14px;
    text-align: center;
    line-height: 20px;
    border-radius: 3px;
}
.image{
    width: 43%;
    height: 100%;
    overflow: hidden;
    
}
.img{
    background: url(https://img.zcool.cn/community/jin_product/6fff5809e93fa84a192fc756d71f.jpg?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100/quality,q_100) no-repeat center center / cover;
    
    width: 100%;
    height: 100%;
}
.img:hover{
    transform: scale(1.1);
    transition: all 1s cubic-bezier(0, 1, 1, 1) 0s;
}
.cv{
    position: absolute;
    bottom: 0;
    width: 45%;
    transform-style: preserve-3d;
    opacity: 1;
}
.cv img{
    max-width: 100%;
    max-height: 100%;
    padding-left: 20px;
    vertical-align: middle;
    
}

.cv canvas{
    position: absolute;
    left: 0%;
    bottom: 0%;
}
</style>