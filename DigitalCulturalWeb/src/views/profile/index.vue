<template>
  <el-container class="profile-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px" class="profile-menu">
      <!-- 头像和用户名 -->
      <div class="user-info">
        <el-avatar :src="user.profileImageUrl" size="large"></el-avatar>
        <p class="username">{{ user.nickname }}</p>
      </div>
      
      <!-- 菜单项 -->
      <el-menu :default-active="activeMenu" @select="handleMenuSelect">
        <el-menu-item index="1">个人设置</el-menu-item>
        <el-menu-item index="2">修改密码</el-menu-item>
        <!-- <el-menu-item index="3">个人设置</el-menu-item> -->
        <!-- <el-menu-item index="4">个人喜好</el-menu-item> -->
        <el-menu-item index="5">退出登录</el-menu-item> <!-- 新增退出登录菜单项 -->
      </el-menu>
    </el-aside>

    <!-- 右侧内容区域 -->
    <el-main class="profile-content">
      <component :is="currentComponent"></component>
    </el-main>
  </el-container>
</template>

<script>
import UserInfo from './UserInfo.vue';
import ChangePassword from './ChangePassword.vue';
import UserSettings from './UserSettings.vue';
import UserPreferences from './UserPreferences.vue';
import { logout } from "@/api/logout"; // 假设有一个退出登录的 API 接口
import { MessageBox } from 'element-ui'; // 引入 MessageBox 弹窗
import {getInfo} from '@/api/getInfo';

export default {
  components: {
    UserInfo,
    ChangePassword,
    UserSettings,
    UserPreferences
  },
  data() {
    return {
      user: {},
      activeMenu: '1', // 默认显示个人信息
      currentComponent: 'UserInfo' // 默认显示个人信息组件
    };
  },
  mounted() {
    // 在组件挂载时获取用户数据
    this.fetchUserData();
  },
  methods: {
    fetchUserData() {
        getInfo().then((res) => {
          this.user = res.data;
          console.log(this.user)
        })
        .catch(error => {
          console.error('获取用户数据失败', error);
        });
    },
    handleMenuSelect(index) {
      this.activeMenu = index;
      switch (index) {
        case '1':
          this.currentComponent = 'UserInfo';
          break;
        case '2':
          this.currentComponent = 'ChangePassword';
          break;
        case '3':
          this.currentComponent = 'UserSettings';
          break;
        case '4':
          this.currentComponent = 'UserPreferences';
          break;
        case '5': // 退出登录
          this.confirmLogout();
          break;
        default:
          this.currentComponent = 'UserInfo';
      }
    },
    confirmLogout() {
      // 使用 MessageBox 弹出确认对话框
      MessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 用户确认退出，执行退出逻辑
        this.logout();
      }).catch(() => {
        // 用户取消，关闭对话框，无需处理
      });
    },
    logout() {
      // 执行退出登录的操作
      logout().then(() => {
        // 退出成功后跳转到登录页面
        this.$store.commit('LOGOUT');
        this.$router.push('/login');
      }).catch(error => {
        console.error('退出登录失败', error);
      });
    }
  }
};
</script>

<style scoped>
.profile-container {
  height: 100vh;
}

.profile-menu {
  background-color: #f4f4f4;
  padding: 20px 10px;
}

.user-info {
  text-align: center;
  margin-bottom: 20px;
}

.username {
  margin-top: 10px;
  font-size: 16px;
  font-weight: bold;
}

.profile-content {
  padding: 20px;
}
</style>
