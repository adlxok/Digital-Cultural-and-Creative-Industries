<template>
    <div class="product-detail-container">
      <div v-if="product" class="product-detail">
        <div class="product-header">
          <h1>{{ product.name }}</h1>
          <div class="product-meta">
            <div class="price-section">
              <span class="current-price">现价: ¥{{ product.discountPrice || product.price }}</span>
              <span v-if="product.discountPrice" class="original-price">原价: ¥{{ product.price }}</span>
            </div>
            <span class="stock">库存: {{ product.stock }}</span>
            <span class="sku">SKU: {{ product.sku }}</span>
          </div>
        </div>
        <div class="product-body">
          <img :src="product.imageUrl" alt="Product Image" class="product-image" />
          <div class="product-info">
            <p class="description">{{ product.description }}</p>
            <p class="additional-info">
              <span>重量: {{ product.weight }} kg</span>
              <span>销量: {{ product.salesVolume }}</span>
              <span>浏览量: {{ product.viewCount }}</span>
              <span>创建时间: {{ formatDate(product.createdAt) }}</span>
            </p>
            <button class="add-to-cart" @click="addToCart(product)">加入购物车</button>
          </div>
        </div>
      </div>
      <div v-if="loading" class="loading">加载中...</div>
      <div v-if="error" class="error">{{ error }}</div>
    </div>
  </template>
  
  <script>
  import { selectProductById } from '../../api/mall';
  import {addToCart} from '../../api/cart'
  
  export default {
    props: ['id'],
    data() {
      return {
        product: null,
        loading: false,
        error: null
      };
    },
    methods: {
      async fetchProduct() {
        this.loading = true;
        try {
          const productId = this.$route.params.id; // 从路由参数中获取商品 ID
          const response = await selectProductById(productId);
          this.product = response.data;
        } catch (error) {
          this.error = "获取商品详情失败，请稍后再试。";
        } finally {
          this.loading = false;
        }
      },
      addToCart(product) {
        console.log(this.$store.state.user.userId)
        addToCart(this.$store.state.user.userId, product.id, 1).then((res) => {
          console.log(res)
        })
        // console.log(`商品 ${product.name} 已加入购物车`);
        this.$message.success(`商品 ${product.name} 已加入购物车`)
      },
      formatDate(date) {
        return new Date(date).toLocaleDateString(); // 格式化日期
      }
    },
    mounted() {
      this.fetchProduct();
    }
  };
  </script>
  
  <style scoped>
  .product-detail-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .product-detail {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .product-header {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  .product-meta {
    display: flex;
    /* flex-direction: column; */
    gap: 10px;
    font-size: 16px;
    color: #555;
  }
  
  .price-section {
    display: flex;
    /* flex-direction: column; */
    gap: 5px;
  }
  
  .current-price {
    font-weight: bold;
    color: #e53935; /* 高亮显示现价 */
  }
  
  .original-price {
    text-decoration: line-through; /* 原价添加删除线 */
    color: #999;
  }
  
  .product-body {
    display: flex;
    gap: 20px;
  }
  
  .product-image {
    width: 100%;
    max-width: 500px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .product-info {
    flex: 1;
  }
  
  .description {
    font-size: 18px;
    color: #333;
    margin-bottom: 20px;
  }
  
  .additional-info {
    font-size: 16px;
    color: #777;
    margin-bottom: 20px;
  }
  
  .additional-info span {
    display: block;
    margin-bottom: 5px;
  }
  
  .add-to-cart {
    background-color: #42b983;
    color: white;
    padding: 12px 24px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 18px;
    font-weight: bold;
  }
  
  .add-to-cart:hover {
    background-color: #369f74;
  }
  
  .loading, .error {
    text-align: center;
    color: red;
  }
  </style>