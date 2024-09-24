<template>
  <div class="product-list">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <input
        type="text"
        v-model="searchTerm"
        placeholder="搜索商品..."
        @input="filterProducts"
        class="search-input"
      />
    </div>

    <!-- 排序按钮 -->
    <div class="sort-buttons">
      <button @click="sortProducts('asc')" class="sort-button">价格从低到高</button>
      <button @click="sortProducts('desc')" class="sort-button">价格从高到低</button>
      <button @click="sortProducts('time')" class="sort-button">按时间排序</button>
    </div>

    <!-- 分类导航 -->
    <div class="category-nav">
      <div class="category-buttons">
        <button
          v-for="(category) in displayedCategories"
          :key="category.id"
          @click="filterCategory(category.id)"
          :class="{ active: selectedCategory === category.id }"
          class="category-button"
        >
          {{ category.name }}
        </button>
      </div>
      <div class="button-wrapper">
        <button class="toggle-button" @click="toggleShowAll">
          {{ showAll ? "收起更多" : "展开更多" }}
        </button>
      </div>
    </div>

    <!-- 商品列表 -->
    <div v-if="loading" class="loading">加载中...</div>
    <div v-if="error" class="error">{{ error }}</div>
    <div class="products-grid" v-if="filteredProducts.length">
      <router-link
        v-for="product in filteredProducts"
        :key="product.id"
        :to="{ name: 'ProductDetail', params: { id: product.id } }"
        class="product-card"
      >
        <img :src="product.imageUrl" alt="Product Image" class="product-image" />
        <div class="product-info">
          <h3>{{ product.name }}</h3>
          <p class="description">{{ product.description }}</p>
          <p class="price">¥{{ product.price }}</p>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
import { getallproducts } from "../../api/mall";
import { getallTopics } from '../../api/getallTopics';

export default {
  data() {
    return {
      products: [],
      filteredProducts: [],
      categories: [],
      showAll: false,
      initialCategoriesToShow: 6,
      selectedCategory: null,
      loading: false,
      error: null,
      searchTerm: '', // 新增搜索关键词
    };
  },
  computed: {
    displayedCategories() {
      return this.showAll ? this.categories : this.categories.slice(0, this.initialCategoriesToShow);
    },
  },
  methods: {
    async fetchProducts() {
      this.loading = true;
      try {
        const res = await getallproducts();
        this.products = res.data;
        this.filterProducts(); // 在获取产品后立即过滤
      } catch (error) {
        this.error = "获取商品数据失败，请稍后再试。";
      } finally {
        this.loading = false;
      }
    },
    async fetchCategories() {
      try {
        const res = await getallTopics();
        this.categories = [{ id: null, name: "全部" }, ...res.data];
      } catch (error) {
        console.error('获取分类数据失败', error);
      }
    },
    filterCategory(categoryId) {
      this.selectedCategory = categoryId;
      this.filterProducts(); // 过滤产品
    },
    filterProducts() {
      this.filteredProducts = this.products.filter(product => {
        const matchesCategory = this.selectedCategory === null || product.topicId === this.selectedCategory;
        const matchesSearch = product.name.includes(this.searchTerm);
        return matchesCategory && matchesSearch;
      });
    },
    sortProducts(order) {
      if (order === 'asc') {
        this.filteredProducts.sort((a, b) => a.price - b.price);
      } else if (order === 'desc') {
        this.filteredProducts.sort((a, b) => b.price - a.price);
      } else if (order === 'time') {
        this.filteredProducts.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)); // 假设产品数据中有 createTime 字段
      }
    },
    toggleShowAll() {
      this.showAll = !this.showAll;
    },
  },
  mounted() {
    this.fetchCategories();
    this.fetchProducts();
  },
};
</script>

<style scoped>
.product-list {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

/* 搜索栏 */
.search-bar {
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

/* 排序按钮 */
.sort-buttons {
  margin-bottom: 20px;
}

.sort-button {
  background-color: white;
  color: #333;
  border: 1px solid #ddd;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
  transition: background-color 0.3s, color 0.3s;
}

.sort-button:hover {
  background-color: #42b983;
  color: white;
}

/* 分类导航 */
.category-nav {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
}

.category-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  max-width: 800px;
}

/* 分类按钮 */
.category-button {
  background-color: white;
  color: #333;
  border: 1px solid #ddd;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

/* 鼠标悬停和激活状态 */
.category-button:hover {
  background-color: #42b983;
  color: white;
}
.category-button.active {
  background-color: #42b983;
  color: white;
}

.button-wrapper {
  margin-left: auto;
  padding-top: 8px;
}

/* 展开/收起按钮 */
.toggle-button {
  background-color: white;
  color: #333;
  border: 1px solid #ddd;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

/* 展开/收起按钮悬停效果 */
.toggle-button:hover {
  background-color: #42b983;
  color: white;
}

.products-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  gap: 20px;
}

.product-card {
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 10px;
  overflow: hidden;
  width: calc(33.333% - 20px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.product-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.product-info {
  padding: 15px;
}

h3 {
  font-size: 1.2em;
  margin: 0 0 10px;
}

.description {
  font-size: 0.9em;
  color: #666;
  height: 50px;
  overflow: hidden;
  text-overflow: ellipsis;
}

.price {
  font-size: 1.2em;
  color: #333;
  font-weight: bold;
  margin: 10px 0;
}

.loading,
.error {
  text-align: center;
  color: red;
}
</style>
