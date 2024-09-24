<template>
  <div class="order-page">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>我的订单</span>
      </div>

      <!-- 订单状态筛选 -->
      <el-tabs v-model="activeStatus" @tab-click="filterOrders">
        <el-tab-pane label="全部" name="all"></el-tab-pane>
        <el-tab-pane label="待发货" name="1"></el-tab-pane>
        <el-tab-pane label="运输中" name="2"></el-tab-pane>
        <el-tab-pane label="待收货" name="3"></el-tab-pane>
        <el-tab-pane label="已收货" name="4"></el-tab-pane>
      </el-tabs>

      <!-- 订单列表 -->
      <el-table :data="filteredOrders" stripe style="width: 100%" @row-click="goToOrderDetail">
        <el-table-column label="订单号" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.orderId }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="productName" label="商品名称"></el-table-column>
        <el-table-column prop="status" label="订单状态" width="180">
          <template slot-scope="scope">
            <el-tag :type="getOrderTagType(scope.row.status)">
              {{ getOrderStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="orderDate" label="下单时间" width="180"></el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button v-if="scope.row.status === 3" type="primary" @click.stop="showConfirmDialog(scope.row)">
              收货
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 确认收货对话框 -->
      <el-dialog title="确认收货" :visible.sync="dialogVisible">
        <span>确定要确认收货吗？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmReceipt">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>


<script>
import { selectAllOrders, updateOrderStatus } from '../../api/orders';

export default {
  data() {
    return {
      activeStatus: "all",
      orders: [],
      filteredOrders: [],
      dialogVisible: false, // 控制对话框的显示
      currentOrder: null, // 当前待确认收货的订单
    };
  },
  mounted() {
    this.fetchOrders();
  },
  methods: {
    fetchOrders() {
      let params = {
        userId: this.$store.state.user.userId
      };
      selectAllOrders(params).then((response) => {
        this.orders = response.data;
        this.filterOrders();
      });
    },
    filterOrders() {
      if (this.activeStatus === "all") {
        this.filteredOrders = this.orders;
      } else {
        this.filteredOrders = this.orders.filter(
          (order) => order.status === parseInt(this.activeStatus)
        );
      }
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
    getOrderTagType(status) {
      switch (status) {
        case 1: return "warning";
        case 2: return "info";
        case 3: return "success";
        case 4: return "default";
        default: return "";
      }
    },
    showConfirmDialog(order) {
      this.currentOrder = order; // 保存当前订单
      this.dialogVisible = true; // 显示对话框
    },
    confirmReceipt() {
      let data = {
        orderId: this.currentOrder.orderId,
        status: 4
      };
      updateOrderStatus(data)
      .then(() => {
        this.currentOrder.status = 4; // 更新订单状态
        this.$message.success("收货成功");
        this.filterOrders(); // 重新过滤订单
        this.dialogVisible = false; // 关闭对话框
      });
    },
    goToOrderDetail(row) {
      console.log(row.orderId)
  this.$emit('view-order-detail', row.orderId); // 使用事件将订单 ID 传递给父组件

}
  },
};
</script>


<style scoped>
.order-page {
  padding: 20px;
}
</style>
