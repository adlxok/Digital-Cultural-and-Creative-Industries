import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data: data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/getInfo',
    method: 'get',
    params: { 
      token: token 
    }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
