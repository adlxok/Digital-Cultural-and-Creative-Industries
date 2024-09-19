<template>
  <div class="dashboard-container">
    <!-- 仪表盘标题 -->
    <h1 class="dashboard-title">仪表盘</h1>
    
    <!-- 数据概览 -->
    <div class="dashboard-overview">
      <div class="overview-item">
        <h3>用户总数</h3>
        <p>{{ 3 }}</p>
      </div>
      <div class="overview-item">
        <h3>商品总数</h3>
        <p>{{ 16 }}</p>
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

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'users',
      'products',
      'totalUsers',
      'totalProducts'
    ])
  },
  mounted() {
    this.renderUserGrowthChart();
    this.renderProductSalesChart();
  },
  methods: {
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
