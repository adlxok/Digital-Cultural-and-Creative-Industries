<template>
    <div class="user-management">
      <!-- 页面标题 -->
      <el-row>
        <el-col :span="24">
          <h2 class="title">用户管理</h2>
        </el-col>
      </el-row>
  
      <!-- 搜索栏 -->
      <el-row :gutter="20" class="search-bar">
        <el-col :span="8">
          <el-input
            v-model="searchQuery"
            placeholder="搜索用户名"
            clearable
            @input="filterUsers"
          />
        </el-col>
      </el-row>
  
      <!-- 用户列表 -->
      <el-table :data="filteredUsers" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="bio" label="简介"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="true"
              :inactive-value="false"
              @change="toggleUserStatus(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="scope">
            <el-button size="mini" @click="editUser(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="confirmDeleteUser(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- 编辑用户的对话框 -->
      <el-dialog
        title="编辑用户"
        :visible.sync="showEditDialog"
        width="30%"
      >
        <el-form :model="currentUser" :rules="rules" ref="userForm" label-width="80px">
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="currentUser.nickname"></el-input>
          </el-form-item>
          <el-form-item label="简介" prop="bio">
            <el-input type="textarea" v-model="currentUser.bio"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeDialog">取消</el-button>
          <el-button type="primary" @click="submitUserEdit">保存</el-button>
        </div>
      </el-dialog>
  
      <!-- 删除用户的确认对话框 -->
      <el-dialog
        title="确认删除"
        :visible.sync="showDeleteDialog"
        width="30%"
      >
        <span>确定要删除这个用户吗？</span>
        <div slot="footer" class="dialog-footer">
          <el-button @click="showDeleteDialog = false">取消</el-button>
          <el-button type="danger" @click="deleteUser">删除</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { selectAll, updateUsers, deleteUsers, updateStatus } from '@/api/user'; 
  
  export default {
    data() {
      return {
        users: [],
        searchQuery: '',
        showEditDialog: false,
        showDeleteDialog: false, // 新增：删除确认对话框的可见性
        currentUser: {
          id: null,
          nickname: '',
          bio: '',
          status: true, // 默认状态为 true (启用)
        },
        userIdToDelete: null, // 新增：要删除的用户ID
        rules: {
          nickname: [{ required: true, message: '请输入用户昵称', trigger: 'blur' }],
        },
      };
    },
    computed: {
      filteredUsers() {
        return this.users.filter((user) =>
          (user.username || '').toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      },
    },
    mounted() {
      this.fetchUsers();
    },
    methods: {
      async fetchUsers() {
        try {
          const response = await selectAll(); // 确保 selectAll 能正确调用 API
          this.users = response.data; // 确保数据结构匹配
          console.log(this.users);
        } catch (error) {
          console.error('获取用户数据失败', error);
        }
      },
      filterUsers() {
        // 搜索过滤逻辑
      },
      editUser(user) {
        this.currentUser = { ...user };
        this.showEditDialog = true;
      },
      toggleUserStatus(user) {
        let data = {
            userId: user.id
        }
        updateStatus(data).then((res) => {
            console.log(res)
        })
        console.log(user)
        this.$message.success(`用户${user.status ? '已启用' : '已禁用'}`);
      },
      async submitUserEdit() {
        this.$refs.userForm.validate(async (valid) => {
          if (valid) {
            try {
                console.log(this.currentUser)
                let data = {
                    userId: this.currentUser.id,
                    blog: this.currentUser.bio,
                    nickname: this.currentUser.nickname
                }
              await updateUsers(data); // 调用 updateUsers API
              const index = this.users.findIndex((user) => user.id === this.currentUser.id);
              if (index !== -1) {
                this.users.splice(index, 1, { ...this.currentUser });
              }
              this.closeDialog();
              this.$message.success('用户信息已更新');
            } catch (error) {
              console.error('更新用户信息失败', error);
              this.$message.error('更新用户信息失败');
            }
          } else {
            console.log('表单验证失败');
            return false;
          }
        });
      },
      confirmDeleteUser(userId) {
        this.userIdToDelete = userId;
        this.showDeleteDialog = true;
      },
      deleteUser() {
        let data = {
            userId: this.userIdToDelete
        }
        deleteUsers(data).then((res) => {
            console.log(res)
        })
        // this.users = this.users.filter((user) => user.id !== this.userIdToDelete);
        this.$message.success('用户已删除');
        this.showDeleteDialog = false;
      },
      closeDialog() {
        this.showEditDialog = false;
        this.currentUser = { id: null, nickname: '', description: '', status: true }; // 默认状态为 true (启用)
      },
    },
  };
  </script>
  
  <style scoped>
  .user-management {
    padding: 20px;
  }
  
  .title {
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  .search-bar {
    margin-bottom: 20px;
  }
  </style>
  