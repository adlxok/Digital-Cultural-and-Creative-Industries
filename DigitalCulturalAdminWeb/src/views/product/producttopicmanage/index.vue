<template>
    <div class="topics-categories-management">
      <!-- 页面标题 -->
      <el-row>
        <el-col :span="24">
          <h2 class="title">主题分类管理</h2>
        </el-col>
      </el-row>
  
      <!-- 添加按钮 -->
      <el-row>
        <el-col :span="24" class="add-button">
          <el-button type="primary" @click="showAddDialog = true">添加分类</el-button>
        </el-col>
      </el-row>
  
      <!-- 分类列表 -->
      <el-table :data="categories" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="主题名称"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="created_at" label="创建时间" width="180"></el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button size="mini" @click="editCategory(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteCategory(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 添加/编辑分类的对话框 -->
      <el-dialog
        :title="editMode ? '编辑分类' : '添加分类'"
        :visible.sync="showAddDialog"
        width="30%"
      >
        <el-form :model="currentCategory" :rules="rules" ref="categoryForm" label-width="100px">
          <el-form-item label="主题名称" prop="name">
            <el-input v-model="currentCategory.name"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input type="textarea" v-model="currentCategory.description"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeDialog">取消</el-button>
          <el-button type="primary" @click="submitCategory">提交</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        categories: [
          { id: 1, name: '主题分类A', description: '这是主题分类A的描述', created_at: '2024-09-01' },
          { id: 2, name: '主题分类B', description: '这是主题分类B的描述', created_at: '2024-09-05' },
          // 更多主题分类...
        ],
        showAddDialog: false,
        editMode: false,
        currentCategory: {
          id: null,
          name: '',
          description: ''
        },
        rules: {
          name: [{ required: true, message: '请输入主题名称', trigger: 'blur' }],
        },
      };
    },
    methods: {
      // 打开编辑分类对话框
      editCategory(category) {
        this.currentCategory = { ...category };
        this.editMode = true;
        this.showAddDialog = true;
      },
      // 删除分类
      deleteCategory(id) {
        this.$confirm('确定要删除该分类吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.categories = this.categories.filter(category => category.id !== id);
          this.$message.success('分类已删除');
        }).catch(() => {
          this.$message.info('已取消删除');
        });
      },
      // 提交添加或编辑的分类
      submitCategory() {
        this.$refs.categoryForm.validate((valid) => {
          if (valid) {
            if (this.editMode) {
              // 编辑模式
              const index = this.categories.findIndex(category => category.id === this.currentCategory.id);
              if (index !== -1) {
                this.categories.splice(index, 1, { ...this.currentCategory });
              }
              this.$message.success('分类编辑成功');
            } else {
              // 添加模式
              const newCategory = { ...this.currentCategory, id: Date.now(), created_at: new Date().toISOString().split('T')[0] };
              this.categories.push(newCategory);
              this.$message.success('分类添加成功');
            }
            this.closeDialog();
          } else {
            console.log('表单验证失败');
            return false;
          }
        });
      },
      // 关闭对话框
      closeDialog() {
        this.showAddDialog = false;
        this.editMode = false;
        this.currentCategory = { id: null, name: '', description: '' };
      },
    },
  };
  </script>
  
  <style scoped>
  .topics-categories-management {
    padding: 20px;
  }
  
  .title {
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  .add-button {
    margin-bottom: 20px;
  }
  </style>
  