<template>
    <div>
      <h2>个人信息</h2>
      <!-- 用户信息内容 -->
      <p>用户名: {{ user.username }}</p>
      <p>邮箱: {{ user.email }}</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        user: {
          username: 'JohnDoe',
          email: 'john.doe@example.com'
        }
      };
    }
  };
  </script>
  