import request from '@/utils/request'

export function selectAllOrders() {
  return request({
    url: '/orders/selectAllOrders',
    method: 'get',
  })
}

export function deleteOrderById(data) {
    return request({
      url: '/orders/deleteOrderById',
      method: 'post',
      data: data
    })
  }
  
  export function OrderDelivery(data) {
    return request({
      url: '/orders/OrderDelivery',
      method: 'post',
      data: data
    })
  }