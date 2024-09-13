<template>
    <div class="message-board">
      <h2>留言板</h2>
      
      <!-- 显示留言列表 -->
      <div v-if="messages.length > 0" class="message-list">
        <ul>
          <li v-for="(message, index) in messages" :key="index" class="message">
            <strong>{{ message.name }}：</strong> {{ message.content }}
          </li>
        </ul>
      </div>
      <div v-else>
        <p>暂无留言。</p>
      </div>
      
      <!-- 添加新留言 -->
      <div class="new-message">
        <h3>添加新留言</h3>
        <form @submit.prevent="addMessage">
          <label for="name">姓名：</label>
          <input type="text" id="name" v-model="newMessage.name" required>
          
          <label for="content">留言内容：</label>
          <textarea id="content" v-model="newMessage.content" required></textarea>
          
          <button type="submit">提交留言</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        messages: [
          { name: '张三', content: '今天天气真好！' },
          { name: '李四', content: '我喜欢这个留言板！' }
        ],
        newMessage: {
          name: '',
          content: ''
        }
      };
    },
    methods: {
      addMessage() {
        if (this.newMessage.name && this.newMessage.content) {
          this.messages.push({
            name: this.newMessage.name,
            content: this.newMessage.content
          });
          
          // 清空表单
          this.newMessage.name = '';
          this.newMessage.content = '';
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .message-board {
    font-family: Arial, sans-serif;
    max-width: 600px;
    margin: 0 auto;
    margin-top: 100px;
  }
  
  .message-list {
    margin-top: 20px;
  }
  
  .message {
    margin-bottom: 10px;
  }
  
  .new-message {
    margin-top: 40px;
  }
  
  .new-message form {
    display: grid;
    gap: 10px;
  }
  
  .new-message label {
    font-weight: bold;
  }
  
  .new-message textarea {
    height: 100px;
  }
  </style>
  