import request from '@/utils/request'
export function upload(data){
    return request({
        url: '/post/uploadImage',
        method: 'post',
        data:data
    })
}