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
        <el-input v-model="searchQuery" placeholder="搜索商品名称" @input="filterProducts" clearable />
      </el-col>
      <el-col :span="8">
        <el-select v-model="statusFilter" placeholder="选择状态" @change="filterProducts" clearable>
          <el-option label="所有状态" value=""></el-option>
          <el-option label="上架" value="1"></el-option>
          <el-option label="下架" value="0"></el-option>
        </el-select>
      </el-col>
      <el-col :span="8">
        <el-button type="primary" @click="goadd">新增商品</el-button>
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
          <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#ff4949" @change="toggleProductStatus(scope.row)" />
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
    <el-pagination layout="prev, pager, next" :total="filteredProducts.length" :page-size="itemsPerPage" @current-change="changePage" class="pagination"></el-pagination>

    <!-- 新增/编辑商品的对话框 -->
    <el-dialog :title="showEditProductModal ? '编辑商品' : '新增商品'" :visible.sync="showAddProductModal" width="30%">
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
import axios from 'axios';
import {selectAll, updateStatus, updateProducts, deleteOne} from '@/api/products'

export default {
  data() {
    return {
      products: [], // 这里将从后端获取数据
      searchQuery: '',
      statusFilter: '',
      currentPage: 1,
      itemsPerPage: 5,
      showAddProductModal: false,
      showEditProductModal: false,
      currentProduct: { id: null, name: '', price: 0, stock: 0, status: 1,  },
    };
  },
  computed: {
    filteredProducts() {
      let filtered = this.products.filter((product) => product.name.toLowerCase().includes(this.searchQuery.toLowerCase()));
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
  },
  mounted() {
    this.fetchProducts(); // 页面加载时获取商品列表
  },
  methods: {
    goadd() {
      this.$router.push("/product/addproduct")
    },
    // 获取所有商品
    fetchProducts() {
      selectAll()
      .then((response) => {
        this.products = response.data;
      }).catch((error) => {
        console.error('获取商品列表失败:', error);
      });
    },
    filterProducts() {
      this.currentPage = 1; // 重新过滤时重置页码
    },
    changePage(page) {
      this.currentPage = page;
    },
    addProduct() {
      axios.post('/products/insertOne', this.currentProduct).then(() => {
        this.$message.success('商品添加成功');
        this.fetchProducts(); // 更新商品列表
        this.closeModal();
      }).catch((error) => {
        console.error('添加商品失败:', error);
      });
    },
    editProduct(product) {
      this.currentProduct = { ...product };
      this.showEditProductModal = true;
      this.showAddProductModal = true;
    },
    updateProduct() {
      let data = 
        this.currentProduct;
      
      updateProducts(data)
      .then(() => {
        this.$message.success('商品更新成功');
        this.fetchProducts(); // 更新商品列表
        this.closeModal();
      }).catch((error) => {
        console.error('更新商品失败:', error);
      });
    },
    deleteProduct(productId) {
      this.$confirm('确定要删除该商品吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        let data = {
          proId: productId
        }
        deleteOne(data)
        .then(() => {
          this.$message.success('商品已删除');
          this.fetchProducts(); // 更新商品列表
        }).catch((error) => {
          console.error('删除商品失败:', error);
        });
      });
    },
    toggleProductStatus(product) {
      let data = {
        proId: product.id
      }
      updateStatus(data)
      .then(() => {
        this.$message.success(`商品${product.status === 1 ? '已上架' : '已下架'}`);
      }).catch((error) => {
        console.error('切换商品状态失败:', error);
      });
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
