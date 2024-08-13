import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

//引入路由
import router from './router'

//引入swiper样式
import "swiper/css/swiper.min.css";

import ElementUI from 'element-ui' //element-ui的全部组件
import 'element-ui/lib/theme-chalk/index.css'//element-ui的css
Vue.use(ElementUI) //使用elementUI

//引入MockServe.js-----mock数据
// import './mock/mockServer';

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')