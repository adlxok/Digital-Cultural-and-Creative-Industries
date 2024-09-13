<template>
    <div>
      <h2>修改密码</h2>
      <el-form @submit.prevent="handleChangePassword">
        <el-form-item label="当前密码">
          <el-input type="password" v-model="currentPassword" required></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input type="password" v-model="newPassword" required></el-input>
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input type="password" v-model="confirmNewPassword" required></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleChangePassword">修改密码</el-button>
        </el-form-item>
      </el-form>
      <p v-if="passwordError" class="error-msg">{{ passwordError }}</p>
      <p v-if="passwordSuccess" class="success-msg">{{ passwordSuccess }}</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        currentPassword: '',
        newPassword: '',
        confirmNewPassword: '',
        passwordError: '',
        passwordSuccess: ''
      };
    },
    methods: {
      handleChangePassword() {
        if (this.newPassword !== this.confirmNewPassword) {
          this.passwordError = '新密码与确认密码不一致';
          return;
        }
  
        // 执行密码修改逻辑
        this.passwordSuccess = '密码修改成功';
        this.passwordError = '';
        this.currentPassword = '';
        this.newPassword = '';
        this.confirmNewPassword = '';
      }
    }
  };
  </script>
  