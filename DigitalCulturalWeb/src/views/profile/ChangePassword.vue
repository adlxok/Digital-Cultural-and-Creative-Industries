<template>
  <div class="change-password-container">
    <h2 class="title">修改密码</h2>
    <el-form @submit.prevent="handleChangePassword" class="form-container">
      <el-form-item label="当前密码">
        <el-input
          :type="currentPasswordVisible ? 'text' : 'password'"
          v-model="currentPassword"
        >
          <i
            slot="suffix"
            :class="currentPasswordVisible ? 'icon-xianshizy' : 'icon-yincangby'"
            @click="toggleCurrentPasswordVisibility"
            style="cursor: pointer;"
          ></i>
        </el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input
          :type="newPasswordVisible ? 'text' : 'password'"
          v-model="newPassword"
          required
        >
          <i
            slot="suffix"
            :class="newPasswordVisible ? 'icon-xianshizy' : 'icon-yincangby'"
            @click="toggleNewPasswordVisibility"
            style="cursor: pointer;"
          ></i>
        </el-input>
      </el-form-item>
      <el-form-item label="确认新密码">
        <el-input
          :type="confirmNewPasswordVisible ? 'text' : 'password'"
          v-model="confirmNewPassword"
          required
        >
          <i
            slot="suffix"
            :class="confirmNewPasswordVisible ? 'icon-xianshizy' : 'icon-yincangby'"
            @click="toggleConfirmNewPasswordVisibility"
            style="cursor: pointer;"
          ></i>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="submit-button" @click="handleChangePassword">修改密码</el-button>
      </el-form-item>
    </el-form>
    <p v-if="passwordError" class="error-msg">{{ passwordError }}</p>
    <p v-if="passwordSuccess" class="success-msg">{{ passwordSuccess }}</p>
  </div>
</template>

<script>

import {updatepassword} from '../../api/updatepassword'

export default {
  data() {
    return {
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
      passwordError: '',
      passwordSuccess: '',
      currentPasswordVisible: false,
      newPasswordVisible: false,
      confirmNewPasswordVisible: false
    };
  },
  methods: {
    handleChangePassword() {
      if (this.newPassword !== this.confirmNewPassword) {
        this.passwordError = '新密码与确认密码不一致';
        return;
      }
      let formdata = {
        userId: this.$store.state.user.userId,
        oldpassword: this.currentPassword,
        newpassword: this.newPassword
      }
      updatepassword(formdata).then((res) => {
        
        console.log(res)
      })

      // 执行密码修改逻辑
      // this.passwordSuccess = '密码修改成功';
      this.$message.success('密码修改成功!');
      this.passwordError = '';
      this.currentPassword = '';
      this.newPassword = '';
      this.confirmNewPassword = '';
    },
    toggleCurrentPasswordVisibility() {
      this.currentPasswordVisible = !this.currentPasswordVisible;
    },
    toggleNewPasswordVisibility() {
      this.newPasswordVisible = !this.newPasswordVisible;
    },
    toggleConfirmNewPasswordVisibility() {
      this.confirmNewPasswordVisible = !this.confirmNewPasswordVisible;
    }
  }
};
</script>

<style scoped>
.change-password-container {
  max-width: 500px; /* 控制容器宽度 */
  margin-bottom: 33px; 
  margin-left: 300px; 
  margin-top: 99px; 
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.title {
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
}

.form-container {
  display: flex;
  flex-direction: column;
}

.el-form-item {
  margin-bottom: 15px; /* 调整表单项的底部边距 */
}

.input-field {
  width: 100%; /* 使输入框宽度100% */
}

.submit-button {
  width: 100%; /* 使按钮宽度100% */
  padding: 10px;
  font-size: 16px;
}

.error-msg {
  color: red;
  text-align: center;
  margin-top: 20px;
}

.success-msg {
  color: green;
  text-align: center;
  margin-top: 20px;
}

/* 根据实际需要调整样式 */
.icon-xianshizy::before {
  content: '\e6cf'; /* 示例，使用实际图标的 Unicode */
}

.icon-yincangby::before {
  content: '\e6d1'; /* 示例，使用实际图标的 Unicode */
}
</style>
