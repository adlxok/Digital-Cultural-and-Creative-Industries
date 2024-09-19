<template>
    <div class="com-center">
        <div class="image-back">
            <el-card class="com-box-card0">
            </el-card>
        </div>
        <!-- 个人信息导航 -->
        <div class="com-nav">
            <el-card class="com-box-card1">
                <div class="com-info-item">
                    <div class="com-profile">
                        <el-avatar :size="80" :src="userInfos.profileImageUrl" alt="用户头像"></el-avatar>
                    </div>
                    <div class="com-name">
                        <span>{{ userInfos.username}}</span>
                    </div>
                    <!-- <div class="button">
            <el-button type="danger">关注</el-button>
            <el-button type="primary">私信</el-button>
           </div> -->
                    <div class="com-like">
                        <span>获赞：{{ userLikeCount }}</span>
                        <el-divider direction="vertical"></el-divider>
                        <span>关注：{{ followeeCount }}</span>
                        <el-divider direction="vertical"></el-divider>
                        <span v-if="followerCount>0">粉丝：{{ followerCount }}</span>
                        <span v-else>粉丝：{{0}}</span>
                        <el-divider direction="vertical"></el-divider>
                        <span>帖子：{{ postCount}}</span>
                        <i class="el-icon-location-information"><span>{{ userInfos.city }}</span></i>
                        <div class="bio">
                            {{ userInfos.bio }}
                        </div>
                    </div>

                </div>
            </el-card>
        </div>
        <!-- 个人信息左部内容 -->
        <div class="com-right">
            <el-card class="com-box-card2">
                <ul class="com-list">
                    <li class="com-item" @click="ifclick(1)">
                        <i class="el-icon-user"></i>
                        <span :class="{ active: activeCategory === '1' }">我的主页</span>
                    </li>
                    <li class="com-item" @click="ifclick(2)">
                        <i class="el-icon-user-solid"></i>
                        <span :class="{ active: activeCategory === '2' }">我的关注</span>
                    </li>
                    <li class="com-item" @click="ifclick(3)">
                        <i class="el-icon-s-custom"></i>
                        <span :class="{ active: activeCategory === '3' }">我的粉丝</span>
                    </li>
                    <li class="com-item" @click="ifclick(4)">
                        <i class="el-icon-document"></i>
                        <span :class="{ active: activeCategory === '4' }">我的帖子</span>
                    </li>
                    <li class="com-item" @click="ifclick(5)">
                        <i class="el-icon-thumb"></i>
                        <span :class="{ active: activeCategory === '5' }">我的点赞</span>
                    </li>
                    <li class="com-item" @click="ifclick(6)">
                        <i class="el-icon-star-off"></i>
                        <span :class="{ active: activeCategory === '6' }">我的收藏</span>
                    </li>
                </ul>
            </el-card>
        </div>
        <!-- 个人信息中部内容 -->
        <div class="homepage" v-if="activeTab==1">
            <el-card class="com-box-card3">
                <div class="com-name">
                    <span>{{ userInfos.username}}</span>
                </div>
                <div class="com-info">
                    <span v-if="userInfos.gender==1">性别:男</span>
                    <span v-if="userInfos.gender==0">性别:女</span>
                    <span>注册于:{{ userInfos.createTime }}</span>
                </div>
                <div class="com-profile">
                    <el-avatar :size="80" :src="userInfos.profileImageUrl" alt="用户头像"></el-avatar>
                    <div class="com-info1">
                        <ul>
                            <li> <span>邮箱: {{ userInfos.email }}</span></li>
                            <li><span>生日: {{ formatDate(userInfos.birthDate) }}</span></li>
                            <li><span>现居: {{ userInfos.country }}</span></li>
                            <li><span>简介: {{ userInfos.bio }}</span></li>
                        </ul>
                        <router-link to="/profile">
                            <el-button size="small" type="primary" plain>修改个人信息</el-button>
                        </router-link>
                    </div>
                </div>
            </el-card>
        </div>
        <div class="follow" v-if="activeTab==2">
            <el-card class="com-box-card3">
                <span v-if="followeeCount>0">全部关注（{{followeeCount }}）</span>
                <span v-else>全部关注（0）</span>
                <el-divider></el-divider>
                <div v-for="followee in followeeList" :key="followee.user.id">
                    <div class="followee-name">
                        <span>{{ followee.user.username}}</span>
                    </div>
                    <div class="followee-bio">
                        <span>{{ followee.user.bio }}</span>
                    </div>
                    <div class="followee-button" v-if="isfollow==true"><el-button round size="small"
                            @click="openfowDialog(followee.user.id)">{{
                            followStatus[followee.user.id] }}</el-button></div>
                    <div v-else class="followee-button"><el-button round size="small"
                            @click="fetchfollowUser(followee.user.id)">{{
                            followStatus[followee.user.id] }}</el-button></div>
                    <el-dialog :ref="`dialog-${followee.user.id}`" title="提示"
                        :visible.sync="fowdialogVisible[followee.user.id]" width="30%" :before-close="handleClose">
                        <span>确定不在关注此人？</span>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="closefowDialog(followee.user.id)">取 消</el-button>
                            <el-button type="primary" @click="confirmfowDelete(followee.user.id)">确
                                定</el-button>
                        </span>
                    </el-dialog>
                    <div class="followee-profile">
                        <el-avatar :size="60" :src="followee.user.profileImageUrl" alt="用户头像"></el-avatar>
                    </div>
                    <el-divider></el-divider>
                </div>
            </el-card>
        </div>
        <div class="follower" v-if="activeTab==3">
            <el-card class="com-box-card3">
                <span v-if="followerCount>0">全部粉丝（{{followerCount }}）</span>
                <span v-else>全部粉丝（0）</span>
                <el-divider></el-divider>
                <div v-for="follower in followerList" :key="follower.user.id">
                    <div class="followee-name">
                        <span>{{ follower.user.username}}</span>
                    </div>
                    <div class="followee-bio">
                        <span>{{ follower.user.bio }}</span>
                    </div>
                    <div class="followee-profile">
                        <el-avatar :size="60" :src="follower.user.profileImageUrl" alt="用户头像"></el-avatar>
                    </div>
                    <el-divider></el-divider>
                </div>
            </el-card>
        </div>
        <div class="com-post" v-if="activeTab==4">
            <el-card class="com-box-card3">
                <span v-if="postCount > 0">全部帖子（{{ postCount }}）</span>
                <span v-else>全部帖子（0）</span>
                <el-divider></el-divider>
                <div v-for="postmap in posts" :key="postmap.post.id" class="com-post">
                    <div class="com-title">
                        <router-link :to="{ path: '/postDetail', query: { postId: postmap.post.id } }">
                            <span class="com-title-text">{{ postmap.post.title }}</span>
                        </router-link>
                        <el-button size="small" round class="com-button"
                            @click="openDialog(postmap.post.id)">删除</el-button>
                    </div>
                    <el-dialog :ref="`dialog-${postmap.post.id}`" title="提示"
                        :visible.sync="dialogVisible[postmap.post.id]" width="30%" :before-close="handleClose">
                        <span>确定要删除此贴？</span>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="closeDialog(postmap.post.id)">取 消</el-button>
                            <el-button type="primary" @click="confirmDelete(postmap.post.id)">确 定</el-button>
                        </span>
                    </el-dialog>
                    <div class="com-sing">
                        <span>创作于 {{ formatDate(postmap.post.createTime) }}</span>
                        <span>获赞 {{ postmap.likeCount }}</span>
                        <span>回帖 {{ postmap.post.commentCount }}</span>
                    </div>
                    <div class="com-content">
                        <router-link :to="{ path: '/postDetail', query: { postId: postmap.post.id } }">
                            <span>{{ truncateContent(postmap.post.content) }}</span>
                        </router-link>
                    </div>
                    <el-divider></el-divider>
                </div>

                <div class="com-page">
                    <div class="com-pagination">
                        <a href="#mao3"> <button id="firstPage3" @click="handleshouye()"
                                class="com-button-item">首页</button></a>
                        <a href="#mao3"> <button id="prevPage3" @click="handlelast()"
                                class="com-button-item">上一页</button></a>
                        <span id="pageInfo3">第 {{ this.postParams.pageNum }} 页</span>
                        <a href="#mao3"> <button id="nextPage3" @click="handlenext()"
                                class="com-button-item">下一页</button></a>
                        <a href="#mao3"><button id="lastPage3" @click="handleweiye()"
                                class="com-button-item">尾页</button></a>
                    </div>
                </div>
            </el-card>
        </div>
        <div class="com-like" v-if="activeTab==5">
            <el-card class="com-box-card3">
                <span v-if="userLikeCount > 0">全部点赞（{{ userLikeCount }}）</span>
                <span v-else>全部点赞（0）</span>
                <el-divider></el-divider>
            </el-card>
        </div>

        <div class="favorite" v-if="activeTab==6">
            <el-card class="com-box-card3">
                <span v-if="favoriteCount > 0">全部收藏（{{ favoriteCount }}）</span>
                <span v-else>全部收藏（0）</span>
                <el-divider></el-divider>
                <div class="favorite-itme">
                    <div v-for="comfavorite in comfavoriteLists" :key="comfavorite.post.id">
                        <router-link :to="{ path: '/postDetail', query: { postId: comfavorite.post.id } }">
                        <div class="com-title">
                        <router-link :to="{ path: '/postDetail', query: { postId: comfavorite.post.id } }">
                            <span class="com-title-text">{{comfavorite.post.title }}</span>
                        </router-link>
                        <el-button size="small" round class="com-button"
                            @click="staropenDialog(comfavorite.post.id)">{{isFavoriteconcent}}</el-button>
                    </div>
                    <el-dialog :ref="`dialog-${comfavorite.post.id}`" title="提示"
                        :visible.sync="stardialogVisible[comfavorite.post.id]" width="30%" :before-close="handleClose">
                        <span>确定要取消收藏此贴？</span>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="starcloseDialog(comfavorite.post.id)">取 消</el-button>
                            <el-button type="primary" @click="starconfirmDelete(comfavorite.post.id)">确 定</el-button>
                        </span>
                    </el-dialog>
                    <div class="com-sing">
                        <router-link :to="{ path: '/postDetail', query: { postId: comfavorite.post.id } }">
                        <span>创作于 {{ formatDate(comfavorite.post.createTime) }}</span>
                        <span>获赞 {{ comfavorite.post.likeCount }}</span>
                        <span>回帖 {{ comfavorite.post.commentCount }}</span>
                    </router-link>
                    </div>
                    <div class="com-content">
                        <router-link :to="{ path: '/postDetail', query: { postId: comfavorite.post.id } }">
                            <span>{{ truncateContent(comfavorite.post.content) }}</span>
                        </router-link>
                    </div>
                </router-link>
                    <el-divider></el-divider>
                    </div>
                    
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
import '../css/base.css';
import '../css/comprofile.css';
import {getUser } from '../../../api/login';
import {followeeList,followStatus,unfollow,followUser,followerList} from '../../../api/follow';
import {showPostList,deletePost} from '../../../api/post';
import {userLikeCount,favoriteList,isFavorite} from '../../../api/like';
export default {
    data() {
        return {
        Id:0, 
        total:0,
        activeCategory: null,
        stardialogVisible: {},
        fowdialogVisible: {},
        dialogVisible: {},
        isfollow:true,
        userInfos:{},
        activeTab: 1,
        followeeList:[],
        followeeListParams:{
        offset:0,
        limit:1000
        },
        followeeCount:0,
        followStatusParams:{
            entityType:2,
            entityId:0
        },
        followStatus:[],
        followUserParams:{
            entityType:2,
            entityId:0
        },
        unfollowParams:{
            entityType:2,
            entityId:0
        },
        followerListParams:{
            offset:0,
            limit:1000
        },
        followerList:[],
        followerCount:0,
        userId:[],
        postParams: {
            pageNum: 1,
			pageSize: 5,
			userId:0,
			orderMode:0
            },
            posts:[],
            postCount:0,
            userLikeCount:0,
            comfavoriteLists:[],
            favoriteStatus:0,
            favoriteCount:0,
            isFavoriteParams:{
                userId:0,
                entityId:0,
                status:0
            },
            isFavoriteconcent:'已收藏'
        };
    },
    created() {
    this.fetchcomfavoritesList();
    this.Id=this.$route.query.userId;
    this.fetchusers();
    this.fetchfolloweeList();
    this.fetchfollowerList();
    this.fetchshowPostList();
    this.fetchuserLikeCount();
    this.fetchcomfavoriteStatus();
  },
    methods: {
        handleshouye() {
            this.postParams.pageNum = 1;
            this.fetchshowPostList()
            console.log("点击首页");
        },
        handlelast() {
            if (this.postParams.pageNum > 1) {
                this.postParams.pageNum--;
                this.fetchshowPostList()
                console.log("点击上一页");
            }
        },
        handlenext() {
            if (this.postParams.pageNum == this.total) {
                this.$message.info('已经是最后一页了');
            } else if (this.postParams.pageNum <= this.total) {
                this.postParams.pageNum++;
                this.fetchshowPostList()
                console.log("点击下一页");
            }
        },
        handleweiye() {
            if (this.postParams.pageNum == this.total) {
                this.$message.info('已经是最后一页了');
            } else {
                this.postParams.pageNum = this.total;
                this.fetchshowPostList()
                console.log("点击尾页");
            }
        },
     truncateContent(content) {  
      if (!content) return '';  
      return content.length > 100 ? content.slice(0, 100) + '...' : content;  
    },  
     ifclick(param) {  
      this.activeCategory=param;
      this.activeTab = param; 
      console.log('点击了标签页：' + param)
    },  
    formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString(); 
  },

  fetchfolloweeList(){
    followeeList(this.Id,this.followeeListParams).then(response => {
      this.followeeList=response.data.list;
      this.followeeCount=response.data.followeeCount;
       response.data.list.map(followmap => {
        this.followStatusParams.entityId=followmap.user.id;
		followStatus(this.followStatusParams).then(response => {
		this.$set(this.followStatus, followmap.user.id, response.data);
	})
        });
    }).catch(error => {
      console.error('获取关注列表失败:', error);
    });
 
  },
  openfowDialog(followId) {
        // 设置对应帖子的对话框为可见
        this.$set(this.fowdialogVisible, followId, true);
    },
    closefowDialog(followId) {
        // 关闭对应帖子的对话框
        this.$set(this.fowdialogVisible, followId, false);
    },
    confirmfowDelete(followId) {
        // 执行删除操作
        this.fetchunfollow(followId);
        // 关闭对话框
        this.closefowDialog(followId);
    },
  fetchfolloweeStatus(userId){
        this.followStatusParams.entityId=userId;
        followStatus(this.followStatusParams).then(response => {
            return response.data;
        }).catch(error => {
            console.error('获取关注状态失败:', error);
        });
    },
    fetchfollowerList(){
        followerList(this.Id,this.followerListParams).then(response => {
            this.followerCount=response.data.followerCount;
            this.followerList=response.data.list;
        }).catch(error => {
            console.error('获取粉丝列表失败:', error);
        });
    },
    fetchfollowUser(entityId) {
            this.followUserParams.entityId = entityId;
            followUser(this.followUserParams);
            this.followStatus[entityId] = '已关注'
            this.isfollow = true
            this.$message.success('关注成功')
        },
    fetchunfollow(entityId) {
            this.unfollowParams.entityId = entityId;
            unfollow(this.unfollowParams);
            this.followStatus[entityId] = '+关注'
            this.isfollow = false
            this.$message.success('取关成功')
        },
    fetchshowPostList(){
        this.postParams.userId=this.Id;
    showPostList(this.postParams).then(response => {
      this.posts = response.data.list;
      this.postCount=response.data.page.Total;
      this.total=response.data.page.Pages;
    }).catch(error => {
      console.error('获取帖子列表失败:', error);
    });
    },
    openDialog(postId) {
        // 设置对应帖子的对话框为可见
        this.$set(this.dialogVisible, postId, true);
    },
    closeDialog(postId) {
        // 关闭对应帖子的对话框
        this.$set(this.dialogVisible, postId, false);
    },
    confirmDelete(postId) {
        // 执行删除操作
        this.fetchdeletePost(postId);
        // 关闭对话框
        this.closeDialog(postId);
    },
    handleClose(done) {
        done();
    },
    fetchdeletePost(id){
        deletePost(id);   
        this.$message.success('删除成功');
     
    },
    fetchuserLikeCount(){
        userLikeCount(this.Id).then(response => {
            this.userLikeCount=response.data;
        }).catch(error => {
            console.error('获取用户点赞数失败:', error);
        });
    },
    fetchcomfavoritesList(){
	favoriteList().then(response => {
	this.comfavoriteLists=response.data;
    this.favoriteCount=response.data.length;
	}).catch(error => {  
        console.error('获取收藏列表失败:', error);   
    });
  },
  staropenDialog(Id) {
        // 设置对应帖子的对话框为可见
        this.$set(this.stardialogVisible,Id, true);
    },
    starcloseDialog(Id) {
        // 关闭对应帖子的对话框
        this.$set(this.stardialogVisible, Id, false);
    },
    starconfirmDelete(Id) {
        // 执行删除操作
        this.fetchisFavorite(Id);
        // 关闭对话框
        this. starcloseDialog(Id);
    },
  fetchisFavorite(entityId){
    this.isFavoriteParams.userId=this.Id;
    this.isFavoriteParams.entityId=entityId;
    isFavorite(this.isFavoriteParams)
  },
    fetchusers(){ 
                getUser(this.Id).then(response => {
                    this.userInfos = response.data;
                    console.log(this.userInfo)
                }).catch(error => {
                    console.error('获取用户信息失败:', error);
                });
            }
        }
    }
    
</script>
