<template>
	<div class="post">
		<!-- 头部 -->
		<div class="community-heander">
			<!-- 导航 -->
			<div class="community-nav">
				<ul class="left-nav">
					<router-link to="/home">
						<li class="nav-item">首页</li>
					</router-link>
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
					<router-link to="/addpost">
						<li class="nav1-item">
							<el-popover placement="bottom-start" title="发布帖子" width="100" trigger="hover"
								content="快开始你的创造吧！">
								<i class="el-icon-edit" slot="reference"></i>
							</el-popover>
						</li>
					</router-link>

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
				<div class="main-right">
					<el-card class="box3-card">
						<div class="block">
							<span class="demonstration">千里之行,始于足下 ——《道德经》</span>
							<el-carousel height="250px">
								<el-carousel-item v-for="(image, index) in showimage" :key="index">
									<img :src="image" alt="轮播图" />
								</el-carousel-item>
							</el-carousel>
						</div>

					</el-card>
				</div>
				<el-card class="box1-card">
					<div class="hot-nav">
						<span>今日热帖</span>
					</div>
					<div v-for="(hotpost,index) in hotPost" :key="hotpost.id" class="hot-list">
						<div class="hot-itme">
							<em>
								{{ index+1 }}
							</em>
							<div class="hot-title">
								<router-link to="/addpost">
									<span>{{ hotpost.title }}</span>
								</router-link>
							</div>
						</div>
					</div>
				</el-card>
				<el-card class="box1-itme-card">
				</el-card>
			</div>
			<!-- 中部-推荐帖子列表 -->
			<div class="recommend-list">
				<div class="content-list">
					<el-card class="box2-card">
						<div class="content-header">
							<a id="mao"></a>
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
							<div v-for="(postmap) in posts" :key="postmap.post.id" class="post-item">
								<div class="profile-photo-main">
									<router-link to="/addpost">
										<el-avatar v-if="postmap.user && postmap.user.profileImageUrl" :size="50"
										:src="postmap.user.profileImageUrl" alt="用户头像"></el-avatar>
										<el-avatar v-else :size="50"
										src="https://img1.baidu.com/it/u=3647744349,2477516282&fm=253&fmt=auto&app=138&f=JPEG?w=380&h=380"
										alt="默认头像"></el-avatar>
									</router-link>
									
								</div>
								<div v-if="postmap.user " class="username">
									<span>{{ postmap.user.username }}</span>
								</div>
								<div v-else class="username">
									<span>没有用户名的用户</span>
								</div>
								<div class="title">
								<router-link :to="{ path: '/postDetail', query: { postId: postmap.post.id }}"> 
								<span>{{ postmap.post.title }}</span>	
								</router-link>
								</div>
								<div class="createTime">
									<span>{{ postmap.post.formattedCreateTime}}</span>
								</div>
								<div class="content">
									<div v-html="truncateContent(postmap.post.content)"></div>
								</div>
								<div class="url">
									<div v-for="(imageUrl, index) in postmap.url.image" :key="index" class="post-image">
										<a :href="imageUrl" target="_blank">
										<img v-if="postmap.url.image.length > 0 && imageUrl" :src="imageUrl" alt="帖子图片"/>
										</a>
									</div>
									<div class="post-video"
										v-if="postmap.url.video.length!=0&&postmap.url.image.length ==0">
										<video :src="postmap.url.video[0]" controls></video>
									</div>
								</div>
								<div class="small-sign">
									<div class="collectCount">
										<span class="font-itme1" >收藏 {{ favorites[postmap.post.id] }}
											</span>
									</div>
									<div class="likeCount">
										<span class="font-itme1">赞 {{ postmap.likeCount }}</span>
									</div>
									<div class="commentCount">
										<span class="font-itme1">回帖 {{ postmap.post.commentCount }}</span>
									</div>
								</div>
								<el-divider></el-divider>
							</div>
						</div>

						<div class="page">
							<div class="pagination">
								<a href="#mao"> <button id="firstPage" @click="handleshouye()">首页</button></a>
								<a href="#mao"> <button id="prevPage" @click="handlelast()">上一页</button></a>
								<span id="pageInfo">第 {{ this.listParams.pageNum }} 页</span>
								<a href="#mao"> <button id="nextPage" @click="handlenext()">下一页</button></a>
								<a href="#mao"><button id="lastPage" @click="handleweiye()">尾页</button></a>
							</div>
						</div>
					</el-card>
				</div>
			</div>
			<!-- 右侧 -->
			<!-- <div class="main-right">
				<el-card class="box3-card">
					<div class="block">
						<span class="demonstration">千里之行,始于足下 ——《道德经》</span>
						<el-carousel height="250px">
							<el-carousel-item  v-for="(image, index) in showimage" :key="index">
								<img :src="image" alt="轮播图" />
							</el-carousel-item>
						</el-carousel>
					</div>

				</el-card>
			</div> -->
		</div>
	</div>



</template>

<script>  
// 引入组件样式  
import '../css/base.css';  
import '../css/post.css';
import { showPostList,hotPost} from "../../../api/post";
import { favoriteStatus} from "../../../api/like";
  
export default {
	name: "CommunityPost",
    data() {
      return {
        input: '',
		posts: [],
		total: 0,
		users:[],
		listParams: {
			pageNum: 1,
			pageSize: 20,
			userId:0,
			orderMode:0
		},
		imageList:[],
		video:'',
		value: true,
		activeCategory: null,
        hotPost:[],
		showimage:["https://img1.baidu.com/it/u=3427678510,362959101&fm=253&fmt=auto&app=138&f=JPEG?w=517&h=500",
		'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage109.360doc.com%2FDownloadImg%2F2023%2F03%2F2417%2F263098200_44_20230324052120600.jpg&refer=http%3A%2F%2Fimage109.360doc.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1727953954&t=41aeff736495085e4609a457d80a2481',
		'https://picnew5.photophoto.cn/20110222/guohua-mudantupian-12054681_1.jpg'
		],
		favorites: {},// 用于存储每个帖子的收藏数量
      }
      } ,
	created() {
	this.fetchPostList();
    this.fetchhotPost();
  },
   methods: {   
	truncateContent(content) {  
      if (!content) return '';  
      return content.length > 100 ? content.slice(0, 100) + '...' : content;  
    },  
  
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
		favoriteStatus(postmap.post.id).then(response => {
		this.$set(this.favorites, postmap.post.id, response.data.count);
	})
        return postmap;
        });
        this.total = response.data.page.Pages;
		
    }).catch(error => {  
        console.error('获取帖子列表失败:', error);   
    });
  },
  fetchhotPost(){
	hotPost().then(response => {
	this.hotPost=response.data;
	}).catch(error => {  
        console.error('获取帖子列表失败:', error);   
    });
  }
  }
  } 
</script> 