import request from '@/utils/request'

export function getPostList(params) {
  return request({
    url: '/post/postList',
    method: 'get',
    params: params
  })
}
export function deletePost (params) {
    return request({
      url: '/post/deletePost',
      method: 'get',
      params:{
        id:params
      }
    })
  }
  
  export function topPost (params) {
    return request({
      url: '/post/topPost',
      method: 'get',
      params:{
        id:params
      }
    })
  }
  
  export function essentialPost (params) {
    return request({
      url: '/post/essentialPost',
      method: 'get',
      params:{
        id:params
      }
    })
  }
  export function updateScore (params) {
    return request({
      url: '/post/updateScore',
      method: 'get',
      params:params
      
    })
  }