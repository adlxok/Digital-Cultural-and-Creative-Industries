import request from '@/utils/request'

export function showPostList (params) {
    return request({
        url: '/communityHome/postList',
        method: 'get',
        params:params
    })
}
export function showUser(userId){
    return request({
        url: '/communityHome/user/'+userId,
        method: 'get'
    })


}