import request from '@/utils/request'

export function getBanner(username, password) {
    return request({
        url: '/banner',
        method: 'get',
        
    })
}