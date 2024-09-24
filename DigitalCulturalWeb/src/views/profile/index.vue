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
        <el-menu-item index="3">我的订单</el-menu-item> <!-- 新增我的订单菜单项 -->
        <el-menu-item index="5">退出登录</el-menu-item> <!-- 退出登录菜单项 -->
      </el-menu>
    </el-aside>

    <!-- 右侧内容区域 -->
    <el-main class="profile-content">
  <UserOrders @view-order-detail="showOrderDetail" v-if="currentComponent === 'UserOrders'" />
  <OrderDetail ref="orderDetailComponent" :orderId="currentOrderId" v-else-if="currentComponent === 'OrderDetail'" />
  <component v-else :is="currentComponent"></component>
</el-main>
  </el-container>
  
</template>

<script>
import UserInfo from './UserInfo.vue';
import ChangePassword from './ChangePassword.vue';
import UserOrders from './UserOrders.vue'; // 导入我的订单组件
import { logout } from "@/api/logout";
import { MessageBox } from 'element-ui';
import {getInfo} from '@/api/getInfo';
import OrderDetail from './OrderDetail.vue';

export default {
  components: {
    UserInfo,
    ChangePassword,
    UserOrders,
    OrderDetail // 注册我的订单组件
  },
  data() {
    return {
      user: {},
      activeMenu: '1', 
      currentComponent: 'UserInfo',
      currentOrderId: null, // 初始化当前订单 ID
    };
  },
  mounted() {
    this.fetchUserData();
    console.log("Mounted currentComponent:", this.currentComponent);
  },
  methods: {
    showOrderDetail(orderId) {
  this.currentOrderId = orderId;
  this.currentComponent = 'OrderDetail';
  console.log("Switching to OrderDetail with orderId:", orderId);
},
    fetchUserData() {
      getInfo().then((res) => {
        this.user = res.data;
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
      this.currentComponent = 'UserOrders'; // 切换到我的订单组件
      this.currentOrderId = null; // 清除当前订单 ID
      break;
    case '5': 
      this.confirmLogout();
      break;
    default:
      this.currentComponent = 'UserInfo';
  }
},
    confirmLogout() {
      MessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.logout();
      }).catch(() => {});
    },
    logout() {
      logout().then(() => {
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
