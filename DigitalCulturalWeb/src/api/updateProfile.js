import request from '@/utils/request'

export function updateProfile(data) {
    return request({
        url: '/user/updateProfile',
        method: 'post',
        data: data
    })
}