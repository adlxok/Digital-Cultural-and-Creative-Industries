<template>
  <div class="cart-page">
    <h1>购物车</h1>

    <!-- 如果购物车为空，显示提示信息 -->
    <div v-if="cartItems.length === 0" class="empty-cart">
      <p>购物车是空的</p>
      <router-link to="/mall">去购物</router-link>
    </div>

    <!-- 如果购物车有商品，展示商品列表 -->
    <div v-else>
      <ul class="cart-items">
        <li
          v-for="(item, index) in cartItems"
          :key="item.id"
          class="cart-item"
          :class="{ 'item-selected': item.selected }"
        >
          <div class="item-selection">
            <!-- 选择结算商品的复选框 -->
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

      <!-- 底部操作栏，包含总价、去结算和全部删除按钮 -->
      <div class="cart-footer">
        <el-button type="danger" @click="openDeleteDialog">全部删除</el-button>
        <div class="cart-summary">
          <h3>总计: ¥{{ selectedTotal }}</h3>
          <el-button type="primary" @click="checkout">去结算</el-button>
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
  </div>
</template>


<script>
import { getCartItems, removeFromCart, clearCart, updateCartItem} from "../../api/cart.js"

export default {
  data() {
    return {
      cartItems: [], // 购物车项
      deleteDialogVisible: false, // 控制全部删除弹框的显示
      deleteItemDialogVisible: false, // 控制单个商品删除弹框的显示
      currentItemIndex: null, // 当前选中的商品索引，用于删除
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
        // 从后端 API 获取购物车数据
        const response = await getCartItems(this.$store.state.user.userId);
        
        // 处理获取的数据
        if (response.code === 200) {
            this.cartItems = response.data;
        } else {
          this.$message.error('获取购物车数据失败');
        }
        console.log(response.data)
        
        
      } catch (error) {
        this.$router.push('/login');
        this.$message.error('请登录后查看购物车');
      }
    },
    
    increaseQuantity(index) {
      let item = this.cartItems[index]
      updateCartItem(this.$store.state.user.userId, item.id, this.cartItems[index].quantity + 1).then(() => {
        this.cartItems[index].quantity++;
      })
    },
    decreaseQuantity(index) {
      if (this.cartItems[index].quantity > 1) {
        let item = this.cartItems[index]
        updateCartItem(this.$store.state.user.userId, item.id, this.cartItems[index].quantity - 1).then(() => {
        this.cartItems[index].quantity--;
      })
      }
    },
    openDeleteItemDialog(index) {
      this.currentItemIndex = index;
      this.deleteItemDialogVisible = true;
    },
    confirmDeleteItem() {
      
      let item = this.cartItems[this.currentItemIndex]
      removeFromCart(this.$store.state.user.userId, item.id).then((res) => {
        console.log(res)
        this.cartItems.splice(this.currentItemIndex, 1);
      })
      .catch((e) => {
        console.log(e)
      })
      this.deleteItemDialogVisible = false;
    },
    openDeleteDialog() {
      this.deleteDialogVisible = true;
    },
    confirmDeleteAll() {
      
      clearCart(this.$store.state.user.userId).then((res) => {
        this.cartItems = [];
        console.log(res)
      }).catch((e) => {
        console.log(e)
      })
      this.deleteDialogVisible = false;
    },
    checkout() {
      const selectedItems = this.cartItems.filter((item) => item.selected);
      if (selectedItems.length === 0) {
        this.$message.error('请选择至少一件商品进行结算');
        return;
      }
      this.$message.success('结算成功！待结算商品数量：' + selectedItems.length);
    },
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
  

