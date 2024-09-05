import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

//引入路由
import router from './router'
import store from './store'

//引入swiper样式
import "swiper/css/swiper.min.css";


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

//引入wangeditor样式
import '@wangeditor/editor/dist/css/style.css'


//引入MockServe.js-----mock数据
// import './mock/mockServer';

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')