<template>
  <div id="head">
    <div class="p-header blk" id="header">
      <div class="x-wrap">
        <div>
          <div class="logo">
            <!-- <a href="https://www.dpm.org.cn/Home.html"> -->
              <router-link to="/">
                <img
                  src="../../assets/images/logo_wt.png"
                  alt="莆田学院"
                  class="wht"
                />
              </router-link>
            <!-- </a> -->
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
              <a>{{ item.name }}</a>

              <dd
                class="item nav_child"
                v-show="item.isShow"
                style="z-index: 999"
                v-if="
                  item.options != null &&
                  item.options != undefined &&
                  item.options.length > 0
                "
              >
                <dl v-for="(item1, index) in item.options" :key="index">
                  <router-link :to="item1.toUrl"
                    ><a>{{ item1.op }}</a></router-link
                  >
                </dl>
              </dd>
            </li>

          </ul>
          
        </div>

        <!-- 工具模块 -->
        <div class="tools">
          <ul>
            <li class="meg">
              <router-link to="/meg">
                <div class="ul1" style="z-index: 999">
                  <span class="a" @click="get">留言板</span>
                </div>
              </router-link>
              
            </li>
            <li class="login_1">
              <router-link to="/login">
                <div class="ul1" style="z-index: 999">
                  <span class="a">登录/注册</span>
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
import {getInfo} from "../../api/getInfo"
export default {
  data() {
    return {
      isShow: false,
      // headerList: [
      //   {
      //     name: "",
      //     toUrl: "",
      //     isShow: "false",
      //     options: [
      //       {
      //         op: "",
      //         toUrl: "",
      //       },
      //     ],
      //   },
      // ],
      headerList: [],
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
      // console.log(this.headerList)
      // console.log("aaa", this.headerList[index].isShow); //true
      // console.log("aaa", this.headerList[index]);
      // console.log("aaa", item); //false
    },
    noShow(item, index) {
      this.headerList[index].isActive = false;
      // console.log("bbb", item);
    },
    get() {
      // console.log(sessionStorage.getItem('token'))
      // let token = sessionStorage.getItem('token')
      const token = this.$store.state.user.token;
    // console.log(token)
    console.log(this.$store.state.user.token)
      getInfo(token).then(res => (
        console.log(res)
      )).catch(e => (
        console.log(e)
      ))
      // this.$store.commit('LOGOUT')
    }
    
  },
};
</script>

<style scoped>
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
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
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

/* 工具栏样式 */
.p-header.blk .tools {
  color: #fff;
}

.p-header .tools {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  position: absolute;
  right: 33px;
  top: 0;
  height: 100%;
}

.p-header .tools ul li {
  color: #707070;
  width: 76px;
  float: left;
  position: relative;
  font-size: 16px;
  cursor: pointer;
  height: 100%;
  padding: 14px 18px;
  box-sizing: border-box;
}

.p-header.blk .tools ul .meg a:after {
  background: url(../../assets/images/ico_tools_msg.png);
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

.p-header.blk .tools ul li::after {
  border-top-color: #fff;
}

.p-header .tools ul li::after {
  content: "";
  border: 4px solid transparent;
  border-top-color: #707070;
  margin-top: 20px;
  float: right;
}

.p-header.blk .tools ul li:nth-child(n + 2)::before {
  border-color: #fff;
}

.p-header .tools ul li:nth-child(n + 2):before {
  content: "";
  border-left: 1px solid #ccc;
  position: absolute;
  left: 0;
  top: 25px;
  height: 20px;
}

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

.p-header.blk .tools ul li:nth-child(n + 2)::before {
  border-color: #fff;
}

.p-header .tools ul li:nth-child(n + 2):before {
  content: "";
  border-left: 1px solid #ccc;
  position: absolute;
  left: 0;
  top: 25px;
  height: 20px;
}

/* 注册登录样式 */
.p-header.blk .tools ul .login_1 a {
  background-image: url(../../assets/images/ico_user2.png);
}

.p-header .tools ul .login_1 a {
  display: inline-block;
  background: url(../../assets/images/ico_user1.png)
    no-repeat center top/100% auto;
  width: 17px;
  height: 19px;
}

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

.p-header.blk .tools ul .ul {
  border-color: rgba(255, 255, 255, 0.5);
  background: rgba(255, 255, 255, 0.05);
}

.p-header .tools ul li.lang {
  padding-top: 13px;
}

.p-header .tools ul .lang a {
  font-weight: bold;
  color: #fff;
}

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

.p-header .tools ul .ul {
  position: absolute;
  width: 100%;
  top: 100%;
  left: 0;
  border: 1px solid #ddd;
  border-radius: 3px;
  text-align: center;
  min-width: 60px;
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
.p-header .tools .ul1 .a {
  padding-left: 10px;
  line-height: 20px;
  font-size: 20px;
  text-align: center;
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

/* 中英文切换下拉 */
.lang .ul1 {
  display: none;
}
.lang:hover .ul1 {
  display: block;
}

/* 搜索框下拉样式 */
.p-header .tools ul .sc .search {
  display: none;
  border: 1px solid #ddd;
  position: absolute;
  height: 20px;
  padding: 5px 20px 5px 10px;
  background: #fff;
  border-radius: 3px;
  margin-top: 5px;
  top: 64px;
  left: -160px;
}
.p-header .tools ul .sc:hover .search {
  display: block;
}
.p-header .tools ul .sc .search .kw {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  display: block;
  color: #707070;
  font-size: 14px;
  width: 180px;
}
.p-header .tools ul .sc .search .ok {
  /* display: none; */
  display: block;
  position: absolute;
  top: 9px;
  right: 0px;
  width: 40px;
  background: url(https://img.dpm.org.cn/Public/static/themes/image/header/ico_search.png)
    no-repeat center center;
  cursor: pointer;
}

input,
button,
textarea,
select {
  font: 14px/100% "Microsoft YaHei", Arial, Tahoma, Helvetica, sans-serif;
  outline: 0;
  border: 0;
  background: none;
  color: #707070;
}

.p-header .tools ul li.sc {
  height: 55px;
}

.p-header.blk .tools ul .sc a {
  background-image: url(https://img.dpm.org.cn/Public/static/themes/image/header/ico_search2.png);
}

.p-header .tools ul .sc a {
  display: inline-block;
  background: url(https://img.dpm.org.cn/Public/static/themes/image/header/ico_search1.png)
    no-repeat center top/100% auto;
  width: 17px;
  height: 17px;
}

.tools ul li .ul1 .a {
  border-color: rgba(255, 255, 255, 0.5);
}

.p-header .tools ul .ul1 .a {
  display: block;
  padding: 5px 0;
  font-size: 10px;
}

.p-header.blk .tools .menu1 {
  background-image: url(https://img.dpm.org.cn/Public/static/themes/image/header/ico_menu2.png);
}

.p-header .tools .menu1 {
  cursor: pointer;
  background: url(https://img.dpm.org.cn/Public/static/themes/image/header/ico_menu1.png)
    no-repeat center top/100% auto;
  width: 34px;
  height: 34px;
  display: block;
}

.nav_child ul a {
  padding: 0px 1.3em;
  font-size: 17px;
}

.p-header .nav_main ul li.cur:after {
  content: "";
  border: 6px solid transparent;
  border-bottom-color: rgba(151, 115, 67, 0.7);
  position: absolute;
  left: 50%;
  bottom: 0;
  margin-left: -6px;
}

.p-header .nav_child {
  position: absolute;
  width: 100%;
  margin-left: -500px;
  text-align: center;
  padding-left: 475px;
  /* left: -687px; */
  /* margin: 0 auto; */
  top: 100%;
  left: 0;
  overflow: hidden;
  /* right: 0; */
  background: rgba(151, 115, 67, 0.6);
}

.p-header .nav_child dl {
  /* height: 0; */
  overflow: hidden;
  float: left;

  color: #fff;
  text-align: center;
  /* padding: 0px 20.3em; */
  /* font-size: 16px; */
}
.p-header .nav_child dl a {
  font-size: 13px;
}

.p-header .nav_child .item {
  height: 0;
  /* overflow: hidden; */
  font-size: 14px;
  color: #fff;
  text-align: center;
}
</style>