<template>
  <div class="dashboard-container">
    <!-- 仪表盘标题 -->
    <h1 class="dashboard-title">仪表盘</h1>

    <!-- 数据概览 -->
    <div class="dashboard-overview">
      <div class="overview-item">
        <h3>用户总数</h3>
        <p>{{ userCount }}</p>
      </div>
      <div class="overview-item">
        <h3>商品总数</h3>
        <p>{{ productCount }}</p>
      </div>
    </div>

    <!-- 图表区域 -->
    <div class="dashboard-charts">
      <h2>数据统计</h2>
      <canvas id="userGrowthChart"></canvas>
      <canvas id="productSalesChart"></canvas>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { Chart } from 'chart.js';
import { selectAll as selectAllProducts } from '@/api/products'; // 为产品选择重命名
import { selectAll as selectAllUsers } from '@/api/user'; // 为用户选择重命名

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      // 'users' 和 'products' 被替换为数据中的直接引用
      'totalUsers',
      'totalProducts'
    ])
  },
  data() {
    return {
      userCount: null,
      productCount: null,
      products: [],
      users: []
    };
  },
  mounted() {
    this.fetchDashboardData();
    this.renderUserGrowthChart();
    this.renderProductSalesChart();
  },
  methods: {
    fetchDashboardData() {
      // 调用重命名的函数来获取数据
      selectAllProducts().then((res) => {
        // 处理产品数据
        this.products = res.data; // 假设返回的产品数据在 res.data 中
        this.productCount = this.products.length; // 使用 this.products 进行引用
        console.log('产品总数:', this.productCount);
      });

      selectAllUsers().then((res) => {
        // 处理用户数据
        this.users = res.data; // 假设返回的用户数据在 res.data 中
        this.userCount = this.users.length; // 使用 this.users 进行引用
        console.log('用户总数:', this.userCount);
      });
    },
    // 渲染用户增长图表
    renderUserGrowthChart() {
      const ctx = document.getElementById('userGrowthChart').getContext('2d');
      new Chart(ctx, {
        type: 'line',
        data: {
          labels: ['January', 'February', 'March', 'April', 'May'],
          datasets: [{
            label: '用户增长趋势',
            data: [12, 19, 3, 5, 2],
            borderColor: 'rgba(75, 192, 192, 1)',
            fill: false
          }]
        },
        options: {
          responsive: true,
          title: {
            display: true,
            text: '用户增长趋势'
          }
        }
      });
    },
    // 渲染商品销售图表
    renderProductSalesChart() {
      const ctx = document.getElementById('productSalesChart').getContext('2d');
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ['Product A', 'Product B', 'Product C', 'Product D'],
          datasets: [{
            label: '商品销售',
            data: [10, 20, 30, 40],
            backgroundColor: 'rgba(153, 102, 255, 0.2)',
            borderColor: 'rgba(153, 102, 255, 1)',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          title: {
            display: true,
            text: '商品销售数据'
          }
        }
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 30px;
}

.dashboard-title {
  font-size: 36px;
  margin-bottom: 20px;
}

.dashboard-overview {
  display: flex;
  justify-content: space-around;
  margin-bottom: 30px;

  .overview-item {
    background-color: #94adc7;
    color: #fff;
    padding: 30px 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    text-align: center;
    width: 45%; /* Adjust the width to control spacing */
    transition: transform 0.3s ease;

    &:hover {
      transform: translateY(-10px);
    }

    h3 {
      margin-bottom: 10px;
      font-size: 24px;
    }

    p {
      font-size: 32px;
      font-weight: bold;
      margin: 0;
    }
  }
}

.dashboard-charts {
  margin-bottom: 30px;

  h2 {
    margin-bottom: 20px;
  }

  canvas {
    margin-bottom: 30px;
    width: 100%;
    max-width: 600px;
  }
}

.dashboard-users, .dashboard-products {
  margin-bottom: 30px;

  h2 {
    margin-bottom: 20px;
  }

  ul {
    list-style: none;
    padding: 0;

    li {
      padding: 10px;
      background-color: #f9f9f9;
      margin-bottom: 10px;
      border-radius: 5px;
    }
  }
}
</style>
