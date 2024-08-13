<template>
    <div class="postList">
        <div class="post-item">
           <div class="profile-photo">
						<el-avatar
							src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
				</div> 
                <div class="username">
                    <span>{{username}}</span>
                </div>
                <div class="Type">{{ this.postList[0].Type }}</div>
                <div class="createTime"></div>
                <div class="likeCount"></div>
                <div class="commentCount"></div>
        </div>

  
    </div>
</template>

<script>
import { showPostList } from "@/api/post";
export default {
    data() {
        return {
            username: '',
            createTime: '',
            Type: '',
            likeCount: '',
            commentCount: '',
            total: 0,
            postList: []
        }
    },
    created() {
       this.postList();
    },
    methods: {
     PostList(params){
          showPostList(params).then(response => {
            this.total=response.data.total;
            this.postList=response.data.list;
          })
     }
    }
    
}
</script>