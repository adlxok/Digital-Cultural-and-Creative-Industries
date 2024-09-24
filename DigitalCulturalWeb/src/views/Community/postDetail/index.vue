<template>
  <div class="postdetail">

    <el-card class="detail-box1-card">
      <div class="author">
        <div class="profile-photo-detail">
          <router-link :to="{ path: '/aloneprofile', query: { userId: user.id }}">
            <el-avatar v-if="user" :size="60" :src="user.profileImageUrl" alt="用户头像"></el-avatar>
            <el-avatar v-else :size="60"
              src="https://img1.baidu.com/it/u=3647744349,2477516282&fm=253&fmt=auto&app=138&f=JPEG?w=380&h=380"
              alt="默认头像"></el-avatar>
          </router-link>
        </div>
        <div v-if="user" class="username-detail">
          <span>{{ user.username }}</span>
        </div>
        <div v-else class="username-detail">
          <span>没有用户名的用户</span>
        </div>
        <div class="createTime-detail">
          <span>{{ post.formattedCreateTime }}</span>
        </div>
        <div class="sign-button">
          <div class="like-button">
            <!-- <span @click="toggleLike()">点赞{{likeCout}}</span> -->
            <button size="small" @click="toggleLike()"  class="like-button-item"><span>{{likeconment}}({{likeCount}})</span></button>
            <transition name="fade">
              <span v-if="liked" class="heart">❤️</span>
            </transition>
          </div>

          <div class="star-button">
            <button size="small"  @click="toggleStar()"  class="star-button-item"><span>{{starconment}}({{starCount}})</span></button>
            <transition name="fade">
              <span v-if="starred" class="el-icon-star-off el-icon-star-off-detail " ></span>
            </transition>
          </div>
        </div>

      </div>
      <div class="post-detail">
        <div class="title-detail">
          <span>{{ post.title }}</span>
          <el-divider content-position="right">标题</el-divider>
        </div>
        <div class="contentHtml" v-html="post.contentHtml"></div>
      </div>
    </el-card>
    <el-card class="detail-box2-card">
      <div class="detail-item">
        <div class="sign-detail"></div>
        <a id="mao-detail1"></a>
        <span v-if="replyCount">{{replyCount}}条回帖</span>
        <span v-else>暂无回帖</span>
        <a href="#mao-detail"><el-button size="mimi" type="warning" class="detail-button1">回帖</el-button></a>
      </div>

      <div class="reply-comment">
        <div v-for="(commentmap,index) in comments" :key="commentmap.communityComment.id" class="reply-comment-item">
          <div v-if="commentmap.communityComment.entityType=1" class="comment-item">
            <div v-if="commentmap.commentUser && commentmap.commentUser.username" class="username-detail">
              <span>{{ commentmap.commentUser.username }}</span>
            </div>
            <div v-else class="username-detail">
              <span>没有用户名的用户</span>
            </div>
            <div class="profile-photo-detail">
              <router-link to="/addpost">
                <el-avatar v-if="commentmap.commentUser && commentmap.commentUser.profileImageUrl" :size="60"
                  :src="commentmap.commentUser.profileImageUrl" alt="用户头像"></el-avatar>
                <el-avatar v-else :size="60"
                  src="https://img1.baidu.com/it/u=3647744349,2477516282&fm=253&fmt=auto&app=138&f=JPEG?w=380&h=380"
                  alt="默认头像"></el-avatar>
              </router-link>
            </div>
            <div class="comment" v-html="commentmap.communityComment.content">
            </div>
          </div>
          <div class="replypost-time">
            <span>{{index+1 }}楼</span>
            <span>{{ formatDate(commentmap.communityComment.createTime) }}</span>
          </div>
          <div class="replycomment-itme">
            <el-button size="mini" class="reply-button"
              @click="toggleCollapse(index),addReplyCommentParam(commentmap.communityComment.id)"> {{show(index)}}({{
              commentmap.replyCount }})</el-button>
            <div style="margin-top: 20px; height: auto;">
              <el-collapse-transition>
                <div v-show="collapsedIndex === index">
                  <div class="transition-box">
                    <div v-for="replymap in commentmap.allreply" :key="replymap.replyComment.id"
                      class="reply-comment-item1">
                      <div v-if="replymap.user && replymap.user.username" class="username-reply">
                        <span class="reply-content-username">{{ replymap.user.username }}:</span>
                        <span class="reply-content-itme">{{ replymap.replyComment.content }}</span>
                      </div>
                      <div v-else class="username-reply">
                        <span>没有用户名的用户</span>
                      </div>
                      <div class="profile-photo-reply">
                        <router-link to="/comprofile">
                          <el-avatar v-if="replymap.user && replymap.user.profileImageUrl" :size="40"
                            :src="commentmap.commentUser.profileImageUrl" alt="用户头像"></el-avatar>
                          <el-avatar v-else :size="40"
                            src="https://img1.baidu.com/it/u=3647744349,2477516282&fm=253&fmt=auto&app=138&f=JPEG?w=380&h=380"
                            alt="默认头像"></el-avatar>
                        </router-link>
                      </div>


                      <div class="relpy-comment-sign">
                        <div><span>{{formatDate(replymap.replyComment.createTime) }}</span></div>
                        <div class="reply-button1"><span
                            @click="addReplyCommentParam(replymap.replyComment.id,replymap.user.username )">回复</span>
                        </div>
                      </div>
                    </div>
                    <el-input type="textarea" :rows="2" :placeholder="placeholders" v-model="textarea"
                      class="reply-textarea">
                    </el-input>
                    <!-- <el-button v-if="ifclick===true" size="small" type="primary" class="reply-button2" @click=" addReplyComment()">发表</el-button> -->
                    <el-button size="small" type="primary" class="reply-button2"
                      @click="addReplyComment()">发表</el-button>

                  </div>
                </div>
              </el-collapse-transition>
            </div>
          </div>
        </div>
      </div>

      <div class="page-detail">
        <div class="pagination-detail">
          <a href="#mao-detail1"> <button id="firstPage" @click="handleshouye()">首页</button></a>
          <a href="#mao-detail1"> <button id="prevPage" @click="handlelast()">上一页</button></a>
          <span id="pageInfo">第 {{ this.listParams.pageNum }} 页</span>
          <a href="#mao-detail1"> <button id="nextPage" @click="handlenext()">下一页</button></a>
          <a href="#mao-detail1"><button id="lastPage" @click="handleweiye()">尾页</button></a>
        </div>
      </div>

      <div class="add-comment">
        <a id="mao-detail"></a>
        <!-- 工具栏 -->
        <Toolbar style="border-bottom: 1px solid #ccc;height: 40px;" :editor="editor" :defaultConfig="toolbarConfig" />
        <!-- 编辑器 -->
        <Editor style="height: 300px; overflow-y: hidden;" v-model="html" :defaultConfig="editorConfig" :mode="mode"
          @onCreated="onCreated" @onChange="onChange" />
      </div>
      <el-button type="primary" @click="addPostComment()">发表</el-button>
    </el-card>
  </div>
</template>

<script>
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
import '../css/postDetail.css';
import '../css/base.css';
import store from '@/store'
import { getPostById } from '../../../api/post';
import {showCommentList,addComment} from '../../../api/comment';
import {addLike,getLikeStatus,favoriteStatus,addFavorite}from '../../../api/like';
export default {
  name: "CommunityPostDetail",
  components: { Editor, Toolbar },
  data() {
    return {
      editor: null,
      html: '',
      toolbarConfig: {
        toolbarKeys: ["emotion", "uploadImage", "insertImage", // 网络图片
          "insertLink", // 插入链接
          "editImage",  // 重做
          "undo"]
      },
      // 跨域是否传递 cookie ，默认为 false
      editorConfig: {
        placeholder: '请输入内容...',
        withCredentials: true,
        MENU_CONF: {
          // 图片上传
          uploadImage: {
            server: 'http://localhost:8080/communityUpload/uploadImage',
            fieldName: 'file',
            // 单个文件的最大体积限制，默认为 2M
            maxFileSize: 10 * 1024 * 1024, // 10M
            // 最多可上传几个文件，默认为 100
            maxNumberOfFiles: 2,
            // 自定义增加 http  header
            headers: this.getUploadHeaders(),
            // 超时时间，默认为 10 秒
            timeout: 10 * 1000, //10 秒
            onSuccess: this.handleUploadSuccess
          },
        }
      },
      mode: 'simple',
      id: null,
      user: {},
      post: {},
      isBookmarked: false,
      listParams:{
        pageNum: 1,
        pageSize: 10,
        postId: 0
      },
      total:0,
      comments:[],
      replyCount:0,
      addCommentParams:{
        content:'',
        entityType:0,
        entityId:0,
        targetId:0,
        status:0
      },
      collapsedIndex: null,
      index: 0,
      likefont:null,
      likeconment:'',
      starconment:'',
      starfont:null,
      liked: '',
      starred: '',
      likeCount: 0,
      starCount: 0,
      textarea: '',
      placeholders: '请输入评论内容...',
      currentReplyId: null,
      replyContent: '',
      replyCommentList:[],
      ifclick:false,
      userid:0,
      addLikeParams:{
        postId:0,
        entityType:1,
        entityId:0,
        entityUserId:0,
      },
      postlikeStatusParams:{
        entityType:1,
        entityId:0,
      },
      addStarParams:{
        entityType:1,
        entityId:0,
      },
      poststarStatusParams:{
        id:1,
        entityId:0,
      },
    }
  },
  created() {
    this.id=this.$route.query.postId;
    this.listParams.postId=this.id; 
    this.fetchLikeStatus();
    this.fetchstarStatus();
    this.fetchPostDetail();
    this.fetchCommentList();
    this.fetchreplyCommentList();
   
  },
  computed: {
    likeIconClass() {
      return this.liked ? 'el-icon-lollipop red' : 'el-icon-lollipop';
    },
    starIconClass() {
      return this.starred ? 'el-icon-star red' : 'el-icon-star-off';
    },

 
  },
  
  methods: {
    show(index) {
      return this.collapsedIndex === index ? '收起回复' : '回复';
    },
    toggleCollapse(index) {
      // this.index = index;
      this.collapsedIndex = this.collapsedIndex === index ? null : index;
   
  },
    toggleLike() {
      this.likefont=!this.likefont;
      this.likeconment=this.likefont?'取消点赞':"点赞"
      console.log(this.likeconment)
      this.addLikeParams.postId=this.id;
      this.addLikeParams.entityId=this.id;
      this.addLikeParams.entityUserId=this.user.id;
      addLike(this.addLikeParams).then(res=>{
      console.log(res);
    })
    if(this.likefont&&this.likeCount<1){
      this.liked = true;
      this.likeCount++;
     }else if(!this.likefont&&this.likeCount>0){
      this.liked = false;
      this.likeCount--;
       
     }
    },
    toggleStar() {
      this.starfont=!this.starfont;
      this.starconment=this.starfont?'取消收藏':"收藏"
      console.log(this.starconment)
      this.addStarParams.entityId=this.id;
      addFavorite(this.addStarParams).then(res=>{
      console.log(res);
    })
    if(this.starfont&&this.starCount<1){
      this.starred = true;
      this.starCount++;
     }else if(!this.starfont&&this.starCount>0){ 
      this.starred = false;
      this.starCount--;
      console.log(":this.poststarStatusParams"+this.poststarStatusParams)
     }
    }
  ,
  handleshouye(){
  this.listParams.pageNum=1;
  this.fetchCommentList();
  console.log("点击首页");
  },
  handlelast(){
  if(this.listParams.pageNum>1){
  this.listParams.pageNum--;
  this.fetchCommentList();
  console.log("点击上一页");
  }
  },
  handlenext(){
	if(this.listParams.pageNum==this.total){
		this.$message.info('已经是最后一页了');
	}else if(this.listParams.pageNum<=this.total)
	{  this.listParams.pageNum++;
  this.fetchCommentList();
  console.log("点击下一页");}
  },
handleweiye(){
	if(this.listParams.pageNum==this.total){
		this.$message.info('已经是最后一页了');
	}else{ this.listParams.pageNum=this.total;
  this.fetchCommentList();
  console.log("点击尾页");}
  },
    onCreated(editor) {
      this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
    },
    onChange(editor) {
      this.htmls = editor.getText();
      this.html=editor.getHtml();
    },
    getUploadHeaders() {
      const token = store.state.user.token;
      if (token) {
        return {
          Accept: 'application/json',
          token: token
        };
      } else {
        console.log("token为空");
      }
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleString(); // 转换为本地时间格式
    },
    fetchPostDetail() {
      getPostById(this.id).then(response => {
        const post = response.data.post;
        post.formattedCreateTime = this.formatDate(post.createTime);
        this.post = post;
        this.user = response.data.user;
        this.userid=response.data.user.id;
        this.likeCount = response.data.likeCount;
        console.log(response);
      })
     
  },
  fetchLikeStatus(){
    this.postlikeStatusParams.entityId=this.id;
    getLikeStatus(this.postlikeStatusParams).then(res=>{
        if(res.data==1){
          this.likeconment="取消点赞"
          this.likefont=true;
          this.starred = true;
        }else{
          this.likeconment="点赞"
          this.likefont=false;
          this.starred = false;
        }
})
  },
  fetchstarStatus(){
    favoriteStatus(this.id).then(res=>{
        if(res.data.status==1){
          this.starconment="取消收藏"
          this.starfont=true;
          this.starred = true;
        }else{
          this.starconment="收藏"
          this.starfont=false;
          this.starred = false;
        }
        this.starCount=res.data.count;
        console.log(res)
})
  },
  fetchCommentList() {
    showCommentList(this.listParams).then(response => {
      this.comments = response.data.list;
      this.replyCount=response.data.total;
      this.total=response.data.pageNum;
      console.log(response);
    })
  },
  addPostComment() {
    this.addCommentParams.content=this.html;
    this.addCommentParams.entityType=1;
    this.addCommentParams.entityId=this.id;
    this.addCommentParams.targetId=this.userid;
    if( this.html==="<p><br></p>"){
      this.$message.error("评论内容不能为空");
      return;
    }
    addComment(this.addCommentParams,this.id);
    this.$message.success("评论成功")
    console.log("添加帖子评论"+this.userid);
  },
  addReplyCommentParam(replyId,replyname){
    if(replyname==null){
      this.placeholders='请输入评论内容...'
    }else
  this.placeholders='回复'+replyname+'：';
  this.addCommentParams.entityType=0;
  this.addCommentParams.entityId=replyId;
  console.log(this.addCommentParams);
  },
  addReplyComment() {
    this.addCommentParams.content=this.textarea;
    if( this.textarea===""){
      this.$message.error("评论内容不能为空");
      return;
    }
    addComment(this.addCommentParams,this.id);
    this.textarea = ''; // 清空输入框
    this.placeholders='请输入评论内容...';
    console.log("添加回复评论");
  },
},
  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  }
}

</script>
