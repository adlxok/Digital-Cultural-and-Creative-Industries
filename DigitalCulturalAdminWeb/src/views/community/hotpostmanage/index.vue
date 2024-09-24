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
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row),dialogVisible = true">推流</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>  
    
    <el-dialog
  title="推流操作"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
 <span>修改帖子分数</span>
 <el-input v-model="input" placeholder="请输入分数..." size="small"></el-input>
  <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="dialogVisible = false">取消</el-button>
    <el-button type="primary" @click="dialogVisible = false,handleupdateScore()">确定</el-button>
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
  import {getPostList,deletePost,updateScore} from '../../../api/post'
    export default {
      data() {
        return {
          tableData: [],
          search: '',
          userIdSearch: '',  
          hotpostParams:{
            pageNum:1,
            pageSize:10,
            userId:0,
            orderMode:1
          },
          total:0,
          scoreParams:{
            id:0,
            score:0
          },
          dialogVisible: false,
          input:'',
          editParamId:0
        }
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
}

,

      created(){
        this.PostList();
      },
      methods: {
        handleupdateScore(){
          this.scoreParams.id=this.editParamId
          this.scoreParams.score=this.input
          updateScore(this.scoreParams).then(response => {  
            this.$message({
              message: response.data,
              type: 'success'
            });
            this.PostList();
          });
        },
        formatDate(dateString) {
          const date = new Date(dateString);
          return date.toLocaleString(); // 转换为本地时间格式
        },
        handlePageChange(newPage) {
          this.postParams.pageNum = newPage;
          this.PostList()
        },
        PostList() {
          getPostList(this.hotpostParams).then(response => {
            this.tableData = response.data.list.map(item => {
              return {
                ...item,
                createTime: this.formatDate(item.createTime) // 格式化创建时间
              };
            });
            this.total = response.data.postCount;
          })
        },
        handleEdit(index, row) {
         this.editParamId=row.id
        },
        handleDelete(index, row) {
          deletePost(row.id).then(response => {
            this.$message({
              message: response.data,
              type: 'success'
            });
            this.PostList();
          });
        }
      }
    }

  </script>