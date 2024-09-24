<template>
    <div class="order-management">
      <h1>订单管理</h1>
  
      <el-table :data="pagedOrders" style="width: 100%">
        <el-table-column prop="userId" label="用户ID" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column :label="'订单状态'" :formatter="getStatusName" />
        <el-table-column prop="orderDate" label="下单时间" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="openDeleteConfirm(scope.row.orderId)" type="text" style="color: red;">删除</el-button>
            <el-button @click="openShipDialog(scope.row.orderId)" type="text">发货</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 删除确认对话框 -->
      <el-dialog :visible.sync="showDeleteConfirm" title="确认删除">
        <p>您确定要删除该订单吗？</p>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeDeleteConfirm">取 消</el-button>
          <el-button type="primary" @click="confirmDelete">确 定</el-button>
        </span>
      </el-dialog>
  
      <el-dialog :visible.sync="showShipModal" title="发货">
        <el-form :model="currentOrder">
          <el-form-item label="物流公司">
            <el-select v-model="selectedLogistics" placeholder="选择物流公司">
              <el-option v-for="company in logisticsCompanies" :key="company.value" :label="company.label" :value="company.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="快递单号">
            <el-input v-model="trackingNumber" placeholder="输入快递单号" />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeShipModal">取 消</el-button>
          <el-button type="primary" @click="confirmShip">确 定</el-button>
        </span>
      </el-dialog>
  
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="orders.length"
        layout="total, prev, pager, next, jumper">
      </el-pagination>
  
      
    </div>
  </template>
  
  <script>
  import { selectAllOrders, deleteOrderById, OrderDelivery } from '@/api/order';
  
  export default {
    data() {
      return {
        orders: [],
        currentOrder: {},
        showModal: false,
        showShipModal: false,
        showDeleteConfirm: false,
        currentPage: 1,
        pageSize: 10,
        logisticsCompanies: [
          { value: 'sf', label: '顺丰快递' },
          { value: 'yt', label: '圆通快递' },
          { value: 'jd', label: '京东快递' },
          { value: 'ems', label: 'EMS' },
        ],
        selectedLogistics: '',
        trackingNumber: '',
        orderToDelete: null, // 用于存储待删除的订单ID
      };
    },
    computed: {
      pagedOrders() {
        const start = (this.currentPage - 1) * this.pageSize;
        return this.orders.slice(start, start + this.pageSize);
      },
    },
    methods: {
      async fetchOrders() {
        const res = await selectAllOrders();
        this.orders = res.data;
      },
      openDeleteConfirm(orderId) {
        this.orderToDelete = orderId;
        this.showDeleteConfirm = true;
      },
      closeDeleteConfirm() {
        this.showDeleteConfirm = false;
        this.orderToDelete = null; // 清空待删除的订单ID
      },
      async confirmDelete() {
        let data = {
            orderId: this.orderToDelete
        }
        await deleteOrderById(data); 
        this.$message.success("成功删除")
        this.closeDeleteConfirm();
        this.fetchOrders();
      },
      openShipDialog(orderId) {
        this.currentOrder = this.orders.find(order => order.orderId === orderId);
        this.showShipModal = true;
      },
      closeShipModal() {
        this.showShipModal = false;
        this.selectedLogistics = '';
        this.trackingNumber = '';
      },
      async confirmShip() {
        // 在这里处理发货逻辑，比如调用发货接口
        // await shipOrder({ orderId: this.currentOrder.orderId, logistics: this.selectedLogistics, trackingNumber: this.trackingNumber });
        let data = {
            orderId: this.currentOrder.orderId,
            logisticsCompany: this.selectedLogistics,
            orderSn: this.trackingNumber
        }
        await OrderDelivery(data)
        this.closeShipModal();
        this.fetchOrders();
      },
      getStatusName(row) {
        const statusMap = {
          1: '待发货',
          2: '运输中',
          3: '待收货',
          4: '已收货',
        };
        return statusMap[row.status] || '未知状态';
      },
      handleCurrentChange(page) {
        this.currentPage = page;
      },
    },
    mounted() {
      this.fetchOrders();
    },
  };
  </script>
  
  <style scoped>
  .order-management {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    text-align: left; /* 使内容靠左对齐 */
  }
  </style>
  