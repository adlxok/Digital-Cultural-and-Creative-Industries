<template>
  <div>
    <div class="block2">
        <!-- 左边 故宫.馆藏 -->
        <div class="box">
            <a class="title"  @click="navigateToMall"  >
                <h1>
                    <span class="tit-span">
                        <!-- public-icon1.png -->
                        <img src="https://img.dpm.org.cn/Public/static/themes/image/public-icon1.png" alt="">
                    </span>
                    <span class="s1">文创</span>
                    <span class="s2">商城</span>
                    <span class="tit-span">
                        <!-- public-icon2.png -->
                        <img src="https://img.dpm.org.cn/Public/static/themes/image/public-icon2.png" alt="">
                    </span>
                </h1>
            </a>
            
        </div>
        
        <!-- 右边区域 -->
        <div class="list">
            <ul class="list flex box-mean">
                <li class="sHoverItem" style="position: relative; overflow: hidden;">
                    <!-- wan.jpg -->
                    <img alt="" title="" class=" lazyloaded" data-src="https://img.dpm.org.cn/Uploads/Picture/2022/05/31/s629590a1de712.jpg" src="https://img.dpm.org.cn/Uploads/Picture/2022/05/31/s629590a1de712.jpg">
                    <div class="mask1" style="width: 292px; height: 370px; z-index: 99; opacity: 1;">
                            <a @click="navigateToMall">
                                <p>斗彩婴戏图杯</p>
                                <p>明成化</p>
                                <p>高4.8厘米，口径6厘米，足径2.7厘米</p>
                                <p></p>
                                <p class="jj">杯外部通景绘婴戏图，一童双手持线放风筝，另一童观看，作欢舞状，其余三童持花为戏。此杯造型轻盈秀雅，胎体洁白细腻，且婴戏纹饰为成化器中少见的题材，愈益珍贵。</p>
                                <p class="pos"></p>
                                <h1></h1>
                            </a>
                            
                    </div>
                </li>
                <li class="sHoverItem" style="position: relative; overflow: hidden;">
                    <!-- ping.jpg -->
                    <img alt="" title="" class=" lazyloaded" data-src="https://img.dpm.org.cn/Uploads/Picture/2022/05/31/s629590a1df355.jpg" src="https://img.dpm.org.cn/Uploads/Picture/2022/05/31/s629590a1df355.jpg">
                    <div class="mask1" style="width: 292px; height: 370px; z-index: 99; opacity: 1;">
                        <a @click="navigateToMall">
                            <p>粉彩花卉凸三婴戏瓶</p>
                            <p>清乾隆</p>
                            <p>高21厘米，口径5.8厘米，足径7.6厘米</p>
                            <p></p>
                            <p class="jj">瓶外体施白釉，绘粉彩花卉纹，紧贴外壁以堆塑法塑出三婴嬉戏。此瓶雕塑手法细腻生动，色彩素净淡雅，堆塑的婴孩使瓶体分外新颖别致，颇具情趣。</p>
                            <p class="pos"></p>
                            <h1></h1>
                        </a>
                        
                </div>
                </li>
                <li class="sHoverItem" style="position: relative; overflow: hidden;">
                    <!-- bowl.jpg -->
                    <img alt="" title="" class=" lazyloaded" data-src="https://img.dpm.org.cn/Uploads/Picture/2022/05/31/s629590a1dfe4a.jpg" src="https://img.dpm.org.cn/Uploads/Picture/2022/05/31/s629590a1dfe4a.jpg">
                    <div class="mask1" style="width: 292px; height: 370px; z-index: 99; opacity: 1;">
                        <a @click="navigateToMall">
                            <p>青花婴戏图碗</p>
                            <p>清光绪</p>
                            <p>高7.4厘米，口径15.5厘米，足径5.8厘米</p>
                            <p></p>
                            <p class="jj">碗外壁以青花装饰，发色灰蓝，绘两组婴戏图，以树木、山石、花卉相隔。婴孩或穿肚兜，或穿宽衣肥裤，嬉戏玩耍，天真可爱。婴戏图是瓷器装饰的典型纹样之一。</p>
                            <p class="pos"></p>
                            <h1></h1>
                        </a>
                        
                </div>
                </li>
            </ul>
        </div>
    </div>                
  </div>
</template>

<script>
// import countTo from 'vue-count-to';
  export default {
  //注册组件
    // components: { countTo },
    data () {
      return {
      //数字开始
        startVal: 0,
       //数字结束
        endVal: 1863404
      }
    },
    methods: {
    navigateToMall() {
      
      this.$router.push('/mall');
    }
  }
  }

</script>

<style scoped>


.box .title h1 .s1,
.box .title h1 .s2 {
    
    color: #fff;
    font-size: 12px;
    margin-right: 48px;
    margin-top: 10px;
    padding-left: 10px;
    transition: color 0.3s ease, transform 0.3s ease;
}

/* 鼠标悬停时的样式 */
.box .title h1 .s1:hover,
.box .title h1 .s2:hover {
    color: #f51313; /* 改变文字颜色 */
    transform: scale(1.1); /* 稍微放大 */
    text-decoration: underline; /* 添加下划线 */
}

.sHoverItem {
    transition: transform 0.3s ease;
}

.sHoverItem:active {
    transform: scale(0.95); /* 点击时缩小 */
}

.list .mask1 a p {
    line-height: 25px;
    transition: color 0.3s ease, transform 0.3s ease;
}

.list .mask1 a p:hover {
    color: #ffcc00; /* 鼠标悬停时改变文字颜色 */
    transform: scale(1.05); /* 鼠标悬停时稍微放大 */
    text-decoration: underline; /* 鼠标悬停时添加下划线 */
}
body {
    font-family: 微软雅黑,'Heiti SC',黑体,Arial;
    font-size: 14px;
    color: #fff;
}
a {
    text-decoration: none !important;
}
.flex {
    display: flex;
}
img {
    border: 0;
    outline: 0;
    /* vertical-align: middle; */
    max-width: 100%;
    max-height: 100%;
}

.all-center {
    -webkit-box-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    align-items: center;
}

.block2 {
    position: relative;
    top: 0px;
    height: 350px;
    width: 1200px;
    display: -webkit-flex;
    margin: 0 auto;
    /* 测试 可删除 */
    margin-bottom: 50px;
}
.box {
    
    width: 20px;
    height: 100%;
    flex: 1;
    margin-right: 10px;
    /* object.jpg */
    background-image: url(https://img.dpm.org.cn/Public/static/themes/image/home_block2_box_bg1920.jpg);
}

.block2 .box a {
    margin-top: 10px;
}

/* 利用伪类元素设置内容 */
/* .block2 .box .title:after {
    content: "";
    display: block;
    width: 6px;
    height: 30px; */
    /* home_line.png */
    /* background: url(https://img.dpm.org.cn/Public/static/themes/image/lyc/home_line.png);
    position: absolute;
    left: 3%;
    margin-left: 110px;
    margin-top: 10px;
} */

.box .title h1{
    /* title_dot.png */
    background: url(https://img.dpm.org.cn/Public/static/themes/image/title_dot.png) center no-repeat;
    text-align: center;
    line-height: 1.2;
    font-weight: normal;
    color: #fff;
    margin-top: 133px;
}

.box .title .tit-span {
    vertical-align: middle;
}

.box .title .tit-span img {
    width: 50px;
    margin-top: -5px;
}

.box .title h1 .s1 {
    color: #fff;
    font-size: 24px;
    margin-right: 48px;
    margin-top: 10px;
    padding-left: 10px;
}
.box .title h1 .s2 {
    color: #fff;
    font-size: 24px;
    margin-right: 11px;
    margin-top: 10px;
}
.block2 .box .num {
    margin-top: 55px;
    margin-bottom: 10px;
}

.block2 .box .num #time {
    font-size: 34px;
    font-weight: bold;
    font-family: Arial, sans-serif;
    color: #fff;
}

.block2 .num p {
    float: right;
    font-size: 26px;
    width: 58px;
    height: 58px;
    line-height: 58px;
    background: url(https://img.dpm.org.cn/Public/static/themes/image/jian.png) center no-repeat;
    text-align: center;
    margin-left: 8px;
    background-size: contain;
}

.block2 .links {
    width: auto;
    height: 46%;
}
.block2 .link {
    width: 140px;
    height: 40px;
    text-align: center;
    font-size: 16px;
    line-height: 40px;
    color: #fff;
    position: relative;
    margin: 10px 0 10px;
}
.block2 .link1 {
    position: absolute;
    left: auto;
    bottom: 70px;
}
.block2 .link2 {
    position: absolute;
    left: auto;
    bottom: 15px;
} 

.list {
    position: relative;
    width: 20px;
    height: 350px;
    flex: 3;
}
.block2 .list ul {
    top: 0px;
    left: 19px;
    height: 100%;
    width: 100%;
}
.block2 .list li img {
    width: 100%;
    height: 100%;
}

.list .mask1{
    position: absolute;
    /* 将盒子定位到下面 */
    bottom: 0px;
    left: -292px;
    background-color: rgba(0, 0, 0, 0.6);
    color: #fff;
}
.list .mask1 a{
    padding: 32px 22px 0;
    display: block;
    color: #fff;
}
.list .mask1 a p {
    line-height: 25px;
}

/* 遮罩效果 */
.block2 .list ul li:hover .mask1 {
    left: 0px;
    transition: 300ms
}
.mask1 {
    left: -292px;
    transition: all 0.4s ease 0s;
}

</style>