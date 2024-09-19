<template>
    <div class="add-product">
      <!-- 页面标题 -->
      <el-row>
        <el-col :span="24">
          <h2 class="title">添加商品</h2>
        </el-col>
      </el-row>
  
      <!-- 商品添加表单 -->
      <el-form :model="product" :rules="rules" ref="productForm" label-width="120px" class="product-form">
        <!-- 商品名称 -->
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="product.name"></el-input>
        </el-form-item>
  
        <!-- 商品描述 -->
        <el-form-item label="商品描述" prop="description">
          <el-input type="textarea" v-model="product.description"></el-input>
        </el-form-item>
  
        <!-- 分类 -->
        <el-form-item label="分类" prop="category_id">
          <el-select v-model="product.category_id" placeholder="请选择分类">
            <el-option v-for="category in categories" :key="category.id" :label="category.name" :value="category.id"></el-option>
          </el-select>
        </el-form-item>
  
        <!-- 主题 -->
        <el-form-item label="主题" prop="topic_id">
          <el-select v-model="product.topic_id" placeholder="请选择主题">
            <el-option v-for="topic in topics" :key="topic.id" :label="topic.name" :value="topic.id"></el-option>
          </el-select>
        </el-form-item>
  
        <!-- 品牌 -->
        <el-form-item label="品牌" prop="brand_id">
          <el-select v-model="product.brand_id" placeholder="请选择品牌">
            <el-option v-for="brand in brands" :key="brand.id" :label="brand.name" :value="brand.id"></el-option>
          </el-select>
        </el-form-item>
  
        <!-- 价格 -->
        <el-form-item label="价格" prop="price">
          <el-input v-model.number="product.price" type="number" min="0"></el-input>
        </el-form-item>
  
        <!-- 折扣价格 -->
        <el-form-item label="折扣价格" prop="discount_price">
          <el-input v-model.number="product.discount_price" type="number" min="0"></el-input>
        </el-form-item>
  
        <!-- 库存 -->
        <el-form-item label="库存" prop="stock">
          <el-input v-model.number="product.stock" type="number" min="0"></el-input>
        </el-form-item>
  
        <!-- 商品状态 -->
        <el-form-item label="商品状态" prop="status">
          <el-switch v-model="product.status" :active-value="1" :inactive-value="0" active-text="上架" inactive-text="下架"></el-switch>
        </el-form-item>
  
        <!-- SKU编号 -->
        <el-form-item label="SKU编号" prop="sku">
          <el-input v-model="product.sku"></el-input>
        </el-form-item>
  
        <!-- 重量 -->
        <el-form-item label="重量(kg)" prop="weight">
          <el-input v-model.number="product.weight" type="number" min="0" step="0.01"></el-input>
        </el-form-item>
  
        <!-- 主图URL -->
        <el-form-item label="主图URL" prop="image_url">
          <el-input v-model="product.image_url"></el-input>
        </el-form-item>
  
        <!-- 提交按钮 -->
        <el-form-item>
          <el-button type="primary" @click="submitForm('productForm')">提交</el-button>
          <el-button @click="resetForm('productForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        product: {
          name: '',
          description: '',
          category_id: null,
          topic_id: null,
          brand_id: null,
          price: 0,
          discount_price: null,
          stock: 0,
          status: 1,
          sku: '',
          weight: 0,
          image_url: '',
        },
        categories: [
          { id: 1, name: '分类A' },
          { id: 2, name: '分类B' },
          { id: 3, name: '分类C' },
        ],
        topics: [
          { id: 1, name: '主题A' },
          { id: 2, name: '主题B' },
          { id: 3, name: '主题C' },
        ],
        brands: [
          { id: 1, name: '品牌A' },
          { id: 2, name: '品牌B' },
          { id: 3, name: '品牌C' },
        ],
        rules: {
          name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
          category_id: [{ required: true, message: '请选择分类', trigger: 'change' }],
          topic_id: [{ required: true, message: '请选择主题', trigger: 'change' }],
          price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
          stock: [{ required: true, message: '请输入库存数量', trigger: 'blur' }],
        },
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // 处理提交的逻辑，例如发送 API 请求
            console.log('提交成功:', this.product);
            this.$message.success('商品添加成功');
          } else {
            console.log('提交失败');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    },
  };
  </script>
  
  <style scoped>
  .add-product {
    padding: 20px;
  }
  
  .title {
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  .product-form {
    max-width: 600px;
  }
  </style>
  