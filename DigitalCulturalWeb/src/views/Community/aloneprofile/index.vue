<template>
     <div class="com-aloneprofile">
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
            <el-button type="danger" @click=" fetchfollowUser() ">{{followStatusconcent}}</el-button>
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
                        <span>帖子：{{  aloneprofilpostCount}}</span>
                        <i class="el-icon-location-information"><span>{{aloneuserInfos.city}}</span></i>
                        <div class="bio">
                            {{ aloneuserInfos.bio }}
                        </div>
                    </div>

                </div>
            </el-card>
        </div>
        <div class="alnoe-main-aloneprofil">
            <el-card class="com-box-card2-aloneprofil">
                <span v-if=" aloneprofilpostCount> 0">发布的全部帖子（{{  aloneprofilpostCount}}）</span>
                <span v-else>发布的全部帖子（0）</span>
                <el-divider></el-divider>
                <!-- <div v-for="aloneprofil in  aloneprofilposts" :key="aloneprofil.post.id" class="com-post-aloneprofil">
                    <div class="com-title-aloneprofil">
                        <router-link :to="{ path: '/postDetail', query: { postId: aloneprofil.post.id } }">
                            <span class="com-title-text-aloneprofil">{{ aloneprofil.post.title }}</span>
                        </router-link>
                    </div>
                    <div class="com-sing-aloneprofil">
                        <span>创作于 {{ formatDate(aloneprofil.post.createTime) }}</span>
                        <span>获赞 {{ aloneprofil.likeCount }}</span>
                        <span>回帖 {{ aloneprofil.post.commentCount }}</span>
                    </div>
                    <div class="com-content-aloneprofil">
                        <router-link :to="{ path: '/postDetail', query: { postId: aloneprofil.post.id } }">
                            <span>{{ truncateContent(aloneprofil.post.content) }}</span>
                        </router-link>
                    </div>
                    <el-divider></el-divider>
                </div> -->

                <div class="com-page-aloneprofil">
                    <div v-if="aloneprofilpostCount > 0" class="com-pagination-aloneprofil">
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
            aloneprofilpostCount:0,
            aloneprofilfollowerCount:0,
            aloneprofilfolloweeCount:0,
            aloneprofiluserLikeCount:0,
            followListParams:{
            offset:0,
            limit:1000
        },
        aloneprofilpostParams: {
            pageNum: 1,
			pageSize: 5,
			userId:0,
			orderMode:0
            },
            aloneprofilposts:[],
            aloneprofiltotal:0
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
    formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString(); 
  },

    fetchshowPostList(){
        this.aloneprofilpostParams.userId=this.id;
       showPostList(this.aloneprofilpostParams).then(response => {
      this.aloneprofilposts = response.data.list;
      this.aloneprofilpostCount=response.data.page.Total;
      this.aloneprofiltotal=response.data.page.Pages;
      console.log("帖子列表",this.aloneprofilpostCount);
    }).catch(error => {
      console.error('获取帖子列表失败:', error);
    });
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