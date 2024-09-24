<template>
  <div class="order-detail">
    <el-card class="box-card" v-if="order">
      <div slot="header">
        <h2>订单详情</h2>
      </div>
      <el-row :gutter="20" class="detail-row">
        <el-col :span="12">
          <p><strong>订单号:</strong> {{ order.orderId }}</p>
        </el-col>
        <el-col :span="12">
          <p><strong>商品名称:</strong> {{ order.productName }}</p>
        </el-col>
      </el-row>
      <el-divider></el-divider>

      <el-row :gutter="20" class="detail-row">
        <el-col :span="12">
          <p><strong>收货人:</strong> {{ order.recipientName }}</p>
        </el-col>
        <el-col :span="12">
          <p><strong>状态:</strong> {{ getOrderStatusText(order.status) }}</p>
        </el-col>
      </el-row>
      <el-divider></el-divider>

      <el-row :gutter="20" class="detail-row">
        <el-col :span="12">
          <p><strong>下单时间:</strong> {{ order.orderDate }}</p>
        </el-col>
        <el-col :span="12">
          <p><strong>收货地址:</strong> {{ order.shippingAddress }}</p>
        </el-col>
      </el-row>
      <el-divider></el-divider>

      <el-row :gutter="20" class="detail-row">
        <el-col :span="12">
          <p><strong>手机号:</strong> {{ order.recipientPhone }}</p>
        </el-col>
        <el-col :span="12">
          <p><strong>总金额:</strong> ￥{{ order.totalAmount }}</p>
        </el-col>
      </el-row>
      <el-divider></el-divider>
      <el-button type="primary" @click="openEditDialog">编辑</el-button>
    </el-card>
    <p v-else>正在加载订单详情...</p>

    <el-dialog title="编辑订单信息" :visible.sync="dialogVisible">
      <el-form :model="editedOrder">
        <el-form-item label="收货人">
          <el-input v-model="editedOrder.recipientName"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="editedOrder.recipientPhone"></el-input>
        </el-form-item>
        <el-form-item label="收货地址">
          <el-input v-model="editedOrder.shippingAddress"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveChanges">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { selectOrderById, updateOrders } from '../../api/orders';

export default {
  props: {
    orderId: Number // 接收传入的订单ID
  },
  data() {
    return {
      order: null,
      dialogVisible: false,
      editedOrder: {}
    };
  },
  mounted() {
    this.fetchOrderDetail(this.orderId); // 根据订单ID获取订单详情
  },
  methods: {
    fetchOrderDetail(orderId) {
      let params = { orderId: orderId };
      selectOrderById(params).then((res) => {
        this.order = res.data;
        this.editedOrder = { ...this.order }; // 复制订单数据以便编辑
      });
    },
    getOrderStatusText(status) {
      switch (status) {
        case 1: return "待发货";
        case 2: return "运输中";
        case 3: return "待收货";
        case 4: return "已收货";
        default: return "未知状态";
      }
    },
    openEditDialog() {
      this.dialogVisible = true; // 显示对话框
    },
    saveChanges() {
      // 这里可以添加保存修改的逻辑
      
      
      updateOrders(this.editedOrder).then(() => {
        this.order = { ...this.editedOrder };
        this.$message.success("编辑成功");
      })
       // 更新订单信息
      this.dialogVisible = false; // 关闭对话框
      console.log('保存的订单信息:', this.order);
    }
  }
};
</script>

<style scoped>
/* 其他样式保持不变 */
</style>


<style scoped>
.order-detail {
  padding: 20px;
  background-color: #f5f5f5; /* 设置背景色 */
  border-radius: 8px; /* 圆角效果 */
}

.box-card {
  border: 1px solid #eaeaea; /* 边框 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

.detail-row {
  margin-bottom: 16px; /* 行间距 */
}

h2 {
  margin: 0; /* 去掉默认的 h2 外边距 */
}

strong {
  color: #333; /* 字体颜色 */
}
</style>