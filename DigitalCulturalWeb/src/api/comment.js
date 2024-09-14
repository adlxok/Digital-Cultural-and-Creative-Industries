import request from '@/utils/request'

export function showCommentList (params) {
    return request({
        url: '/comment/commentList',
        method: 'get',
        params:params
    })
}
export function addComment (data,postId) {
    return request({
        url: '/comment/addComment/'+postId,
        method: 'post',
        data:data
    })
}
export function replyCommentList (replyId) {
    return request({
        url: '/comment/replyCommentList/'+replyId,
        method: 'get',
    })
}

