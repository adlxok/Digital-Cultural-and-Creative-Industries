<template>
	<div>
		<!-- 头部 -->
		<div class="community-heander">
			<!-- 导航 -->
			<div class="community-nav">
				<ul class="left-nav">
					<li class="nav-item">首页</li>
					<li class="nav-item">书法</li>
					<li class="nav-item">国画</li>
					<li class="nav-item">中医</li>
					<li class="nav-item">刺绣</li>
					<li class="nav-item">汉服</li>
					<li class="nav-item">戏剧</li>
				</ul>

				<div class="center-search">
					<el-input placeholder="请输入内容" v-model="input" clearable suffix-icon="el-icon-search" value="搜索内容">
					</el-input>
				</div>

				<ul class="right-nav">
					<a href='../../Home/index.vue'>
						<li class="nav1-item"><i class="el-icon-chat-dot-square"></i></li>
					</a>
					<li class="nav1-item"><i class="el-icon-star-off "></i></li>
					<li class="nav1-item"><i class="el-icon-time"></i></li>
					<li class="nav1-item"><i class="el-icon-edit"></i></li>
					<li class="nav1-item"><i class="el-icon-setting"></i></li>
				</ul>

				<div class="profile-photo">
					<div>
						<el-avatar
							src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
					</div>
				</div>
			</div>
		</div>

		<!-- 主体 -->
		<div class="main">
			<!-- 左侧 -->
			<div class="main-left">
				<el-card class="box1-card">

				</el-card>
			</div>
			<!-- 中部-推荐帖子列表 -->
			<div class="recommend-list">
				<div class="content-list">
					<el-card class="box2-card">
						<div class="content-header">
							<span class="sign" @click="hotPostList">热门</span>

							<el-divider direction="vertical"></el-divider>
							<span class="sign">最新</span>
							<el-divider direction="vertical"></el-divider>
							<span class="sign">精华</span>
						</div>
						<el-divider></el-divider>
						<!-- <el-empty description="暂无数据"></el-empty> -->

						<div class="post-list">
							<!-- <div v-for="post in posts" :key="post.id" class="post-item">
								<div class="profile-photo">
									<el-avatar src={{ this.imageurl }}></el-avatar>
								</div>
								<div class="username">
									<span>{{ this.username }}</span>
								</div>
								<div class="Type">{{ post.Type }}</div>
								<div class="createTime"></div>
								<div class="likeCount">{{  }}</div>
								<div class="commentCount">{{ post.commentCount }}</div>
							</div> -->
							<el-table :data="posts" style="width: 100%">
									<el-table-column prop="title" label="标题" width="180">
									</el-table-column>
									<el-table-column prop="content" label="内容" width="180">
									</el-table-column>
									<el-table-column prop="createTime" label="事件">
									</el-table-column>
								</el-table>
							
							<el-divider></el-divider>
						</div>

						<div class="block">
							<el-pagination layout="prev, pager, next" :total="50">
							</el-pagination>
						</div>
					</el-card>
				</div>
			</div>
			<!-- 右侧 -->
			<div class="main-right">
				<el-card class="box3-card">

				</el-card>
			</div>
		</div>
	</div>



</template>

<script>  
// 引入组件样式  
import '../css/base.css';  
import '../css/post.css';
import { showPostList} from "../../../api/post";
  
export default {
    data() {
      return {
        input: '',
		posts: [],
		total: 0,
		imageurl: '',
		username:'',
		userId:0,
		listParams: {
			pageNum: 1,
			pageSize: 10,
			userId:0,
			orderMode:0
		},
      }
      }
    ,
	created() {
    this.hotPostList();
	this.fetchPostList();

  },
   methods: {   
    hotPostList() {  
      console.log('文字被点击了！');
    }, 
	fetchPostList () {
    showPostList(this.listParams).then(response => {
         this.posts = response.data.list;
         this.total=response.data.total;
    }).catch(error => {  
        console.error('获取帖子列表失败:', error);  
        // 处理错误，比如显示错误消息给用户  
      });  
  }

  } 

  } 
</script>  
