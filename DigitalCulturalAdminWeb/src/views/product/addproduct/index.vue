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
  
        <!-- 主题 -->
        <el-form-item label="主题" prop="topicId">
          <el-select v-model="product.topicId" placeholder="请选择主题">
            <el-option v-for="topic in topics" :key="topic.id" :label="topic.name" :value="topic.id"></el-option>
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
  import {selectAll} from '@/api/topic'
  import {insertOne} from '@/api/products'

  export default {
    
    data() {
      return {
        product: {
          name: '',
          description: '',
          topicId: null,
          price: 0,
          discount_price: null,
          stock: 0,
          status: 1,
        },
        topics: [],
        rules: {
          name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
          category_id: [{ required: true, message: '请选择分类', trigger: 'change' }],
          topic_id: [{ required: true, message: '请选择主题', trigger: 'change' }],
          price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
          stock: [{ required: true, message: '请输入库存数量', trigger: 'blur' }],
        },
      };
    },
    mounted() {
    this.fetchData(); // 页面加载时获取分类列表
    }, 
    methods: {
      fetchData() {
        selectAll().then((res) => {
          this.topics=res.data
        })
        
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = this.product
            // 处理提交的逻辑，例如发送 API 请求
            insertOne(data).then(() => {
              
              this.$message.success('商品添加成功');
            })
            
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
  