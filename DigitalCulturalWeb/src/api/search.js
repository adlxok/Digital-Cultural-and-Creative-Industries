import request from '@/utils/request'
export function searchPost(params){
    return request({
        url: '/search/post',
        method: 'get',
        params: params
    })
}