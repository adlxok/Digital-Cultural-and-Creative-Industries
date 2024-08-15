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
					<a href='../../Home/index.vue'>
						<li class="nav1-item"><i class="el-icon-edit"></i></li>
					</a>
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
							<span class="sign" :class="{ active: activeCategory === 'hot' }"
								@click="hotPostList">热门</span>

							<el-divider direction="vertical"></el-divider>
							<span class="sign" :class="{ active: activeCategory === 'new' }"
								@click="newPostList">最新</span>
							<el-divider direction="vertical"></el-divider>
							<span class="sign" :class="{ active: activeCategory === 'elite' }"
								@click="elitePostList">精华</span>
						</div>
						<el-divider></el-divider>
						<!-- <el-empty description="暂无数据"></el-empty> -->

						<div class="post-list">
							<div v-for="postmap in posts" :key="postmap.post.id" class="post-item">

								<div class="profile-photo-main">
									<el-avatar v-if="postmap.user && postmap.user.profileImageUrl" :size="50"
										:src="postmap.user.profileImageUrl" alt="用户头像"></el-avatar>
									<el-avatar v-else :size="50"
										src="https://img1.baidu.com/it/u=3647744349,2477516282&fm=253&fmt=auto&app=138&f=JPEG?w=380&h=380"
										alt="默认头像"></el-avatar>
								</div>
								<div v-if="postmap.user && postmap.user.profileImageUrl" class="username">
									<span>{{ postmap.user.username }}</span>
								</div>
								<div v-else class="username">
									<span>没有用户名的用户</span>
								</div>
								<div class="title">
									<span>{{ postmap.post.title }}</span>
								</div>
								<div class="createTime">
									<span>发布于{{ postmap.post.formattedCreateTime}}</span>
								</div>
								<div class="collectCount">
									<span class="font-itme1"><i class="el-icon-star-off"></i>{{ postmap.likeCount
										}}</span>
								</div>
								<div class="likeCount">
									<span class="font-itme1">赞{{ postmap.likeCount }}</span>
								</div>
								<div class="commentCount">
									<span class="font-itme1">回帖{{ postmap.post.commentCount }}</span>
								</div>
							</div>
						</div>

						<div class="page">
							<div class="pagination">
								<button id="firstPage" @click="handleshouye()">首页</button>
								<button id="prevPage" @click="handlelast()">上一页</button>
								<span id="pageInfo">第 {{ this.listParams.pageNum }} 页</span>
								<button id="nextPage" @click="handlenext()">下一页</button>
								<button id="lastPage" @click="handleweiye()">尾页</button>
							</div>
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
		users:[],
		listParams: {
			pageNum: 1,
			pageSize: 10,
			userId:0,
			orderMode:0
		},
		value: true,
		activeCategory: null
      }
      }
    ,
	created() {
	this.fetchPostList();

  },
   methods: {   
    hotPostList() {  
		this.activeCategory = 'hot';
		this.listParams.orderMode=1;
		this.listParams.pageNum=1;
		this.fetchPostList();
      console.log('热门被点击了！');
    }, 
	newPostList() {  
		this.activeCategory = 'new';
		this.listParams.orderMode=0;
		this.listParams.pageNum=1;
		this.fetchPostList();
      console.log('最新被点击了！');
    },
	elitePostList(){
		this.activeCategory = 'elite';
		this.listParams.orderMode=2;
		this.listParams.pageNum=1;
		this.fetchPostList();
      console.log('精华被点击了！');
	},
  handleshouye(){
  this.listParams.pageNum=1;
  this.fetchPostList();
  console.log("点击首页");
  },
  handlelast(){
  if(this.listParams.pageNum>1){
  this.listParams.pageNum--;
  this.fetchPostList();
  console.log("点击上一页");
  }
  },
  handlenext(){
	if(this.listParams.pageNum==this.total){
		this.$message.info('已经是最后一页了');
	}else if(this.listParams.pageNum<=this.total)
	{  this.listParams.pageNum++;
  this.fetchPostList();
  console.log("点击下一页");}
  },
handleweiye(){
	if(this.listParams.pageNum==this.total){
		this.$message.info('已经是最后一页了');
	}else{ this.listParams.pageNum=this.total;
  this.fetchPostList();
  console.log("点击尾页");}
  },
	formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString(); // 转换为本地时间格式
  },
  fetchPostList () {
    showPostList(this.listParams).then(response => {
        this.posts = response.data.list.map(postmap => {
            postmap.post.formattedCreateTime = this.formatDate(postmap.post.createTime);
            return postmap;
        });
        this.total = response.data.page.Pages;
    }).catch(error => {  
        console.error('获取帖子列表失败:', error);   
    });  
  }

  }


  } 

</script>  
