import request from '@/utils/request'

export function login(username, password) {
    return request({
        url: '/user/login',
        method: 'post',
        data: {
            username: username,
            password: password
        }
    })
}

export function getInfo(params) {
    return request({
        url: '/user/getInfo',
        method: 'get',
        params:{
            token: params
        }
    })
}

export function getUser(userId) {
    return request({
        url: '/user/getUser/'+userId,
        method: 'get',
     
    })
}
