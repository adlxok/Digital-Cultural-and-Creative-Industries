import request from '@/utils/request'

export function getallTopics() {
    return request({
        url: '/getallTopics',
        method: 'get',
        
    })
}