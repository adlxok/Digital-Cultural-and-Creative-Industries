<template>
    <div class="comnotice">
        <div class="notice-main">
            <el-card class="notice-card">
                <div class="notice-nav">
                    <div class="notice-name">
                        <i class="el-icon-bell"></i>
                        <span class="notice-name-span">系统通知</span>
                        <router-link to= '/comhome' v-if="isclick==0">
                            <el-button round class="notice-bnt" size="small">返回</el-button>
                        </router-link>
                    </div>
                </div>
                <div class="notice-list" v-if="isclick==0">
                    <div class="notice-list-itme"  @click="isclicks(1)">
                        <div class="notice-list-priflie">
                            <el-badge v-if="likemap.unReadNoticeCount>0" :value=likemap.unReadNoticeCount :max="99"  class="item">
                            <el-avatar src="https://img1.baidu.com/it/u=4034379492,784259864&fm=253&fmt=auto&app=138&f=JPEG?w=256&h=256" :size="55"></el-avatar>
                        </el-badge>
                        <el-avatar v-else src="https://img1.baidu.com/it/u=4034379492,784259864&fm=253&fmt=auto&app=138&f=JPEG?w=256&h=256" :size="55"></el-avatar>
                        </div>
                        <div class="notice-list-name" v-if="likemap.entityType==1">
                            <span>点赞</span>
                            <div class="notice-list-cenent">
                                <span>用户 {{likemap.user.username}} 点赞了你的帖子...</span>
                            </div>
                        </div>
                        <div class="notice-list-name" v-if="likemap.entityType==0">
                            <span>点赞</span>
                            <div class="notice-list-cenent">
                                <span>用户 {{likemap.user.username}} 点赞了你的评论...</span>
                            </div>
                        </div>
                        <div class="notice-list-time">
                            <span>{{ formatDate(likemap.notice.createTime) }}</span>
                        </div>
                    </div>
                    
                    
                    <div class="notice-list-itme" @click="isclicks(2)" >
                        <div class="notice-list-priflie">
                            <el-badge v-if="commentmap.unReadNoticeCount>0" :value=commentmap.unReadNoticeCount :max="99"  class="item">
                            <el-avatar src="https://pic4.zhimg.com/v2-5d6dcc3cd0753d1773ab9d1bc76cbec7_r.jpg" :size="55"></el-avatar>
                        </el-badge>
                        <el-avatar v-else src="https://pic4.zhimg.com/v2-5d6dcc3cd0753d1773ab9d1bc76cbec7_r.jpg" :size="55"></el-avatar>
                        </div>
                        <div class="notice-list-name" v-if="likemap.entityType==1">
                            <span>评论</span>
                            <div class="notice-list-cenent">
                                <span>用户 {{commentmap.user.username}} 评论了你的帖子...</span>
                            </div>
                        </div>
                        <div class="notice-list-name" v-if="likemap.entityType==0">
                            <span>评论</span>
                            <div class="notice-list-cenent">
                                <span>用户 {{likemap.user.username}} 评论了你的评论...</span>
                            </div>
                        </div>
                        <div class="notice-list-time">
                            <span>{{ formatDate(commentmap.notice.createTime) }}</span>
                        </div>
                    </div>
                    <div class="notice-list-itme" @click="isclicks(3)">
                        <div class="notice-list-priflie">
                            <el-badge v-if="followmap.unReadNoticeCount>0" :value=followmap.unReadNoticeCount :max="99"  class="item">
                            <el-avatar src="https://t9.baidu.com/it/u=4057500076,3057276209&fm=193" :size="55"></el-avatar>
                        </el-badge>
                        <el-avatar v-else src="https://t9.baidu.com/it/u=4057500076,3057276209&fm=193" :size="55"></el-avatar>
                        </div>
                        <div class="notice-list-name" v-if="followmap.entityType=2">
                            <span>关注</span>
                            <div class="notice-list-cenent">
                                <span>用户 {{followmap.user.username}} 关注了你...</span>
                            </div>
                        </div>
                        <div class="notice-list-time">
                            <span>{{ formatDate(followmap.notice.createTime) }}</span>
                        </div>
                    </div>
                </div>
                <div class="notice-list" v-if="isclick!=0 ">
                    <el-card class="notice-list-card">
                        <span class="notice-detatil-name">{{ conversationIds }}({{ noticeDetail.length }})</span>
                            <el-button round type="primary" class="notice-detatil-bnt" size="mini" @click="back()">返回</el-button>
                    </el-card>
                    <div class="notice-detatil-list" v-for="(item,index) in noticeDetail" :key="index">
                        <div class="notice-detail-itme">
                            
                            <div class="notice-detail-priflie">
                                <router-link :to="{ path: '/comprofile', query: { userId: item.user.id } }">
                                    <el-avatar :src="item.user.profileImageUrl" :size="60"></el-avatar>
                                </router-link>
                            </div>
                            <div class="notice-detail-name1">
                                
                                    <span>{{ item.user.username }}</span>
                                    <span>  {{ conversationIds }}了你</span>
                                    
                            </div> <div class="notice-detail-time">
                                    <span>{{ formatDate(item.notice.createTime) }}</span>
                                </div>
                        </div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>
<script>
import '../css/notice.css'
import '../css/base.css'
import { noticeList, noticeDetail } from '../../../api/massage'
import { getPostById } from "../../../api/post";

export default {
    name: "communityNotice",
    data() {
        return {
            isclick: 0,
            likemap: {},
            commentmap: {},
            followmap: {},
            noticeDetail: [],
            conversationId: '',
            conversationIds: '',
            posts: [], // 存储帖子列表
        }
    },
    created() {
        this.fetchnoticeList();
    },
    watch: {
    // 监控用户状态的变化
    user(newVal) {
        if (newVal) {
            this.fetchnoticeList();
        }
    }
}
,
    methods: {
        back(){
            this.isclick=0;
            this. fetchnoticeList() ;
        },
        fetchpostbyId(id) {
            return getPostById(id).then(response => {
                return response.data; // 返回帖子数据
            });
        },
        isclicks(num) {
            if (this.isclick === num) return; // 防止重复请求
            this.isclick = num;
            switch(num) {
                case 1:
                    this.conversationId = "like";
                    this.conversationIds = "点赞";
                    break;
                case 2:
                    this.conversationId = "comment";
                    this.conversationIds = "评论";
                    break;
                case 3:
                    this.conversationId = "follow";
                    this.conversationIds = "关注";
                    break;
            }
            this.fetchnoticeDetail();
        },
        fetchnoticeList() {
            noticeList().then(response => {
                this.likemap = response.data.likeMap;
                this.commentmap = response.data.commentMap;
                this.followmap = response.data.followMap;
                console.log(this.noticeList);
            });
        },
        formatDate(dateString) {
            const date = new Date(dateString);
            return date.toLocaleString(); // 转换为本地时间格式
        },
        fetchnoticeDetail() {
            noticeDetail(this.conversationId).then(response => {
                this.noticeDetail = response.data;
                this.total = this.noticeDetail.length;
                console.log(response);

            });
        }
    }
}
</script>

