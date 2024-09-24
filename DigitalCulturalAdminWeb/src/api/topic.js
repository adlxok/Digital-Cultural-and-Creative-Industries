import request from '@/utils/request'

export function selectAll() {
  return request({
    url: '/topicscategories/selectAll',
    method: 'get',
  })
}

export function updateStatus(data) {
  return request({
    url: '/topicscategories/updateStatus',
    method: 'post',
    data: data
  })
}

export function insertone(data) {
  return request({
    url: '/topicscategories/insertone',
    method: 'post',
    data: data
  })
}



export function updateTopic(data) {
  return request({
    url: '/topicscategories/updateTopic',
    method: 'post',
    data: data
  })
}

export function deleteOne(data) {
  return request({
    url: '/topicscategories/deleteOne',
    method: 'post',
    data: data
  })
}