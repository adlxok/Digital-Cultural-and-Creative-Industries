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

export function addpost(data){
    return request({
        url: '/post/addPost',
        method: 'post',
        data:data
    })
}
export function saveImage(data){
    return request({
        url: '/communityUpload/saveImage',
        method: 'post',
        data:data
    })
}
export function hotPost(){
    return request({
        url: '/post/hotPost',
        method: 'get'
    })
}
export function getPostById(params){
    return request({
        url: '/communityHome/getPostById/'+params,
        method: 'get'
    })
}
