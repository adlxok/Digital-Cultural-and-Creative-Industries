<template>
     <div class="aloneprofile">
        <div class="image-back-aloneprofile">
            <el-card class="com-box-card0-aloneprofile">
            </el-card>
        </div>
        <!-- 个人信息导航 -->
        <div class="com-nav-aloneprofile">
            <el-card class="com-box-card1-aloneprofile">
                <div class="com-info-item-aloneprofile">
                    <div class="com-profile-aloneprofile">
                        <el-avatar :size="80" :src="aloneuserInfos.profileImageUrl" alt="用户头像"></el-avatar>
                    </div>
                    <div class="com-name-aloneprofile">
                        <span>{{ aloneuserInfos.username}}</span>
                    </div>
                    <div class="button-aloneprofile" v-if=" isfollow==false">
            <el-button  type="danger" @click=" fetchfollowUser() ">{{followStatusconcent}}</el-button>
            <el-button type="primary">私信</el-button>
           </div>
           <div class="button-aloneprofile" v-if=" isfollow==true">
            <el-button type="danger" @click=" fetchisfollowUser() ">{{followStatusconcent}}</el-button>
            <el-button type="primary">私信</el-button>
           </div>
                    <div class="com-like-aloneprofile">
                        <span>获赞：{{aloneprofiluserLikeCount}}</span>
                        <el-divider direction="vertical"></el-divider>
                        <span>关注：{{  aloneprofilfolloweeCount }}</span>
                        <el-divider direction="vertical"></el-divider>
                        <span v-if="aloneprofilfollowerCount>0">粉丝：{{aloneprofilfollowerCount}}</span>
                        <span v-else>粉丝：{{0}}</span>
                        <el-divider direction="vertical"></el-divider>
                        <span>帖子：{{ postCount}}</span>
                        <i class="el-icon-location-information"><span>{{aloneuserInfos.city}}</span></i>
                        <div class="bio">
                            {{ aloneuserInfos.bio }}
                        </div>
                    </div>

                </div>
            </el-card>
        </div>
        <div class="alnoe-main-aloneprofil">
            <el-card class="com-box-card2-aloneprofile">
                <span v-if="postCount > 0">发布的全部帖子（{{ postCount }}）</span>
                <span v-else>发布的全部帖子（0）</span>
                <div v-for="postmap in posts" :key="postmap.post.id" class="com-post-aloneprofile">
                    <router-link :to="{ path: '/postDetail', query: { postId: postmap.post.id } }">
                    <div class="com-title-aloneprofile">
                            <span class="com-title-text-aloneprofile">{{ postmap.post.title }}</span>
                       
                    </div>
                   
                    <div class="com-sing-aloneprofile">
                        <span>创作于 {{ formatDate(postmap.post.createTime) }}</span>
                        <span>获赞 {{ postmap.likeCount }}</span>
                        <span>回帖 {{ postmap.post.commentCount }}</span>
                    </div>
                    <div class="com-content-aloneprofile">
                        <router-link :to="{ path: '/postDetail', query: { postId: postmap.post.id } }">
                            <span>{{ truncateContent(postmap.post.content) }}</span>
                        </router-link>
                    </div>
                </router-link>
                    <el-divider></el-divider>
                </div>
                <div class="com-page-aloneprofil">
                    <div  class="com-pagination-aloneprofil">
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
        </div>
</template>

<script>
import '../css/base.css';
import '../css/aloneprofile.css';
import {getUser } from '../../../api/login';
import {userLikeCount} from '../../../api/like';
import { showPostList} from "../../../api/post";
import {followeeList,followStatus,unfollow,followUser,followerList} from '../../../api/follow';
export default {
    data() {
        return {
            id:0,
            aloneuserInfos :{},
            isfollow:null,
            followStatusParams:{
                entityType:2,
                entityId:0
            },
            followParams:{
                entityType:2,
                entityId:0
            },
            followStatusconcent:'',
            postCount:0,
            aloneprofilfollowerCount:0,
            aloneprofilfolloweeCount:0,
            aloneprofiluserLikeCount:0,
            followListParams:{
            offset:0,
            limit:1000
        },
        postParams: {
            pageNum: 1,
			pageSize: 5,
			userId:0,
			orderMode:0
            },
        posts:[],
            total:0,
            isButtonDisabled:false
        }
    }
    ,
    created() {
      this.id=this.$route.query.userId;
      this.fetchaloneusers();
      this.fetchfolloweeList();
      this.fetchfollowerList();  
      this.fetchshowPostList()
      this.fetchfollowStatus();
     this.fetchuserLikeCount();    


    },
    methods: {
    fetchshowPostList(){
        this.postParams.userId=this.id;
       showPostList(this.postParams).then(response => {
      this.posts = response.data.list;
      this.postCount=response.data.page.Total;
      this.total=response.data.page.Pages;
      console.log("帖子列表",this.postCount);
    }).catch(error => {
      console.error('获取帖子列表失败:', error);
    });
    },
    truncateContent(content) {  
      if (!content) return '';  
      return content.length > 100 ? content.slice(0, 100) + '...' : content;  
    },  
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
    formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString(); 
  },


    fetchfolloweeList(){
    followeeList(this.id,this.followListParams).then(response => {
      this. aloneprofilfolloweeCount=response.data.followeeCount;
    }).catch(error => {
      console.error('获取关注数量失败:', error);
    });
 
  },
        fetchfollowerList(){
        followerList(this.id,this. followListParams).then(response => {
            this. aloneprofilfollowerCount=response.data.followerCount;
        }).catch(error => {
            console.error('获取粉丝数量失败:', error);
        });
    },
    fetchuserLikeCount(){
        userLikeCount(this.id).then(response => {
            this. aloneprofiluserLikeCount=response.data;
        }).catch(error => {
            console.error('获取用户点赞数失败:', error);
        });
    },
        fetchfollowStatus(){
            this.followStatusParams.entityId=this.id;
            followStatus(this.followStatusParams).then(res=> {
                this.followStatusconcent=res.data;
                if(res.data=='关注'){
                    this.isfollow=false
                }else{
                    this.isfollow=true
                }
})
        },
        fetchfollowUser() {   
            this.followParams.entityId = this.id;
            followUser(this.followParams);
            this.followStatusconcent = '已关注'
            this.$message.success('关注成功')
        },
        fetchisfollowUser(){
            let params = {
                entityType:2,
                entityId:this.id
            }
            unfollow(params);
            this.followStatusconcent = '关注'
            this.$message.success('取关成功')
        },
        fetchaloneusers(){ 
                getUser(this.id).then(response => {
                    this.aloneuserInfos = response.data;
                    console.log(this.aloneuserInfos)
                }).catch(error => {
                    console.error('获取用户信息失败:', error);
                });
            }
        }

}
</script>