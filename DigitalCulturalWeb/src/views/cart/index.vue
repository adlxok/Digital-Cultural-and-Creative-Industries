<template>
  <div class="cart-page">
    <h1>购物车</h1>

    <div v-if="cartItems.length === 0" class="empty-cart">
      <p>购物车是空的</p>
      <router-link to="/mall">去购物</router-link>
    </div>

    <div v-else>
      <ul class="cart-items">
        <li
          v-for="(item, index) in cartItems"
          :key="item.id"
          class="cart-item"
          :class="{ 'item-selected': item.selected }"
        >
          <div class="item-selection">
            <input type="checkbox" v-model="item.selected" />
          </div>
          <div class="item-info">
            <img :src="item.image" alt="商品图片" class="item-image" />
            <div class="item-details">
              <h2>{{ item.name }}</h2>
              <p>价格: ¥{{ item.price.toFixed(2) }}</p>
            </div>
          </div>
          <div class="item-quantity">
            <button @click="decreaseQuantity(index)">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="increaseQuantity(index)">+</button>
          </div>
          <div class="item-total">
            <p>小计: ¥{{ (item.price * item.quantity).toFixed(2) }}</p>
            <button @click="openDeleteItemDialog(index)" class="remove-button">删除</button>
          </div>
        </li>
      </ul>

      <div class="cart-footer">
        <el-button type="danger" @click="openDeleteDialog">全部删除</el-button>
        <div class="cart-summary">
          <h3>总计: ¥{{ selectedTotal }}</h3>
          <el-button type="primary" @click="openCheckoutDialog">去结算</el-button>
        </div>
      </div>
    </div>

    <!-- 全部删除的确认弹框 -->
    <el-dialog
      title="确认删除"
      :visible.sync="deleteDialogVisible"
      width="30%"
    >
      <span>确定要全部删除购物车中的商品吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmDeleteAll">确定</el-button>
      </span>
    </el-dialog>

    <!-- 单个商品删除的确认弹框 -->
    <el-dialog
      title="确认删除"
      :visible.sync="deleteItemDialogVisible"
      width="30%"
    >
      <span>确定要删除该商品吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteItemDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmDeleteItem">确定</el-button>
      </span>
    </el-dialog>

    <!-- 收货信息填写对话框 -->
    <el-dialog
      title="填写收货信息"
      :visible.sync="checkoutDialogVisible"
      width="40%"
    >
      <el-form :model="checkoutForm" ref="checkoutForm">
        <el-form-item label="收货人姓名" required>
          <el-input v-model="checkoutForm.recipientName"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" required>
          <el-input v-model="checkoutForm.recipientPhone" type="tel"></el-input>
        </el-form-item>
        <el-form-item label="收货地址" required>
          <el-input v-model="checkoutForm.shippingAddress"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="checkoutDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmCheckout">确认结算</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getCartItems, removeFromCart, clearCart, updateCartItem } from "../../api/cart.js"
import { addOrders } from "../../api/orders.js";

export default {
  data() {
    return {
      cartItems: [],
      deleteDialogVisible: false,
      deleteItemDialogVisible: false,
      checkoutDialogVisible: false,
      checkoutForm: {
        recipientName: '',
        recipientPhone: '',
        shippingAddress: ''
      },
      currentItemIndex: null,
    };
  },
  computed: {
    selectedTotal() {
      return this.cartItems
        .filter((item) => item.selected)
        .reduce((total, item) => total + item.price * item.quantity, 0)
        .toFixed(2);
    },
  },
  methods: {
    async fetchCartItems() {
      try {
        const response = await getCartItems(this.$store.state.user.userId);
        if (response.code === 200) {
          this.cartItems = response.data;
        } else {
          this.$message.error('获取购物车数据失败');
        }
      } catch (error) {
        this.$router.push('/login');
        this.$message.error('请登录后查看购物车');
      }
    },
    
    increaseQuantity(index) {
      let item = this.cartItems[index];
      updateCartItem(this.$store.state.user.userId, item.id, item.quantity + 1).then(() => {
        this.fetchCartItems();
      });
    },
    decreaseQuantity(index) {
      if (this.cartItems[index].quantity > 1) {
        let item = this.cartItems[index];
        updateCartItem(this.$store.state.user.userId, item.id, item.quantity - 1).then(() => {
          this.fetchCartItems();
        });
      }
    },
    openDeleteItemDialog(index) {
      this.currentItemIndex = index;
      this.deleteItemDialogVisible = true;
    },
    confirmDeleteItem() {
      let item = this.cartItems[this.currentItemIndex];
      removeFromCart(this.$store.state.user.userId, item.id).then(() => {
        this.cartItems.splice(this.currentItemIndex, 1);
      });
      this.deleteItemDialogVisible = false;
    },
    openDeleteDialog() {
      this.deleteDialogVisible = true;
    },
    confirmDeleteAll() {
      clearCart(this.$store.state.user.userId).then(() => {
        this.cartItems = [];
      });
      this.deleteDialogVisible = false;
    },
    openCheckoutDialog() {
      const selectedItems = this.cartItems.filter((item) => item.selected);
      if (selectedItems.length === 0) {
        this.$message.error('请选择至少一件商品进行结算');
        return;
      }
      this.checkoutDialogVisible = true; // 显示结算对话框
    },
    async confirmCheckout() {
    if (this.$refs.checkoutForm.validate()) {
      const selectedItems = this.cartItems.filter(item => item.selected);
      
      const ordersList = []; // 初始化一个空数组
      const proIdList = [];
      
selectedItems.forEach(item => {
  const order = {
    userId: this.$store.state.user.userId,
    productName: item.name, // 单个商品名称
    totalAmount: this.selectedTotal, // 总金额
    recipientName: this.checkoutForm.recipientName,
    recipientPhone: this.checkoutForm.recipientPhone,
    shippingAddress: this.checkoutForm.shippingAddress,
    // 其他必要的字段可以添加在这里
  };
  ordersList.push(order); // 将 order 对象添加到 orderData 数组中
  proIdList.push(item.id)
});
      
      try {
        const response = await addOrders(ordersList, proIdList, this.$store.state.user.userId); // 调用创建订单的 API
        if (response.code === 200) {
          // 成功创建订单，移除已结算的商品
          this.cartItems = this.cartItems.filter(item => !item.selected);
          this.$message.success('结算成功！');
        } else {
          this.$message.error('结算失败，请重试。');
        }
      } catch (error) {
        this.$message.error('结算过程中发生错误，请稍后再试。');
      }

      this.checkoutDialogVisible = false; // 隐藏对话框
      this.checkoutForm = { recipientName: '', recipientPhone: '', shippingAddress: '' }; // 重置表单
    }
  }
  },
  mounted() {
    this.fetchCartItems(); // 组件挂载后获取购物车数据
  },
};
</script>


  <style scoped>
  /* 页面整体布局 */
.cart-page {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

/* 空购物车的样式 */
.empty-cart {
  text-align: center;
}

.empty-cart p {
  font-size: 18px;
  margin-bottom: 20px;
}

.empty-cart a {
  color: #c55137;
  text-decoration: none;
}

/* 确保每个购物车项的布局 */
.cart-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding: 15px 0;
  transition: background-color 0.3s ease;
  width: 100%;
}

/* 选择商品的复选框样式 */
.item-selection {
  margin-right: 10px;
}

/* 商品信息布局 */
.item-info {
  display: flex;
  align-items: center;
  flex: 1;
}

.item-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 10px;
  margin-right: 20px;
}

.item-details {
  display: flex;
  flex-direction: column;
}

.item-details h2 {
  font-size: 18px;
  margin-bottom: 5px;
  color: #333;
}

.item-details p {
  font-size: 16px;
  color: #666;
}

/* 商品数量控制 */
.item-quantity {
  display: flex;
  align-items: center;
  min-width: 120px; /* 确保有足够的宽度 */
  justify-content: center;
}

.item-quantity button {
  width: 30px;
  height: 30px;
  background-color: #f4f4f4;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
  margin: 0 5px; /* 控制按钮间距 */
  flex-shrink: 0; /* 防止缩小 */
}

.item-quantity span {
  font-size: 16px;
  min-width: 30px; /* 确保数字区域有足够宽度 */
  text-align: center; /* 确保数字居中 */
}

/* 商品小计和删除按钮 */
.item-total {
  text-align: right;
  min-width: 150px; /* 确保有足够的宽度 */
}

.item-total p {
  margin-bottom: 10px;
  font-size: 16px;
  color: #333;
}

.remove-button {
  color: #d9534f;
  border: none;
  background: none;
  cursor: pointer;
}

/* 购物车底部按钮布局 */
.cart-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  align-items: center;
}

.cart-summary {
  min-width: 200px; /* 确保有足够的宽度 */
}

.cart-summary h3 {
  font-size: 24px;
  color: #333;
}

.delete-all-button {
  background-color: #d9534f;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
}

.checkout-button {
  background-color: #c55137;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
}

.checkout-button:hover {
  background-color: #a4432e;
}

  </style>
  

