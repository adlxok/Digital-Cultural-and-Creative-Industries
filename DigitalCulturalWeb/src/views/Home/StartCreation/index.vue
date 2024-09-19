<template>
    <div class="start-creation">
      <div class="content-wrapper">
        <h2>开始创作</h2>
        <p>探索你的创意，开始创造属于你的作品。</p>
        <button @click="navigateToCreation">立即开始</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "StartCreation",
    methods: {
      navigateToCreation() {
        // 跳转到创作页面，例如 "/creation"
        this.$router.push('/create');
      }
    }
  };
  </script>
  
  <style scoped>
  .start-creation {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    padding: 60px 20px;
    background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
    border-radius: 15px;
    margin: 40px auto;
    max-width: 900px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    overflow: hidden;
  }
  
  .content-wrapper {
    max-width: 600px;
    color: #fff;
  }
  
  .start-creation h2 {
    font-size: 36px;
    color: #fff;
    margin-bottom: 20px;
    font-weight: 700;
  }
  
  .start-creation p {
    font-size: 18px;
    color: #e0e0e0;
    margin-bottom: 30px;
  }
  
  .start-creation button {
    padding: 15px 30px;
    font-size: 20px;
    color: #fff;
    background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
    border: none;
    border-radius: 50px;
    cursor: pointer;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    box-shadow: 0 5px 15px rgba(255, 126, 95, 0.4);
  }
  
  .start-creation button:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 20px rgba(255, 126, 95, 0.6);
  }
  
  .start-creation button:focus {
    outline: none;
  }
  </style>
  