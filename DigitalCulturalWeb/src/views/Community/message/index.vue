<template>
    <div class="message">
        <!-- 侧边栏 -->
        <div class="right">
            <el-card class="message-card">
                <div slot="header" class="clearfix">
                    <div class="header-priflie">
                        <router-link :to="{ path: '/comprofile', query: { userId: userInfo.id } }">
                            <el-avatar :src="userInfo.profileImageUrl" :size="60"></el-avatar>
                        </router-link>
                    </div>
                    <div class="header-title">
                        <span>{{ userInfo.username }}</span>
                    </div>
                    <div class="header-btn">
                       <router-link to=/comhome >
                        <el-button round size="small">返回</el-button>
                        </router-link>
                    </div>
                </div>
                <div class="messageList" v-for="(item,index) in letterList" :key="item.letter.id">
                    <div class="list-itme" @click="fetchletterDetail(item.letter.fromId,item.letter.toId,index)">
                        <div class="list-priflie">
                            <router-link :to="{ path: '/aloneprofile', query: { userId:user[index] } }">
                                <el-avatar :src="user[index].profileImageUrl" :size="55"></el-avatar>
                            </router-link>
                        </div>
                        <div class="list-title">
                            <span>{{ user[index].username }}</span>
                            <span class="list-time">{{formatDate(item.letter.createTime) }}</span>
                        </div>
                        <div class="list-content">
                            <span>{{ truncateContent(item.letter.content )}}</span>
                        </div>
                        <el-badge v-if="item.unReadLetterCount>0" :value=item.unReadLetterCount :max="99"
                            class="list-badge"></el-badge>
                    </div>
                </div>

            </el-card>
            <div class="message-main">
                <el-crad class="message-main-itme">
                    <div class="isclick" v-if="letterDetail.length<=0 ">
                        <span>您还未选中会话...快开启聊天模式吧</span>
                    </div>
                    <div class="click" v-if="letterDetail.length>0 ">
                        <div class="message-detail-nav">
                        <el-card class="message-detail-card">
                            <span>{{ nowuser.username }}</span>
                        </el-card>
                    </div>
                    <div class="message-detail">
                        <div class="message-detail-list" v-for="item in letterDetail" :key="item.letter.id">
                            <div class="detail-priflie">
                                <router-link :to="{ path: '/aloneprofile', query: { userId:item.fromUser.id } }">
                                    <el-avatar :src="item.fromUser.profileImageUrl" :size="50"></el-avatar>
                                </router-link>
                            </div>
                            <div class="detail-name">
                                <span>{{ item.fromUser.username }}</span>
                                <div class="detail-content">
                                    <span class="detail-content-span">{{ item.letter.content }}</span>
                                </div>
                            </div>
                        </div>
                         <div class="tishi"> <span>没有更多信息了....</span></div>
                        <div class="editor">
                            <el-card class="editor-card">
                                <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" placeholder="请输入内容"
                                    v-model="textarea2[index]">
                                </el-input>
                                <el-button type="primary"  class="send-btn" @click="fetchaddLetter()">发送</el-button>
                            </el-card>
                        </div>
                    </div>
                    </div>
                </el-crad>
            </div>
        </div>
        <!-- 中部 -->
    </div>
</template>

<script>
import '../css/base.css';
import '../css/message.css'
import { getInfo,getUser } from '../../../api/login';
import store from '@/store'
import { letterList,letterDetail,addLetter } from '../../../api/massage'
export default {
    name: "messaage",
    data() {
        return {
            routerLink:false,
            routerusername:'',
            routertoId:0,
            textarea2:[],
            userInfo: {},
            letterList: [],
            user:[],
            letterDetail:[],
            letterDetailParam:'',
            nowuser:{},
            addLetterParam:{
                toId:0,
                content:'',
            },
            index:0
        }
    },
    created() {
        this.fetchletterList();
        this.fetchuser();
    },
    methods: {
        fetchaddLetter(){
            this.addLetterParam.content=this.textarea2[this.index];
            addLetter(this.addLetterParam).then(response => {
                this.$message.success(response.data);
                this.fetchletterDetail(this.addLetterParam.toId,this.userInfo.id,this.index);
            }).catch(error => {
                console.error('发送私信失败:', error);
            }); 
                
        },
        fetchletterDetail(fromId,toId,index) {
            this.nowuser=this.user[index]
            if(fromId>toId){
            this.letterDetailParam=fromId+"_"+toId
            }else{
                this.letterDetailParam=toId+"_"+fromId
            }
            letterDetail(this.letterDetailParam).then(response => {
                this.letterDetail = response.data;
            }).catch(error => {
                console.error('获取私信详情失败:', error);
            });
            if(this.userInfo.id==fromId){
                this.addLetterParam.toId=toId;
            }else{
                this.addLetterParam.toId=fromId;
            }
            this.index=index;
            this.fetchletterList() 
        },
        formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString(); // 转换为本地时间格式
  },
        truncateContent(content) {  
      if (!content) return '';  
      return content.length > 100 ? content.slice(0, 15) + '...' : content;  
    },
    fetchletterList() {
        letterList().then(response => {
            this.letterList = response.data;
            const userPromises = this.letterList.map(letter => this.fetchaloneusers(letter.target));
            Promise.all(userPromises)
                .then(users => {
                    this.user = users; // 将所有用户信息存入 this.user
                })
                .catch(error => {
                    console.error('获取用户信息失败:', error);
                });
        }).catch(error => {
            console.error('获取私信列表失败:', error);
        });
    },
    fetchuser() {
        const token = store.state.user.token;
        getInfo(token).then(response => {
            this.userInfo = response.data;
            console.log(this.userInfo);
        }).catch(error => {
            console.error('获取用户信息失败:', error);
        });
    },
    fetchaloneusers(id) { 
        return getUser(id).then(response => response.data)
            .catch(error => {
                console.error('获取用户信息失败:', error);
            });
    }
}

}
</script>