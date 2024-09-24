import request from '@/utils/request'

export function selectAllOrders(params) {
    return request({
        url: '/orders/selectAllOrders',
        method: 'get',
        params: params
    })
}

export function updateOrderStatus(data) {
    return request({
        url: '/orders/updateOrderStatus',
        method: 'post',
        data: data
    })
}


export function selectOrderById(params) {
    return request({
        url: '/orders/selectOrderById',
        method: 'get',
        params: params
    })
}



export function updateOrders(data) {
    return request({
        url: '/orders/updateOrders',
        method: 'post',
        data: data
    })
}

export function addOrders(ordersList, proIdList, userId) {
    return request({
        url: '/orders/addOrders',
        method: 'post',
        data: {
            ordersList: ordersList,
            proIdList: proIdList,
            userId: userId
        }
    })
}