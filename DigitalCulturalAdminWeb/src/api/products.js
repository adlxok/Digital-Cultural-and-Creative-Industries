import request from '@/utils/request'

export function selectAll() {
  return request({
    url: '/products/selectAll',
    method: 'get',
  })
}



export function updateStatus(data) {
    return request({
      url: '/products/updateStatus',
      method: 'post',
      data: data
    })
  }

  

  export function updateProducts(data) {
    return request({
      url: '/products/updateProducts',
      method: 'post',
      data: data
    })
  }

  
  export function deleteOne(data) {
    return request({
      url: '/products/deleteOne',
      method: 'post',
      data: data
    })
  }

  
  export function insertOne(data) {
    return request({
      url: '/products/insertOne',
      method: 'post',
      data: data
    })
  }