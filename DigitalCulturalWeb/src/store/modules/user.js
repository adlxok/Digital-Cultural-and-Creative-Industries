const user = {
    state: {
        user: window.localStorage.getItem('user'),
        token: window.localStorage.getItem('token')
    },
 
    mutations: {
        //将token保存到sessionStorage里，token表示登陆状态
        SET_TOKEN: (state, data) => {
            state.token = data
            window.localStorage.setItem('token', data)
        },
        //获取用户名
        SET_USER: (state, data) => {
            // 把用户名存起来
            state.user = data
            window.localStorage.setItem('user', data)
        },
        //登出
        LOGOUT: (state) => {
            // 登出的时候要清除token
            state.token = null
            state.user = null
            window.localStorage.removeItem('token')
            window.localStorage.removeItem('user')
        }
    },
    actions: {
 
    }
};
 
export default user;