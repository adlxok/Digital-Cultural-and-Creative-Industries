import request from '@/utils/request'

export function reqGetBannerList() {
    return request({
        url: '/reqGetBannerList',
        method: 'get',
    })
}