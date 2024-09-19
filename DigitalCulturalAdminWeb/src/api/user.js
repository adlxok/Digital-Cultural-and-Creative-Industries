import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}
 
export function getInfo(username) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { username }
  })
}


export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

export function selectAll() {
  return request({
    url: '/users/selectAll',
    method: 'get'
  })
}

export function deleteUsers(data) {
  return request({
    url: '/users/deleteUsers',
    method: 'post',
    data: data
  })
}

export function updateUsers(data) {
  return request({
    url: '/users/updateUsers',
    method: 'post',
    data: data
  })
}
export function updateStatus(data) {
  return request({
    url: '/users/updateStatus',
    method: 'post',
    data: data
  })
}