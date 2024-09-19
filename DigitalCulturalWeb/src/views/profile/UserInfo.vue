<template>
  <div class="user-info-container">
    <h2 class="title">个人设置</h2>
    <div class="content-wrapper">
      <!-- 左侧表单区域 -->
      <el-form :model="user" class="user-info-form" label-width="120px">
        <el-form-item label="昵称">
          <el-input v-model="user.nickname"></el-input>
        </el-form-item>
        <el-form-item label="个性签名">
          <!-- 设置 maxlength 属性来限制输入的最大字符数 -->
          <el-input
            type="textarea"
            v-model="user.bio"
            :rows="5"
            :autosize="{ minRows: 5, maxRows: 10 }"
            maxlength="50"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save">保存</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧头像区域 -->
      <div class="avatar-section">
        <img :src="user.profileImageUrl" alt="Avatar" class="avatar-preview" />
        <el-upload
          class="avatar-uploader"
          action="" 
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <el-button size="small" type="primary">上传头像</el-button>
        </el-upload>
      </div>
    </div>
  </div>
</template>

<script>
import {getInfo} from '@/api/getInfo';
import {updateProfile} from '../../api/updateProfile'
export default {
  data() {
    return {
      user: {}
    };
  },
  mounted() {
    // 在组件挂载时获取用户数据
    this.fetchUserData();
  },
  methods: {
    fetchUserData() {
        getInfo().then((res) => {
          this.user = res.data;
          console.log(this.user)
        })
        .catch(error => {
          console.error('获取用户数据失败', error);
        });
    },
    save() {
      let userProfile = {
        nickName: this.user.nickname,
        blog: this.user.bio,
        // 如果还要保存其他数据，比如头像，可以在这里添加
        userId: this.$store.state.user.userId,
      };
      // 保存用户信息的逻辑
      updateProfile(userProfile).then((res) => {
        this.$message.success('保存成功');
        console.log('保存信息成功:', res);
      })
      // console.log('保存信息:', this.user);
    },
    handleAvatarSuccess(response, file) {
      // 头像上传成功后，设置头像 URL
      this.user.avatar = URL.createObjectURL(file.raw);
      console.log(this.user.avatar)
      console.log(response)
    },
    beforeAvatarUpload(file) {
      // 可以在这里添加文件格式和大小限制
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
};
</script>

<style scoped>
.user-info-container {
  display: flex;
  flex-direction: column;
  padding: 20px;
  max-width: 100%; /* 确保容器不会超出视口宽度 */
  margin: 0; /* 移除水平居中 */
}

.content-wrapper {
  display: flex;
  justify-content: space-between; /* 左右对齐 */
}

.title {
  font-size: 24px;
  margin-bottom: 33px; /* 调整标题与表单之间的间距 */
  margin-left: 399px; /* 手动设置标题与左侧的距离 */
  text-align: left; /* 标题文本左对齐 */
  width: auto; /* 可以设置成 auto 以便根据内容自动调整宽度 */
  padding-left: 10px; /* 可增加 padding-left 以微调标题的左侧间距 */
}

.user-info-form {
  width: 60%; /* 表单区域宽度 */
  margin: 0;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 35%; /* 头像区域宽度 */
}

.avatar-preview {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  margin-bottom: 20px;
}
</style>
