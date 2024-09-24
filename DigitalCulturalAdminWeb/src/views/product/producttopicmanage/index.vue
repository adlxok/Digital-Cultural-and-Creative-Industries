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
        <el-button type="primary" @click="openAddDialog">添加分类</el-button>
      </el-col>
    </el-row>

    <!-- 分类列表 -->
    <el-table :data="categories" style="width: 100%" stripe>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="主题名称"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180"></el-table-column>
      <el-table-column prop="isDeleted" label="状态" width="120">
        <template #default="scope">
          <el-switch
            v-model="scope.row.isDeleted"
            :active-value="false"
            :inactive-value="true"
            @change="toggleStatus(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
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

import { selectAll, updateStatus, insertone, updateTopic, deleteOne } from '@/api/topic';

export default {
  data() {
    return {
      categories: [],
      showAddDialog: false,
      editMode: false,
      currentCategory: {
        id: null,
        name: '',
        description: '',
        isDeleted: 0, // 默认启用状态
      },
      rules: {
        name: [{ required: true, message: '请输入主题名称', trigger: 'blur' }],
      },
    };
  },
  mounted() {
    this.fetchCategories(); // 页面加载时获取分类列表
  },
  methods: {
    // 获取所有分类
    fetchCategories() {
      selectAll()
        .then((response) => {
          this.categories = response.data;
        })
        .catch((error) => {
          console.error('获取分类列表失败:', error);
        });
    },
    // 切换状态
    toggleStatus(category) {
      let data = {
        topicId: category.id,
      };

      updateStatus(data)
        .then(() => {
          this.$message.success(`主题${!category.isDeleted ? '已启用' : '已禁用'}`);
        })
        .catch((error) => {
          console.error('状态切换失败:', error);
          this.$message.error('状态切换失败');
        });
    },
    // 提交分类（添加或更新）
    submitCategory() {
      this.$refs.categoryForm.validate((valid) => {
        if (valid) {
          if (this.editMode) {
            let data = {
              topicId: this.currentCategory.id,
              name: this.currentCategory.name,
              description: this.currentCategory.description,
            }
            // 编辑模式
            updateTopic(data)
            .then(() => {
              this.$message.success('分类更新成功');
              this.fetchCategories(); // 更新列表
            }).catch((error) => {
              console.error('更新分类失败:', error);
            });
          } else {
            // 添加模式
            insertone(this.currentCategory)
            
              .then(() => {
                this.$message.success('分类添加成功');
                this.fetchCategories(); // 更新列表
              })
              .catch((error) => {
                console.error('添加分类失败:', error);
              });
          }
          this.closeDialog();
        }
      });
    },
    // 打开添加对话框
    openAddDialog() {
      this.currentCategory = { id: null, name: '', description: '', isDeleted: 0 };
      this.editMode = false;
      this.showAddDialog = true;
    },
    // 打开编辑对话框
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
        let data = {
          topid: id
        }
        deleteOne(data)
          .then(() => {
            this.$message.success('分类已删除');
            this.fetchCategories(); // 更新列表
          })
          .catch((error) => {
            console.error('删除分类失败:', error);
          });
      });
    },
    // 关闭对话框
    closeDialog() {
      this.showAddDialog = false;
      this.editMode = false;
      this.currentCategory = { id: null, name: '', description: '', isDeleted: 0 }; // 重置当前分类
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
