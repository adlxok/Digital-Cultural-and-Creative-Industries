import request from '@/utils/request'
export function followUser(params){
    return request({
        url: '/follow/followUser',
        method: 'get',
        params: params
    })
}
export function followStatus(params){
    return request({
        url: '/follow/followStatus',
        method: 'get',
        params: params
    })
}
export function unfollow(params){
    return request({
        url: '/follow/unfollow',
        method: 'get',
        params: params
    })
}
export function followeeList(userId, params){
    return request({
        url: '/follow/followeeList/'+userId,
        method: 'get',
        params: params
    })
}
export function followerList(userId, params){
    return request({
        url: '/follow/followerList/'+userId,
        method: 'get',
        params: params
    })
}

