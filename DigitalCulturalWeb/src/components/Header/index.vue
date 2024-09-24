<template>
  <div id="head">
    <div class="p-header blk" id="header">
      <div class="x-wrap">
        <div>
          <div class="logo">
              <router-link to="/">
                <img 
                  src="../../assets/images/logo.png"
                  alt="logo"
                  class="wht"
                />
              </router-link>
          </div>
        </div>
    

        <!-- 导航模块 -->
        <div class="nav_main">
          <ul>
            <li
              v-for="(item, index) in headerList"
              :key="index"
              @mouseover="showIt(item, index)"
              @mouseout="noShow(item, index)"
            >
              <router-link :to="item.url">
                <a>{{ item.name }}</a>
              </router-link>
              
            </li>

          </ul>
        </div>
        
      

        <!-- 工具模块 -->
        <div style="display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  position: absolute;
  right: 33px;
  top: 0;
  height: 100%;"   class="tools">
          <ul>
            <li style="color: #595757;
  width: 76px;
  float: left;
  position: relative;
  font-size: 16px;
  cursor: pointer;
  height: 100%;
  padding: 14px 18px;
  box-sizing: border-box;" class="meg">
              <router-link  to="/cart">
                <div class="ul1" style="z-index: 999">
                  <span class="a" @click="get">购物车</span>
                </div>
              </router-link>
              
            </li>
            <li style="color: #595757;
  width: 76px;
  float: left;
  position: relative;
  font-size: 16px;
  cursor: pointer;
  height: 100%;
  padding: 14px 18px;
  box-sizing: border-box;" class="login_1">
              <router-link v-if="!this.$store.state.user.token" to="/login">
                <div class="ul1" style="z-index: 999">
                  <span   class="a">登录/注册</span>
                </div>
              </router-link>
              
              <router-link v-else to="/profile">
                <div class="ul1" style="z-index: 999"
                
                >
                  <span class="a"
                  >个人中心</span>
                </div>

              </router-link>
                

            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reqGetHeadList } from "../../api/reqGetHeadList";
// import {getInfo} from "../../api/getInfo"
// import {logout} from "../../api/logout"
export default {
  data() {
    return {
      isShow: false,

      headerList: [],
      ar: "../../assets/images/ico_user2.png",
    };
  },
  mounted() {
    reqGetHeadList().then((res) => {
      this.headerList = res.data;
      // console.log(this.headerList)
    });
  },
  methods: {
    showIt(item, index) {
      this.headerList[index].isActive = true;
      
    },
    noShow(item, index) {
      this.headerList[index].isActive = false;
    },
    get() {
      
    }

  },
};
</script>

<style scoped>


/* 注册登录样式 */

.p-header .tools ul .login_1 a {
  display: inline-block;
  background: url(../../assets/images/ico_user2.png)
    no-repeat center top/100% auto;
  width: 17px;
  height: 19px;
}

/* 白尖尖 */
.p-header.blk .tools ul li::after {
  border-top-color: #fff;
}
.p-header .tools ul li::after {
  content: "";
  border: 4px solid transparent;
  /* border-top-color: #707070; */
  margin-top: 20px;
  float: right;
}

/* tools下拉 */
.p-header .tools .ul1 {
  position: absolute;
  width: 100%;
  top: 82%;
  left: 0;
  border: 1px solid #ddd;
  border-radius: 3px;
  text-align: center;
  /* min-width: 60px; */
}
/* 下拉框样式 */
.p-header .tools .ul1 .a {
  padding-left: 10px;
  line-height: 20px;
  font-size: 20px;
  text-align: center;
}
/* 下拉字体样式 */
.p-header .tools ul .ul1 .a {
  display: block;
  padding: 5px 0;
  font-size: 10px;
}

/* //登录下拉样式 */
/* 留言板下拉 */
.meg .ul1 {
  display: none;
}
.meg:hover .ul1 {
  display: block;
}
/* 用户登录注册下拉 */
.login_1 .ul1 {
  display: none;
}
.login_1:hover .ul1 {
  display: block;
}

html,
body,
h1,
h2,
h3,
h4,
h5,
h6,
div,
ol,
ul,
li,
dl,
dt,
dd,
table,
caption,
tr,
th,
td,
span,
p,
a,
b,
u,
i,
em,
img,
iframe,
fieldset,
form,
label,
legend,
input,
button,
select,
textarea {
  margin: 0;
  padding: 0;
}

/* 导航栏总样式 */
.p-header.blk {
  position: absolute;
  background: rgba(218, 20, 20, 0.5);
  color: #ffffff;
  border: none;
  left: 0;
  top: 0;
  right: 0;
  position: relative;
  line-height: 50px;
  height: 50px;
}
/* 导航栏抬头样式 */
.p-header {
  position: relative;
  line-height: 50px;
  height: 50px;
}

.p-header .x-wrap {
  /* width: auto; */
  margin: 0 40px;
  /* position: relative; */
  height: 100%;
  z-index: 5;
}

.p-header .nav_main {
  position: absolute;
  /* left: 50%; */
  top: 0;
  left: 30%;
  height: 100%;
  /* margin-left: -400px; */
}

.p-header.blk .nav_main {
  font-size: 18px;
}
/* 社区测试 */
/* .community_test {
  color: #fff;
} */

ol,
ul,
li {
  list-style: none;
  padding: 0;
  margin: 0;
}

.p-header .nav_main ul {
  width: 1100px;
  height: 100%;
}

.p-header .nav_main ul li {
  /* position: relative; */
  float: left;
  line-height: 50px;
}

.p-header .nav_main ul a {
  padding: 0 1.3em;
  font-size: 16px;
}

.p-header .nav_main ul a {
  /* padding: 0 1.8em; */
  height: 100%;
  /* display: -webkit-box; */
  /* display: -ms-flexbox; */
  display: flex;
  -webkit-box-align: center;
  /* -ms-flex-align: center; */
  align-items: center;
  white-space: nowrap;
}

a {
  text-decoration: none !important;
  color: inherit;
}

/* 图片样式 */
img {
  border: 0;
  outline: 0;
  vertical-align: middle;
  max-width: 100%;
  max-height: 35px;
}






.p-header.blk .tools ul .meg a:after {
  background: url(../../assets/images/shopping.jpg);
  background-size: 100%;
}


.p-header .tools ul .meg a:after {
  width: 20px;
  height: 19px;
  /* background: url(../image/header/ico_tools_msg2.png); */
  border: 0;
  /* background-size: 100%; */
  content: "";
  display: inline-block;
}


</style>