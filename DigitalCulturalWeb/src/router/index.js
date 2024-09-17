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
            path: '/mall',
            name: 'mall',
            component:() => import('../views/mall/index.vue')
        },
        {
            path: '/mall/products/:id',
            name: 'ProductDetail',
            component:() => import('../views/ProductDetail/index.vue')
        },
        {
            path: '/cart',
            name: 'cart',
            component:() => import('../views/cart/index.vue')
        },
        {
            path: '/profile',
            name: 'profile',
            component:() => import('../views/profile/index.vue')
        },
        {
            path: '/create',
            name: 'create',
            meta: { hideHeaderFooter: true },
            component:() => import('../views/create/index.vue')
        },
    ]
})