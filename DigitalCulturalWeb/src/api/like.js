import request from '@/utils/request'
export function addLike(params){
    return request({
        url: '/like/addLike',
        method: 'get',
        params: params
    })
}
export function getLikeStatus(params){
    return request({
        url: '/like/likeStatus',
        method: 'get',
        params: params
    })
}
export function addFavorite(data){
    return request({
        url: '/like/addFavorite',
        method: 'post',
        data: data
    })
}
export function isFavorite(params){
    return request({
        url: '/like/isFavorite',
        method: 'get',
        params: params
    })
}
export function favoriteList(params){
    return request({
        url: '/like/favoriteList',
        method: 'get',
        params: params
    })
}
export function favoriteStatus(params){
    return request({
        url: '/like/favoriteStatus/'+params,
        method: 'get',
    })
}