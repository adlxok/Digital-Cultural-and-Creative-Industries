import request from '@/utils/request'

export function reqGetHeadList() {
    return request({
        url: '/reqGetHeadList',
        method: 'get',
    })
}