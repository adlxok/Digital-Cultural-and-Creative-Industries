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
					<!-- <li class="nav1-item" @click="show3 = !show3"><i class="el-icon-star-off" ></i></li> -->

					<div>
						<li class="nav1-item" @click="drawer = true">
							<el-popover placement="bottom-start" title="收藏帖子" width="50" trigger="hover"
								content="查看我的收藏列表">
								<i class="el-icon-star-off" slot="reference"></i>
							</el-popover>

						</li>
						<el-drawer title="我的收藏列表" :visible.sync="drawer" :direction="direction"
							:before-close="handleClose" :close-on-click-overlay="true">
							<div class="favoritelist">
								<div v-for="favoritemap in favoriteList" :key="favoritemap.post.id">
									<div class="favorite-item">
										<div class="favorite-title">
											<i class="el-icon-caret-right"></i>
											<router-link
												:to="{ path: '/postDetail', query: { postId: favoritemap.post.id }}">
												<span>{{ favoritemap.post.title }}</span>
											</router-link>
										</div>
									</div>
								</div>
							</div>
						</el-drawer>
					</div>


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
						<router-link :to="{ path: '/comprofile', query: { userId: userInfo.id }}">
							<el-avatar :src="userInfo.profileImageUrl" :size="55"></el-avatar>
						</router-link>
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
								<router-link :to="{ path: '/postDetail', query: { postId: hotpost.id} }">
									<span class="hot-title-item1"> {{ hotpost.title }}</span>
								</router-link>
							</div>
							<span class="hot-title-item2">热度{{ hotpost.score }}</span>
						</div>
					</div>
				</el-card>
				<el-card class="box1-itme-card" >
  <div class="card-content">
	<span class="block-title">探索更多</span>
	<div class="demo-image">
  <div class="block-item1">
    <el-image
      style="width: 100px; height: 100px"
      :src="url"
      :fit="fits"></el-image>
	<span class="block-item1-span">花鸟虫鱼篇</span>
    <span class="block-item1-span2">齐白石《多子图》张大千《禽鸟》关山月《黑牡丹》谢稚柳《荷花图》吴青霞《连年有余》...</span>
  </div>
  <div class="block-item1">
    <el-image
      style="width: 100px; height: 100px"
      src="https://img.phb01.com/d/file/p/2021/08-13/57-200FQ10A4300.jpg"
      fit="cover"></el-image>
	<span class="block-item1-span">笔墨丹青篇</span>
    <span class="block-item1-span2">王羲之《兰亭序》颜真卿《祭侄文稿》苏轼《黄州寒食帖》欧阳询《仲尼梦奠帖》怀素《自叙帖》...</span>
  </div>
  <div class="block-item1">
    <el-image
      style="width: 100px; height: 100px"
      src="https://img.ixintu.com/download/jpg/202004/8a9cd6cc7d117279c1fa3ecdb6d77b11_800_1422.jpg%21con0"
      fit="cover"></el-image>
	<span class="block-item1-span">医者仁心篇</span>
    <span class="block-item1-span2">阴阳学说 五行学说 脏腑学说 经络学说 中药治疗 针灸疗法 推拿按摩疗法...</span>
  </div>
  <div class="block-item1">
    <el-image
      style="width: 100px; height: 100px"
      src="https://img0.baidu.com/it/u=651072961,585621092&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1726678800&t=0e6d8f8a54d5da1afe20bda56450473d"
      fit="cover"></el-image>
	<span class="block-item1-span">戏韵悠长篇</span>
    <span class="block-item1-span2">京剧 昆曲 豫剧 评剧 越剧 黄梅戏 曲剧 越调 吕剧 沪剧...</span>
  </div>
  <div class="block-item1">
    <el-image
      style="width: 100px; height: 100px"
      src="https://img1.baidu.com/it/u=3616382313,1125059306&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=605"
      fit="cover"></el-image>
	<span class="block-item1-span">锦绣华章篇</span>
    <span class="block-item1-span2">苏绣 湘绣 粤绣 蜀绣 京绣 鲁绣 汴绣 瓯绣 杭绣 汉绣...</span>
  </div>
</div>
</div>
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
									<router-link :to="{ path: '/aloneprofile', query: { userId: postmap.user.id }}">
										<el-avatar v-if="postmap.user && postmap.user.profileImageUrl" :size="55"
											:src="postmap.user.profileImageUrl" alt="用户头像"></el-avatar>
										<el-avatar v-else :size="55"
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
											<img v-if="postmap.url.image.length > 0 && imageUrl" :src="imageUrl"
												alt="帖子图片" />
										</a>
									</div>
									<div class="post-video"
										v-if="postmap.url.video.length!=0&&postmap.url.image.length ==0">
										<video :src="postmap.url.video[0]" controls></video>
									</div>
								</div>
								<div class="small-sign">
									<div class="collectCount">
										<span class="font-itme1">收藏 {{ favorites[postmap.post.id] }}
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
		</div>
	</div>



</template>

<script>  
// 引入组件样式  
import '../css/base.css';  
import '../css/post.css';
import { showPostList,hotPost} from "../../../api/post";
import { favoriteStatus,favoriteList} from "../../../api/like";
import {getInfo}from '../../../api/login';
import store from '@/store'
  
export default {
	name: "CommunityPost",
    data() {
      return {
		fits: 'cover',
        url: 'https://img1.baidu.com/it/u=2289635445,3333255469&fm=253&fmt=auto&app=138&f=JPEG?w=1067&h=800',
		userInfo:{},
        input: '',
		posts: [],
		total: 0,
		users:[],
		listParams: {
			pageNum: 1,
			pageSize: 5,
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
		activeNames: [],
		drawer: false,
        direction: 'rtl',
		favoriteList:[]
      }
      } ,
	created() {
	this.fetchPostList();
    this.fetchhotPost();
	this.fetchfavoriteList();
	this. fetchuser()
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
		this.listParams.orderMode=0;
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
  },
  fetchfavoriteList(){
	favoriteList().then(response => {
	this.favoriteList=response.data;
	}).catch(error => {  
        console.error('获取收藏列表失败:', error);   
    });
  },
  fetchuser(){
	const token = store.state.user.token;
    getInfo(token).then(response => {
	this.userInfo=response.data;
	console.log(this.userInfo)
	}).catch(error => {  
        console.error('获取用户信息失败:', error);   
    });
}
  }
  } 
</script> 