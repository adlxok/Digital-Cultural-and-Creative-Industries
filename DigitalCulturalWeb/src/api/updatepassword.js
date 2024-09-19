import request from '@/utils/request'

export function updatepassword(data) {
    return request({
        url: '/user/updatepassword',
        method: 'post',
        data: data
    })
}