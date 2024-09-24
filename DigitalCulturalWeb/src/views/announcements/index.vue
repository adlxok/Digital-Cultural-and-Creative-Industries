<template>
    <div class="about-page">
      <h1>官方公告</h1>
      <div class="announcements">
        <div v-for="(announcement, index) in announcements" :key="index" class="announcement">
          <h2>{{ announcement.title }}</h2>
          <p>{{ announcement.content }}</p>
          <span>{{ announcement.date }}</span>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        announcements: [
          {
            title: "关于数字文化创意的新政策",
            content: "数字文化创意产业发展的最新政策与支持方向。",
            date: "2024-09-20"
          },
          {
            title: "平台维护通知",
            content: "平台将于2024年10月1日进行维护，预计时长6小时。",
            date: "2024-09-18"
          },
          {
            title: "新品发布",
            content: "我们将在本周发布新的数字文化创意产品，敬请期待！",
            date: "2024-09-15"
          }
        ]
      };
    }
  };
  </script>
  
  <style scoped>
  .about-page {
    padding: 20px;
    font-family: Arial, sans-serif;
  }
  
  h1 {
    font-size: 32px;
    color: #333;
  }
  
  .announcements {
    margin-top: 20px;
  }
  
  .announcement {
    background-color: #f9f9f9;
    padding: 15px;
    border: 1px solid #ddd;
    margin-bottom: 10px;
  }
  
  .announcement h2 {
    margin: 0;
    font-size: 24px;
    color: #2c3e50;
  }
  
  .announcement p {
    margin: 10px 0;
    color: #666;
  }
  
  .announcement span {
    font-size: 12px;
    color: #999;
  }
  </style>
  