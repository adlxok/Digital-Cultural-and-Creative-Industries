import request from '@/utils/request'

export function getallproducts() {
    return request({
        url: '/mall/products/getallproducts',
        method: 'get',
        
    })
}

export function selectProductById(productId) {
    return request({
        url: `/mall/products/selectProductById/${productId}`,
        method: 'get',
        
    })
}