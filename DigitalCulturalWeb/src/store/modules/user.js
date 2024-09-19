
const user = {
    state: {
        userId: window.localStorage.getItem('userId'),
        token: window.localStorage.getItem('token'),
        avatar: window.localStorage.getItem('avatar'),
    },
 
    mutations: {
        //将token保存到sessionStorage里，token表示登陆状态
        SET_TOKEN: (state, data) => {
            state.token = data
            window.localStorage.setItem('token', data)
        },
        //获取用户名
        SET_USERID: (state, data) => {
            // 把用户名存起来
            state.userId = data
            window.localStorage.setItem('userId', data)
        },
        SET_AVATAR: (state, data) => {
            state.avatar = data
            window.localStorage.setItem('avatar', data)
        },
        //登出
        LOGOUT: (state) => {
            // 登出的时候要清除token
            state.token = null
            state.userId = null
            state.avatar = null
            window.localStorage.removeItem('token')
            window.localStorage.removeItem('userId')
            window.localStorage.removeItem('avatar')
        }
    },
    actions: {
 
    }
};
 
export default user;