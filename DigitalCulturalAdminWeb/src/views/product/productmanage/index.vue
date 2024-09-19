<template>
    <div class="product-management">
      <!-- 页面标题 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <h1 class="title">商品管理</h1>
        </el-col>
      </el-row>
  
      <!-- 搜索和筛选 -->
      <el-row :gutter="20" class="filters">
        <el-col :span="8">
          <el-input
            v-model="searchQuery"
            placeholder="搜索商品名称"
            @input="filterProducts"
            clearable
          />
        </el-col>
        <el-col :span="8">
          <el-select v-model="statusFilter" placeholder="选择状态" @change="filterProducts" clearable>
            <el-option label="所有状态" value=""></el-option>
            <el-option label="上架" value="1"></el-option>
            <el-option label="下架" value="0"></el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" @click="goToAddProduct">新增商品</el-button>
        </el-col>
      </el-row>
  
      <!-- 商品列表 -->
      <el-table :data="paginatedProducts" style="width: 100%;" class="product-table" stripe>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="商品名称"></el-table-column>
        <el-table-column prop="price" label="价格" width="100"></el-table-column>
        <el-table-column prop="stock" label="库存" width="80"></el-table-column>
        <el-table-column label="状态" width="120">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="toggleProductStatus(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button size="mini" @click="editProduct(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteProduct(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 分页 -->
      <el-pagination
        layout="prev, pager, next"
        :total="filteredProducts.length"
        :page-size="itemsPerPage"
        @current-change="changePage"
        class="pagination"
      ></el-pagination>
  
      <!-- 新增/编辑商品的对话框 -->
      <el-dialog
        :title="showEditProductModal ? '编辑商品' : '新增商品'"
        :visible.sync="showAddProductModal"
        width="30%"
      >
        <el-form :model="currentProduct" label-width="80px">
          <el-form-item label="商品名称">
            <el-input v-model="currentProduct.name"></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model.number="currentProduct.price" type="number"></el-input>
          </el-form-item>
          <el-form-item label="库存">
            <el-input v-model.number="currentProduct.stock" type="number"></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="currentProduct.status" placeholder="选择状态">
              <el-option label="上架" value="1"></el-option>
              <el-option label="下架" value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeModal">取消</el-button>
          <el-button type="primary" @click="showEditProductModal ? updateProduct() : addProduct()">提交</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        products: [
          { id: 1, name: '商品A', price: 100, stock: 10, status: 1 },
          { id: 2, name: '商品B', price: 200, stock: 5, status: 0 },
          { id: 3, name: '商品C', price: 300, stock: 0, status: 1 },
          // 更多商品数据...
        ],
        searchQuery: '',
        statusFilter: '',
        currentPage: 1,
        itemsPerPage: 5,
        showAddProductModal: false,
        showEditProductModal: false,
        currentProduct: { id: null, name: '', price: 0, stock: 0, status: 1 },
      };
    },
    computed: {
      filteredProducts() {
        let filtered = this.products.filter((product) =>
          product.name.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
        if (this.statusFilter !== '') {
          filtered = filtered.filter((product) => product.status === Number(this.statusFilter));
        }
        return filtered;
      },
      paginatedProducts() {
        const start = (this.currentPage - 1) * this.itemsPerPage;
        const end = start + this.itemsPerPage;
        return this.filteredProducts.slice(start, end);
      },
      totalPages() {
        return Math.ceil(this.filteredProducts.length / this.itemsPerPage);
      },
    },
    methods: {
      filterProducts() {
        this.currentPage = 1; // 重新过滤时重置页码
      },
      changePage(page) {
        this.currentPage = page;
      },
      goToAddProduct() {
        // 使用 Vue Router 的编程式导航跳转到 /product/addproduct
        this.$router.push('/product/addproduct');
      },
      addProduct() {
        const newProduct = { ...this.currentProduct, id: Date.now() };
        this.products.push(newProduct);
        this.closeModal();
      },
      editProduct(product) {
        this.currentProduct = { ...product };
        this.showEditProductModal = true;
      },
      updateProduct() {
        const index = this.products.findIndex(product => product.id === this.currentProduct.id);
        if (index !== -1) {
          this.products.splice(index, 1, { ...this.currentProduct });
        }
        this.closeModal();
      },
      deleteProduct(productId) {
        this.products = this.products.filter(product => product.id !== productId);
      },
      toggleProductStatus(product) {
        // 可在此添加处理逻辑，例如发送 API 请求更新商品状态
        console.log(`商品 ${product.name} 状态变更为：${product.status === 1 ? '上架' : '下架'}`);
      },
      closeModal() {
        this.showAddProductModal = false;
        this.showEditProductModal = false;
        this.currentProduct = { id: null, name: '', price: 0, stock: 0, status: 1 };
      },
    },
  };
  </script>
  
  <style scoped>
  .product-management {
    padding: 20px;
  }
  
  .title {
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  .filters {
    margin-bottom: 20px;
  }
  
  .pagination {
    text-align: right;
    margin-top: 20px;
  }
  </style>
  