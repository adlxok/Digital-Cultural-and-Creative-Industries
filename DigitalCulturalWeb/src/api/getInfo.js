import request from '@/utils/request'

export function getInfo(token) {
    return request({
        url: '/user/getInfo',
        method: 'get',
        params: {
            token: token
        }
    })
}