import axios from 'axios'
// import router from "../router";
// import {logout} from "../api/logout"
import store from '@/store'

const request = axios.create({
    baseURL: '/api',
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    //取出sessionStorage里面缓存的用户信息  
    const token = store.state.user.token;
    // const token = sessionStorage.getItem('token')
    // console.log(token)
    if(token)
    {
        // 设置请求头
        config.headers['token'] = token;
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (res.code === 200) {
            // 如果是返回的文件
            if (response.config.responseType === 'blob') {
                return res
            }
            // 兼容服务端返回的字符串数据
            if (typeof res === 'string') {
                res = res ? JSON.parse(res) : res
            }
            return res;
        } else {
            // 暂未登录或token已过期 || 用户未登录没有权限
            if (res.code === 401 || res.code === 403) {
                // sessionStorage.removeItem('token')
                // logout().then();
                store.commit('LOGOUT');
                // router.push('/login')
            }
            return Promise.reject(new Error(res.message || 'Error'))
        }
        
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request

