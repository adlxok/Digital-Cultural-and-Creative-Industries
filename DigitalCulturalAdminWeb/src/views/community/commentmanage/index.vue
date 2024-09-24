<template>
  <div>
    <div class="input">
      <el-input v-model="input1" placeholder="请输入评论类型 [0->评论 1->帖子]"></el-input>
      <el-input v-model="input2" placeholder="请输入评论目标ID"></el-input>
      <el-button type="primary" @click="fetchlist()">确定</el-button>
    </div>
    <el-table :data="filteredData" style="width: 100%">
      <el-table-column label="ID" prop="id"></el-table-column>
      <el-table-column label="用户ID" prop="userId"></el-table-column>
      <el-table-column label="评论类型" prop="entityType"></el-table-column>
      <el-table-column label="实体ID" prop="entityId"></el-table-column>
      <el-table-column label="评论用户ID" prop="targetId"></el-table-column>
      <el-table-column label="内容" prop="content"></el-table-column>
      <el-table-column label="状态" prop="status"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column align="right">
        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
          <el-input v-model="userIdSearch" size="mini" placeholder="输入用户ID搜索" />
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row), dialogVisible = true">加评</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑操作" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
            <el-input v-model="addparams.content" type="textarea" :rows="2" placeholder="请输入评论内容..."></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false ">取消</el-button>
                <el-button type="primary" @click="dialogVisible = false, addComment()">确定</el-button>
            </span>
        </el-dialog>

    <el-pagination :page-size="10" :pager-count="10" layout="prev, pager, next" :total="total" style="margin-top: 20px;"
      @current-change="handlePageChange"></el-pagination>
  </div>
</template>

<script>
import { list, add,deletecomment } from '../../../api/comment'
export default {
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      total: 0,
      listparams: {
        pageNum: 1,
        pageSize: 10,
        entityType: 0,
        entityId: 0,

      },
      addparams:{
        userId:0,
        entityType:0,
        entityId:0,
        targetId:0,
        content:'',
      },
      userIdSearch: '',
      search: '',
      input1: '',
      input2: '',
    }
  },
  created() {

  },
  computed: {
    filteredData() {
      const userIdNumber = Number(this.userIdSearch);
      return this.tableData.filter(data => {
        const matchesTitle = !this.search || data.content.toLowerCase().includes(this.search.toLowerCase());
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
    fetchlist() { 
  this.listparams.entityType = this.input1;
  this.listparams.entityId = this.input2;
  list(this.listparams).then(response => {
    if (response.data=="评论列表为空") {
      this.$message({
        message: '评论列表为空',
        type: 'warning'
      });
    } 
    else{
    this.total = response.data.count;
    this.tableData = response.data.list.map(item => {
              return {
                ...item,
                createTime: this.formatDate(item.createTime) // 格式化创建时间
              };
            });
    }
  }).catch(error => {
    console.log(error);
  });
},

    handlePageChange(newPage) {
      this.listparams.pageNum = newPage;
      this.fetchlist()
    },
    handleDelete(index, row) {
      deletecomment(row.id).then(response => {
        this.$message({
          message: response.data,
          type: 'success'
        });
        this.fetchlist();
      });
    },
    handleEdit(index, row) {
      this.addparams.userId=4 //暂时以4作为管理员
      this.addparams.entityType=0
      this.addparams.entityId=row.id
      this.addparams.targetId=row.userId
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
<style>
.input {
  margin-bottom: 20px;
  display: flex;
}
</style>