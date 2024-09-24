import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/comment/add',
    method: 'post',
    data: data
  })
}

export function list(params) {
  return request({
    url: '/comment/list',
    method: 'get',
    params: params
  })
}
export function deletecomment(params){
    return request({
      url: '/comment/delete',
      method: 'get',
      params: {
        id:params
      }
    })
  }