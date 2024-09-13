<template>
  <div class="login-container">
    <div class="switch-buttons">
      <button :class="{ active: loginMode }" @click="switchToLogin">登录</button>
      <button :class="{ active: !loginMode }" @click="switchToRegister">注册</button>
    </div>

    <h2 v-if="loginMode">登录</h2>
    <h2 v-else>注册</h2>

    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="username">账号:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" required />
      </div>

      <div v-if="!loginMode" class="form-group">
        <label for="confirmPassword">确认密码:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
      </div>

      <div class="form-group">
        <label for="captcha">验证码:</label>
        <div class="captcha-container">
          <input
            type="text"
            id="captcha"
            v-model="captchaInput"
            required
            class="captcha-input"
          />
          <span class="captcha" v-html="captcha"></span>
          <button type="button" @click="refreshCaptcha" class="refresh-button">刷新一下</button>
        </div>
      </div>

      <button type="submit" v-if="loginMode">登录</button>
      <button type="submit" v-else>注册</button>
    </form>

    <p v-if="error" class="error-msg">{{ error }}</p>
    <p v-if="success" class="success-msg">{{ success }}</p>
  </div>
</template>

  
  <script>
  import {login} from '../../api/login'
  import { getInfo } from '../../api/getInfo';

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
        success: '',
        user: null
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
        login(this.username, this.password)
          .then((res) => {
            const token = res.data.token;
            this.$message.success('登录成功');
            this.$store.commit('SET_TOKEN', token);

            // 这里用大括号包裹箭头函数，确保正确赋值和逻辑
            getInfo(token).then((res) => {
              this.user = res.data;
              this.$store.commit('SET_USERID', this.user.id);
              console.log(this.$store.state.user.userId);
              // 确保用户信息已成功存储后，再跳转到首页
              this.$router.push('/');
            });
            
          })
          .catch((error) => {
            console.error(error);
            this.$message.error('登录失败，请检查用户名或密码');
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
  margin-top: 30px;
  padding: 30px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.switch-buttons {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
}

.switch-buttons button {
  padding: 12px 25px;
  margin: 0 10px;
  font-size: 18px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  background: linear-gradient(135deg, #6b73ff, #000dff);
  color: #ffffff;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transition: background 0.3s ease;
}

.switch-buttons button.active {
  background: linear-gradient(135deg, #ff73a1, #ff007e);
}

.switch-buttons button:hover {
  background: linear-gradient(135deg, #4f54ff, #000cff);
}

h2 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
  font-weight: 600;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
  font-size: 16px;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  transition: border-color 0.3s;
}

input[type="text"]:focus,
input[type="password"]:focus {
  border-color: #6b73ff;
}

.captcha-container {
  display: flex;
  align-items: center;
}

.captcha-input {
  width: 100px;
  height: 40px;
  font-size: 16px;
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ddd;
}

.captcha {
  margin-left: 10px;
  font-size: 20px;
  color: #333;
  background-color: #f1f1f1;
  padding: 8px;
  border-radius: 5px;
  width: 120px;
  text-align: center;
  border: 1px solid #ddd;
}

.refresh-button {
  margin-left: 10px;
  padding: 8px;
  border: none;
  border-radius: 5px;
  background-color: #6b73ff;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.refresh-button:hover {
  background-color: #4f54ff;
}

button[type="submit"] {
  width: 100%;
  padding: 15px;
  font-size: 18px;
  background: linear-gradient(135deg, #6b73ff, #000dff);
  border: none;
  border-radius: 25px;
  color: white;
  cursor: pointer;
  transition: background 0.3s ease;
}

button[type="submit"]:hover {
  background: linear-gradient(135deg, #4f54ff, #000cff);
}

.error-msg,
.success-msg {
  margin-top: 15px;
  font-size: 16px;
}

.error-msg {
  color: #ff4d4d;
}

.success-msg {
  color: #28a745;
}
</style>
