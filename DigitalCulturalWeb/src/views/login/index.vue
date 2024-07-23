<template>
    <div class="login-container">
      <div class="switch-buttons">
        <button :class="{ active: loginMode }" @click="switchToLogin">Login</button>
        <button :class="{ active: !loginMode }" @click="switchToRegister">Register</button>
      </div>
  
      <h2 v-if="loginMode">Login</h2>
      <h2 v-else>Register</h2>
  
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" id="username" v-model="username" required>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required>
        </div>
  
        <div v-if="!loginMode" class="form-group">
          <label for="confirmPassword">Confirm Password:</label>
          <input type="password" id="confirmPassword" v-model="confirmPassword" required>
        </div>
  
        <div class="form-group">
          <label for="captcha">Verification Code:</label>
          <div class="captcha-container">
            <input type="text" id="captcha" v-model="captchaInput" required style="width: 100px; height: 30px;">
            <span class="captcha" v-html="captcha"></span>
            <button type="button" @click="refreshCaptcha">Refresh</button>
          </div>
        </div>
  
        <button type="submit" v-if="loginMode">Login</button>
        <button type="submit" v-else>Register</button>
      </form>
  
      <p v-if="error" class="error-msg">{{ error }}</p>
      <p v-if="success" class="success-msg">{{ success }}</p>
    </div>
  </template>
  
  <script>
  import {login} from '../../api/login'
  export default {
    data() {
      return {
        username: '',
        password: '',
        confirmPassword: '',
        captcha: '', // 用于存储生成的验证码
        captchaInput: '', // 用户输入的验证码
        loginMode: true, // 默认显示登录表单
        error: '',
        success: ''
      };
    },
    created() {
      this.generateCaptcha(); // 组件创建时生成验证码
    },
    methods: {
      switchToLogin() {
        this.loginMode = true;
        this.resetForm();
      },
      switchToRegister() {
        this.loginMode = false;
        this.resetForm();
      },
      handleSubmit() {
        if (!this.validateCaptcha()) {
          this.error = 'Invalid verification code. Please try again.';
          return;
        }
  
        if (this.loginMode) {
          this.handleLogin();
        } else {
          this.handleRegister();
        }
      },
      handleLogin() {
        login(this.username, this.password).then(res => {
            console.log(res)
            this.$router.push('/');
        }).catch(e => {
            console.log(e)
        });
        
      },
      handleRegister() {
        // 实现注册逻辑
        if (this.password !== this.confirmPassword) {
          this.error = 'Passwords do not match. Please try again.';
          return;
        }
        // 可以添加注册 API 调用等实际逻辑
        this.success = 'Registration successful!';
      },
      generateCaptcha() {
        // 生成随机的验证码
        const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        let result = '';
        for (let i = 0; i < 6; i++) {
          result += chars[Math.floor(Math.random() * chars.length)];
        }
        this.captcha = result;
      },
      refreshCaptcha() {
        // 刷新验证码
        this.generateCaptcha();
        this.captchaInput = ''; // 清空用户输入的验证码
      },
      validateCaptcha() {
        // 验证验证码是否匹配
        return this.captchaInput.toLowerCase() === this.captcha.toLowerCase();
      },
      resetForm() {
        this.username = '';
        this.password = '';
        this.confirmPassword = '';
        this.captchaInput = '';
        this.error = '';
        this.success = '';
        this.generateCaptcha(); // 重置验证码
      }
    }
  };
  </script>
  
  <style scoped>
  .login-container {
    max-width: 400px;
    margin: 0 auto;
    margin-top: 100px;
    margin-bottom: 140px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f9f9f9;
    text-align: center;
  }
  
  .switch-buttons {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
  }
  
  .switch-buttons button {
    padding: 10px 20px;
    margin: 0 5px;
    font-size: 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    background-color: #007bff;
    color: #fff;
  }
  
  .switch-buttons button.active {
    background-color: #0056b3;
  }
  
  h2 {
    margin-bottom: 20px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
  }
  
  input[type="text"],
  input[type="password"],
  input[type="number"] {
    width: 100%;
    padding: 8px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .captcha-container {
    display: flex;
    align-items: center;
  }
  
  .captcha {
    margin-left: 10px;
    padding: 5px;
    font-size: 18px;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 100px; /* 与输入框宽度一致 */
    height: 30px; /* 与输入框高度一致 */
  }
  
  button[type="submit"] {
    width: 100%;
    padding: 10px;
    margin-top: 10px;
    font-size: 16px;
    color: #fff;
    background-color: #007bff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button[type="button"] {
  padding: 10px;
  font-size: 16px;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
  
  button[type="submit"]:hover,
  button[type="button"]:hover {
    background-color: #0056b3;
  }
  
  .error-msg,
  .success-msg {
    color: #ff0000;
    margin-top: 10px;
  }
  .success-msg {
    color: green;
  }
  </style>
  