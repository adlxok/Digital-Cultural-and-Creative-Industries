import Vue from 'vue';
import VueRouter from 'vue-router';
//使用插件
Vue.use(VueRouter);


//引入路由组件
// import Home from '../views/Home/index.vue'
// import Detail from '../views/Detail/index.vue'

//对外暴露VueRouter类的实例
export default new VueRouter({
    routes: 
    [
        {
            path: '*',
            redirect: '/home'
        },
        {
            path: '/home',
            name: 'Home',
            component: () => import('../views/Home/index.vue')
        },
        {
            path: '/detail',
            name: 'Detail',
            component: () => import('../views/Home/index.vue')
        },
        {
            path: '/login',
            name: 'login',
            component:() => import('../views/login/index.vue')
        },
        {
            path: '/meg',
            name: 'meg',
            component:() => import('../views/meg/index.vue')
        },
        {
            path: '/comhome',
            name: 'Comhome',
            component:() => import('../views/Community/post/index.vue')
        },
        {
            path: '/addpost',
            name: 'Addpost',
            component:() => import('../views/Community/addpost/index.vue')
        },
        {
            path: '/postDetail',
            name: 'PostDetail',
            component:() => import('../views/Community/postDetail/index.vue')
        }
    ]
})