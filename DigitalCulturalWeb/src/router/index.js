// import Vue from 'vue';
// import VueRouter from 'vue-router';
// //使用插件
// Vue.use(VueRouter);


// //引入路由组件
// // import Home from '../views/Home/index.vue'
// // import Detail from '../views/Detail/index.vue'

// //对外暴露VueRouter类的实例
// export default new VueRouter({
//     routes: 
//     [
//         {
//             path: '*',
//             redirect: '/home'
//         },
//         {
//             path: '/home',
//             name: 'Home',
//             component: () => import('../views/Home/index.vue')
//         },
//         {
//             path: '/detail',
//             name: 'Detail',
//             component: () => import('../views/Home/index.vue')
//         },
//         {
//             path: '/login',
//             name: 'login',
//             component:() => import('../views/login/index.vue')
//         },
//         {
//             path: '/meg',
//             name: 'meg',
//             component:() => import('../views/meg/index.vue')
//         },
//         {
//             path: '/comhome',
//             name: 'Comhome',
//             meta: { hideHeaderFooter: true },
//             component:() => import('../views/Community/post/index.vue')
//         },
//         {
//             path: '/addpost',
//             name: 'Addpost',
//             component:() => import('../views/Community/addpost/index.vue')
//         },
//         {
//             path: '/postDetail',
//             name: 'PostDetail',
//             component:() => import('../views/Community/postDetail/index.vue')
//         },
//         {
//             path: '/comprofile',
//             name: 'ComProfile',
//             meta: { hideHeaderFooter: true },
//             component:() => import('../views/Community/comprofile/index.vue')
//         },
//         {
//             path: '/aloneprofile',
//             name: 'AloneProfile',
//             meta: { hideHeaderFooter: true },
//             component:() => import('../views/Community/aloneprofile/index.vue')
//         },
//         {
//             path: '/message',
//             name: 'Message',
//             meta: { hideHeaderFooter: true },
//             component:() => import('../views/Community/message/index.vue')
//         },
//         {
//             path: '/notice',
//             name: 'Notice',
//             component:() => import('../views/Community/notice/index.vue')
//         },
//         {   path: '/mall',
//             name: 'mall',
//             component:() => import('../views/mall/index.vue')
//         },
//         {
//             path: '/mall/products/:id',
//             name: 'ProductDetail',
//             component:() => import('../views/ProductDetail/index.vue')
//         },
//         {
//             path: '/cart',
//             name: 'cart',
//             component:() => import('../views/cart/index.vue')
//         },
//         {
//             path: '/profile',
//             name: 'profile',
//             component:() => import('../views/profile/index.vue')
//         },
//         {
//             path: '/create',
//             name: 'create',
//             meta: { hideHeaderFooter: true },
//             component:() => import('../views/create/index.vue')
//         },

//     ] 
// })

import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store'

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
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
                        meta: { hideHeaderFooter: true },
                        component:() => import('../views/Community/post/index.vue')
                    },
                    {
                        path: '/addpost',
                        name: 'Addpost',
                        meta: { requiresAuth: true },
                        component:() => import('../views/Community/addpost/index.vue')
                    },
                    {
                        path: '/postDetail',
                        name: 'PostDetail',
                        component:() => import('../views/Community/postDetail/index.vue')
                    },
                    {
                        path: '/comprofile',
                        name: 'ComProfile',
                        meta: { hideHeaderFooter: true, requiresAuth: true  },
                        component:() => import('../views/Community/comprofile/index.vue')
                    },
                    {
                        path: '/aloneprofile',
                        name: 'AloneProfile',
                        meta: { requiresAuth: true },
                        component:() => import('../views/Community/aloneprofile/index.vue')
                    },
                    {
                        path: '/message',
                        name: 'Message',
                        meta: { hideHeaderFooter: true, requiresAuth: true  },
                        component:() => import('../views/Community/message/index.vue')
                    },
                    {
                        path: '/notice',
                        name: 'Notice',
                        meta: { requiresAuth: true },
                        component:() => import('../views/Community/notice/index.vue')
                    },
                    {   path: '/mall',
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
});

// 路由守卫
router.beforeEach((to, from, next) => {
    const isAuthenticated = !!store.state.user.token; // 检查是否登录

    if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {
        next({ name: 'login' }); // 未登录重定向到登录页面
    } else {
        next(); // 继续导航
    }
});

export default router;

