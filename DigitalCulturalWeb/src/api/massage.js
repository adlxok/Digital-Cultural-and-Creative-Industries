import request from '@/utils/request'
export function addLetter(params){
    return request({
        url: '/letter/addLetter',
        method: 'get',
        params: params
    })
}
export function unreadCount(){
    return request({
        url: '/letter/unreadCount',
        method: 'get',
    })
}
export function letterList(){
    return request({
        url: '/letter/letterList',
        method: 'get',
    })
}
export function letterDetail(params){
    return request({
        url: '/letter/letterDetail',
        method: 'get',
        params: {
            conversationId: params
        }
    })
}
export function noticeList(){
    return request({
        url: '/letter/letterDetail',
        method: 'get',
    })
}
export function noticeDetail(param){
    return request({
        url: '/letter/noticeDetail',
        method: 'get',
        param: param
    })
}
