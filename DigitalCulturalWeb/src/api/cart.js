import request from '@/utils/request'

export function getCartItems(userId) {
    return request({
        url: '/cart/getCartItems',
        method: 'get',
        params: {
            userId: userId
        }
    })
}

export function addToCart(userId, productId, quantity) {
    return request({
        url: '/cart/addToCart',
        method: 'post',
        data: {
            userId: userId,
            productId: productId,
            quantity: quantity
        }
    })
}

export function removeFromCart(userId, productId) {
    return request({
        url: '/cart/removeFromCart',
        method: 'post',
        data: {
            userId: userId,
            productId: productId,
        }
    })
}

export function clearCart(userId) {
    return request({
        url: '/cart/clearCart',
        method: 'post',
        data: {
            userId: userId
        }
    })
}

export function updateCartItem(userId, productId, quantity) {
    return request({
        url: '/cart/updateCartItem',
        method: 'post',
        data: {
            userId: userId,
            productId: productId,
            quantity: quantity
        }
    })
}