<template>
  <div>
    <el-table :data="filteredData" style="width: 100%">
      <el-table-column label="ID" prop="id"></el-table-column>
      <el-table-column label="用户ID" prop="userId"></el-table-column>
      <el-table-column label="标题" prop="title"></el-table-column>
      <el-table-column label="文本内容" prop="content"></el-table-column>
      <el-table-column label="类型" prop="type"></el-table-column>
      <el-table-column label="状态" prop="status"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="评论数量" prop="commentCount"></el-table-column>
      <el-table-column label="分数" prop="score"></el-table-column>
      <el-table-column align="right">
        <template slot="header" slot-scope="scope">
  <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
  <el-input v-model="userIdSearch" size="mini" placeholder="输入用户ID搜索" />
</template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row),dialogVisible = true">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <el-dialog
  title="编辑操作"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
 <span>请点击需要的操作按钮</span>
  <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="dialog = true,dialogVisible = false">加评帖子</el-button>
    <el-button type="primary" @click="dialogVisible = false ,handleTopPost()">置顶帖子</el-button>
    <el-button type="primary" @click="dialogVisible = false,handleessentialPost()">加精帖子</el-button>
  </span>
</el-dialog>

<el-dialog title="加评帖子" :visible.sync="dialog" width="30%" :before-close="handleClose">
            <el-input v-model="addparams.content" type="textarea" :rows="2" placeholder="请输入评论内容..."></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialog = false ">取消</el-button>
                <el-button type="primary" @click="dialog = false, addComment()">确定</el-button>
            </span>
        </el-dialog>

    <el-pagination
      :page-size="10"
      :pager-count="10"
      layout="prev, pager, next"
      :total="total"
      style="margin-top: 20px;"
      @current-change="handlePageChange"
    ></el-pagination>
  </div>
</template>

  
  <script>
  import {add} from '../../../api/comment'
  import {getPostList,deletePost,topPost,essentialPost} from '../../../api/post'
    export default {
      data() {
        return {
          tableData: [],
          search: '',
          dialog: false,
          postParams:{
            pageNum:1,
            pageSize:10,
            userId:0,
            orderMode:0
          },
          addparams: {
            userId: 0,
            entityType: 0,
            entityId: 0,
            targetId: 0,
            content: '',
          },
          total:0,
          dialogVisible: false,
          editParamId:0
        }
      },
      created(){
        this.fetchPostList();
      },
      computed: {
  filteredData() {
    const userIdNumber = Number(this.userIdSearch);
    return this.tableData.filter(data => {
      const matchesTitle = !this.search || data.title.toLowerCase().includes(this.search.toLowerCase());
      const matchesUserId = !this.userIdSearch || data.userId === userIdNumber;
      return matchesTitle && matchesUserId;
    });
  }
},
      methods: {
        formatDate(dateString) {
          const date = new Date(dateString);
          return date.toLocaleString(); // 转换为本地时间格式
        },
        handlePageChange(newPage) {
          this.postParams.pageNum = newPage;
          this.fetchPostList()
        },
        fetchPostList() {
          getPostList(this.postParams).then(response => {
            this.tableData = response.data.list.map(item => {
              return {
                ...item,
                createTime: this.formatDate(item.createTime) // 格式化创建时间
              };
            });
            this.total = response.data.postCount;
          });
        },
        handleTopPost(){
          topPost(this.editParamId).then(response => {
            this.$message({
              message: response.data,
              type: 'success'
            });
            this.fetchPostList();
          });
        },
        handleessentialPost(){
          essentialPost(this.editParamId).then(response => {
            this.$message({
              message: response.data,
              type: 'success'
            });
            this.fetchPostList();
          });
        },

        handleEdit(index, row) {
          this.editParamId=row.id
      this.addparams.userId=4 //暂时以4作为管理员
      this.addparams.entityType=1
      this.addparams.entityId=row.id
      this.addparams.targetId=row.userId
        },
        handleDelete(index, row) {
          deletePost(row.id).then(response => {
            this.$message({
              message: response.data,
              type: 'success'
            });
            this.fetchPostList();
          });
        },
        addComment(){
      add(this.addparams).then(response => {
        this.$message({
          message: response.data,
          type: 'success'
        });
        this.fetchlist();
      })
    }
      }
    }

  </script>