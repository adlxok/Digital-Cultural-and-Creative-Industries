<template>
  <div class="editor">
    <!-- 页头 -->
    <div class="editor-header">
      <el-card class="editor-box1-card">
        <div class="editor-nav">
          <router-link to="/comhome">
            <i class="el-icon-s-home"></i>
          </router-link>
          <div class="editor-nav-title">
            <span>发布</span>
          </div>
          <div style="border: 1px solid #ccc; margin-top: 30px">
            <!-- 工具栏 -->
            <Toolbar style="border-bottom: 1px solid #ccc;height: 60px;" :editor="editor"
              :defaultConfig="toolbarConfig" />
          </div>
        </div>
      </el-card>
    </div>
    <!-- 侧边栏 -->
    <!-- <div class="editor-sidebar">
      <el-card class="editor-box2-card">
        <h3>目录</h3>  
      <ul>  
        <li v-for="(header, index) in headers" :key="index">  
          <a :href="`#miao${index}`">{{ header}}</a>  
        </li>  
      </ul>  
      </el-card>
    </div> -->
    <!-- 编辑工具 -->
    <div class="editor-tool">
      <el-card class="editor-box3-card">
        <div class="tool-other">
          <span>标题</span>
          <div class="tool-title">
            <el-input type="text" placeholder="请输入内容" v-model="input" maxlength="50" show-word-limit />
          </div>
          <div class="font-item">
            <span>类别</span>
          </div>
          <div class="tool-class">
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />

            </el-select>
          </div>
        </div>
        <div style="border: 1px solid #ccc; margin-top: 30px">
          <!-- 编辑器 -->
          <Editor style="height: 400px; overflow-y: hidden;" v-model="html" :defaultConfig="editorConfig" :mode="mode"
            @onCreated="onCreated" @onChange="onChange" />
        </div>
      </el-card>
    </div>
    <!-- 页尾 -->
    <div class="editor-end">
      <el-card class="editor-box4-card">
        <div class="editor-button">
          <el-button plain>保存</el-button>
          <el-button type="warning" plain @click="fetchaddpost()">发布</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>
  
  <script>
  import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
  import { addpost,saveImage }from "../../../api/post";
  import store from '@/store'
  import '../css/addpost.css';
  import '../css/base.css'

  export default {
    components: { Editor, Toolbar },
    data() {
        return {
            editor: null,
            html: '<p>hello</p>',
            toolbarConfig: { },
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
            onSuccess:this.handleUploadSuccess
            },
            //视频上传
           uploadVideo:{
            server: 'http://localhost:8080/communityUpload/uploadImage',
            fieldName: 'file',
            // 自定义增加 http  header
            headers: this.getUploadHeaders(),
            maxFileSize: 30 * 1024 * 1024,
            // 超时时间，默认为 10 秒
            timeout: 10 * 1000, //10 秒
            onSuccess:this.handleUploadSuccess
             }
           },
          },
            mode: 'default', // or 'simple'
            options: [
        { value: '选项1', label: '书法' },
        { value: '选项2', label: '国画' },
        { value: '选项3', label: '中医' },
        { value: '选项4', label: '刺绣' },
        { value: '选项5', label: '汉服' },
        { value: '选项6', label: '戏剧' },
        { value: '选项7', label: '其他' }
      ],
       value: '',
       input: '',
       addpostParams:{
        title: '',
        content: '',
        contentHtml:''
       },
       htmls:'',
       saveImageParams: {
        imageUrlList: [],
                postId: 0
              },
        }
    },
    created() {

    },
    methods: {
        onCreated(editor) {
            this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
        },
        onChange(editor){
         this.htmls=editor.getText();
        },
        fetchaddpost(){
        this.addpostParams.title=this.input;
        this.addpostParams.contentHtml=this.html;
        this.addpostParams.content=this.htmls;
          addpost(this.addpostParams).then(response => {
            this.saveImageParams.postId = response.data;
            this.fetchsaveImage();
          })
        this.$message.success('发布成功');
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
      fetchsaveImage() {
      saveImage(this.saveImageParams).then(response => {
        console.log("图片上传成功", response);
      })
    },
    handleUploadSuccess(file, res) {
      this.saveImageParams.imageUrlList.push(res.data.url);
      console.log(`${file.name} 上传成功`, res);
    },
  },

    mounted() {
        // 模拟 ajax 请求，异步渲染编辑器
        // setTimeout(() => {
        //     this.html = '<p>模拟 Ajax 异步设置内容 HTML</p>'
        // }, 1500)
    },
    beforeDestroy() {
        const editor = this.editor
        if (editor == null) return
        editor.destroy() // 组件销毁时，及时销毁编辑器
    }
  }
  </script>