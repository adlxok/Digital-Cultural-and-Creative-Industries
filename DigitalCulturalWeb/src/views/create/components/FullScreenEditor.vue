  <template>
    <div class="full-screen-editor" @contextmenu.prevent="openContextMenu">
      <!-- 顶部工具栏 -->
      <div class="top-toolbar">
        <div class="top-toolbar-left">
          <button @click="undo" class="icon-button">
            <img src="../../../assets/images/undo.jpg" alt="撤销">
          </button>
          <button @click="redo" class="icon-button">
            <img src="../../../assets/images/redo.jpg" alt="重做">
          </button>
        </div>
        <div class="top-toolbar-center">
          <!-- 可以在这里添加顶部工具栏中间内容 -->
        </div>
        <div class="top-toolbar-right">
          <!-- <button @click="downloadImage">下载</button> -->
          <!-- 下载按钮 -->
    <el-button type="primary" @click="openDownloadDialog">下载</el-button>
    
    <!-- 下载确认对话框 -->
    <el-dialog
      title="确认"
      :visible.sync="showDownloadDialog"
      width="30%">
      <span>确定要下载吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showDownloadDialog = false">取消</el-button>
        <el-button type="primary" @click="downloadImage">确定</el-button>
      </span>
    </el-dialog>
        </div>
      </div>

      <!-- 主内容区域 -->
      <div class="main-content">
        <!-- 左侧模块栏 -->
        <div class="sidebar">
          <!-- 模块栏 -->
          <div class="sidebar-header">
            <button @click="activeModule = 'add'">添加</button>
            <!-- <button @click="activeModule = 'materials'">素材</button> -->
          </div>
          <!-- 返回按钮 -->
          <button @click="goBack" class="back-button">返回</button>
        </div>

        <!-- 中间工具栏区域，根据选择的模块动态显示 -->
        <div class="toolbar">
          <!-- 添加工具栏 -->
          <div v-if="activeModule === 'add'" class="toolbar-content">
            <h3>图片</h3>
            <div class="toolbar-options">
              <button @click="uploadImage">本地图片</button>
            </div>
            <h3>形状</h3>
      <div class="toolbar-options">
        <input type="color" v-model="shapeColor" class="color-picker" />
        <button @click="addShape('rect')">添加矩形</button>
        <button @click="addShape('circle')">添加圆形</button>
      </div>
            <h3>文本</h3>
            <div class="toolbar-options">
              <button @click="addText">添加文字</button>
            </div>
            <h3>擦除</h3>
      <div class="toolbar-options">
        <button @click="toggleEraseMode" :class="{ 'active-button': isErasing }">橡皮擦</button>
        <input
          v-if="isErasing"
          type="range"
          min="1"
          max="50"
          v-model="eraserSize"
          @input="updateEraserSize"
          class="brush-size-slider"
        />
        <!-- 清空画布按钮 -->
    <el-button type="danger" @click="openClearDialog">清空画布</el-button>
        <!-- 清空画布的对话框 -->
    <el-dialog
      title="确认"
      :visible.sync="showClearDialog"
      width="30%">
      <span>确定要清空画布吗？此操作无法撤销。</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showClearDialog = false">取消</el-button>
        <el-button type="primary" @click="clearCanvas">确定</el-button>
      </span>
    </el-dialog>
      </div>
            <!-- 绘制 -->
      <h3>绘制</h3>
      <div class="toolbar-options toolbar-draw-options">
        <button 
          @click="startDrawing" 
          :class="{ 'active-button': isDrawing }"
        >
          画笔
        </button>
        <button 
          @click="startDrawingLine" 
          :class="{ 'active-button': isDrawingLine }"
        >
          直线
        </button>
      </div>

      <!-- 调整大小和颜色滑块容器 -->
  <div class="toolbar-slider-container">
    <!-- 画笔大小调整滑块 -->
    <input 
      v-if="isDrawing"
      type="range" 
      min="1" 
      max="50" 
      v-model="brushSize"
      @input="updateBrushSize"
      class="brush-size-slider"
    >
    <!-- 直线大小调整滑块 -->
    <input 
      v-if="isDrawingLine"
      type="range" 
      min="1" 
      max="50" 
      v-model="lineWidth"
      @input="updateLineWidth"
      class="line-width-slider"
    >
    <!-- 颜色选择器 -->
    <input 
      v-if="isDrawing || isDrawingLine"
      type="color" 
      v-model="drawColor" 
      @input="updateDrawColor"
      class="color-picker"
    >
  </div>
          </div>

          <!-- 素材工具栏 -->
          <div v-if="activeModule === 'materials'" class="toolbar-content">
            <h3>素材工具栏</h3>
            <button>示例素材 1</button>
            <button>示例素材 2</button>
          </div>
        </div>

        <!-- 右侧画布区域 -->
        <div class="canvas-container">
          <canvas ref="canvas"></canvas>
        </div>
        <el-dialog
      title="未保存的更改"
      :visible.sync="showUnsavedChangesDialog"
      width="30%"
      @close="handleDialogClose"
    >
      <p>您有未保存的更改，是否确定要离开页面？</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleDialogConfirm">确定</el-button>
        <el-button @click="handleDialogCancel">取消</el-button>
      </span>
    </el-dialog>
      </div>

      <!-- 隐藏的文件输入框，用于上传图片 -->
      <input type="file" ref="fileInput" @change="handleFileUpload" style="display: none;">

      <!-- 右击上下文菜单 -->
      <ul v-if="showContextMenu" :style="{ top: `${contextMenuY}px`, left: `${contextMenuX}px` }" class="context-menu">
        <li @click="copySelected">复制</li>
        <li @click="deleteSelectedObjects">删除</li>
      </ul>
    </div>
  </template>

  <script>
  import { fabric } from 'fabric';
  

  export default {
    data() {
      return {
        canvas: null,
        activeModule: 'add', // 当前激活的模块，默认是 'add'
        isDrawing: false, // 是否正在绘制
        isErasing: false, // 是否正在擦除
        isDrawingLine: false,
        history: [], // 保存历史记录
        redoStack: [], // 重做操作栈
        showContextMenu: false, // 是否显示右键菜单
        contextMenuX: 0, // 右键菜单的X位置
        contextMenuY: 0, // 右键菜单的Y位置
        brushSize: 5, // 画笔默认大小
        lineWidth: 5, // 直线默认宽度
        drawColor: '#000000', // 默认绘制颜色
        shapeColor: '#ff0000', // 形状的默认颜色
      eraserSize: 20, // 橡皮擦的默认大小
      showClearDialog: false,
      showDownloadDialog: false,
      hasUnsavedChanges: false, // 添加未保存状态
      showUnsavedChangesDialog: false,
      };
    },
    mounted() {
      this.initializeCanvas();
  window.addEventListener('resize', this.resizeCanvas);
  window.addEventListener('beforeunload', this.handleBeforeUnload);
    },
    beforeDestroy() {
      window.removeEventListener('resize', this.resizeCanvas);
  window.removeEventListener('beforeunload', this.handleBeforeUnload);
    },
    methods: {
      goBack() {
        if (this.hasUnsavedChanges) {
          this.showUnsavedChangesDialog= true;
        } else {
          this.$router.go(-1); // 返回上一页

        }
      },
      handleDialogConfirm() {
    this.showUnsavedChangesDialog = false;
    // 强制离开页面（例如，通过刷新页面或导航到另一个路由）
    this.$router.go(-1);
  },

  handleDialogCancel() {
    this.showUnsavedChangesDialog = false;
  },
      startDrawing() {
          // 禁用其他绘制模式
          this.isDrawingLine = false;
          this.canvas.off('mouse:down', this._onLineMouseDown);

          // 切换绘制模式
          this.isDrawing = !this.isDrawing;
          if (this.isDrawing) {
              this.canvas.freeDrawingBrush = new fabric.PencilBrush(this.canvas);
              this.canvas.freeDrawingBrush.color = this.drawColor; // 使用选定颜色
              this.canvas.freeDrawingBrush.width = this.brushSize;

              
          }
          this.canvas.isDrawingMode = this.isDrawing;
          
      },

      startDrawingLine() {
          // 禁用其他绘制模式
          this.isDrawing = false;
          this.canvas.isDrawingMode = false;

          // 切换直线绘制模式
          this.isDrawingLine = !this.isDrawingLine;
          if (this.isDrawingLine) {
              this.canvas.on('mouse:down', this._onLineMouseDown);
          } else {
              this.canvas.off('mouse:down', this._onLineMouseDown);
          }
      },

      // 更新画笔颜色
      updateDrawColor() {
          if (this.isDrawing) {
              this.canvas.freeDrawingBrush.color = this.drawColor;
          }
      },

      // 更新画笔大小
      updateBrushSize() {
          if (this.isDrawing) {
              this.canvas.freeDrawingBrush.width = this.brushSize;
          }
      },

      // 更新直线宽度
      updateLineWidth() {
          this.lineWidth = parseInt(this.lineWidth);
      },

      _onLineMouseDown(event) {
          const pointer = this.canvas.getPointer(event.e);
          
          this.line = new fabric.Line([pointer.x, pointer.y, pointer.x, pointer.y], {
              strokeWidth: this.lineWidth,
              stroke: this.drawColor, // 使用选定颜色
              selectable: true,
              evented: true,
          });

          this.canvas.add(this.line);
          // 禁用画布上的选择功能
    this.canvas.selection = false;
          this.canvas.on('mouse:move', this._onLineMouseMove);
          this.canvas.on('mouse:up', this._onLineMouseUp);
          
      },

      _onLineMouseMove(event) {
          if (!this.line) return;
          const pointer = this.canvas.getPointer(event.e);
          this.line.set({ x2: pointer.x, y2: pointer.y });
          this.canvas.renderAll();
      },

      _onLineMouseUp() {
          this.canvas.off('mouse:move', this._onLineMouseMove);
          this.canvas.off('mouse:up', this._onLineMouseUp);
          this.line.setCoords(); // 更新直线的坐标
          this.line = null; // 清除当前绘制的直线
           // 恢复画布上的选择功能
    this.canvas.selection = true;
          this.saveHistory(); // 保存当前画布状态
      },







      
      initializeCanvas() {
        this.canvas = new fabric.Canvas(this.$refs.canvas);
        this.resizeCanvas();

        // 监听画布变化，保存历史记录
        this.canvas.on('object:modified', this.saveHistory);
        this.canvas.on('object:added', this.saveHistory);
        this.canvas.on('mouse:down', this.closeContextMenu);

        // 保存初始化状态
        this.saveHistory();
      },
      resizeCanvas() {
        this.canvas.setWidth(window.innerWidth - 400); // 400px for sidebar + toolbar width
        this.canvas.setHeight(window.innerHeight - 90); // 90px for top-toolbar height
        this.canvas.calcOffset();
      },
      // 保存当前画布状态到历史记录
    saveHistory() {
      const json = this.canvas.toJSON();
      this.history.push(json);
      // 如果历史记录超过最大数量，移除最早的记录
    if (this.history.length > 50) { // 50 是一个示例的最大数量
        this.history.shift();
    }
      // 清空重做栈
      // this.redoStack = [];
      this.hasUnsavedChanges = true; // 标记有未保存的更改
    },
    
    // 撤销操作
    undo() {
      if (this.history.length > 1 && this.canvas.getObjects().length > 1) {
        console.log("Undo: current history length", this.history.length); // Debug information
        this.redoStack.push(this.history.pop());
        console.log("Undo: c", this.redoStack);
        const previousState = this.history[this.history.length - 1];  
        this.canvas.loadFromJSON(previousState, () => {
          this.canvas.renderAll(); // 在加载完成后重新渲染画布
          console.log("Undo: canvas updated"); // Debug information
        });
      } else {
        console.log("No more undo available"); // Debug information
      }
    },
    
    // 重做操作
    redo() {
      console.log("Redo method called"); // Debug information
      if (this.redoStack.length > 0) {
        console.log("Redo: redo stack length", this.redoStack.length); // Debug information
        const nextState = this.redoStack.pop();
        console.log("Redo: nextState", nextState); // Debug information
        this.history.push(nextState);
        this.canvas.loadFromJSON(nextState, () => {
          this.canvas.renderAll(); // 在加载完成后重新渲染画布
          console.log("Redo: canvas updated"); // Debug information
        });
      } else {
        console.log("Redo stack is empty"); // Debug information
      }
    },
      addText() {
        const text = new fabric.Textbox('输入文字', {
          left: 100,
          top: 100,
          fontSize: 20,
        });
        this.canvas.add(text);
      },
      addShape(type) {
        let shape;
        switch (type) {
          case 'rect':
            shape = new fabric.Rect({
              left: 100,
              top: 100,
              fill: this.shapeColor, // 使用选择的颜色
              width: 100,
              height: 100,
            });
            break;
          case 'circle':
            shape = new fabric.Circle({
              left: 100,
              top: 100,
              fill: this.shapeColor, // 使用选择的颜色
              radius: 50,
            });
            break;
        }
        this.canvas.add(shape);
      },
      uploadImage() {
        this.$refs.fileInput.click();
      },
      handleFileUpload(event) {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = (e) => {
          fabric.Image.fromURL(e.target.result, (img) => {
            this.canvas.add(img);
          });
        };
        reader.readAsDataURL(file);
      },

  // 更新橡皮擦大小
  updateEraserSize() {
      if (this.isErasing) {
        this.canvas.freeDrawingBrush.width = this.eraserSize;
      }
    },

    // 切换橡皮擦模式
    toggleEraseMode() {
      this.isErasing = !this.isErasing;
      if (this.isErasing) {
        this.isDrawing = false; // 退出绘制模式
        this.canvas.freeDrawingBrush = new fabric.EraserBrush(this.canvas);
        this.canvas.freeDrawingBrush.color = 'white'; // 模拟橡皮擦效果
        this.canvas.freeDrawingBrush.width = this.eraserSize; // 设置擦除宽度
        this.canvas.isDrawingMode = true; // 启用绘图模式（模拟擦除）
      } else {
        this.canvas.isDrawingMode = false; // 退出绘图模式
      }
    },
    openClearDialog() {
      // 打开清空画布的对话框
      this.showClearDialog = true;
    },
      clearCanvas() {
        // 清空画布
      this.canvas.clear();
      this.saveHistory();
      this.hasUnsavedChanges = false; // 清空画布后没有未保存的更改
      // 关闭对话框
      this.showClearDialog = false;
      },
      saveImage() {
        const dataURL = this.canvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.href = dataURL;
        link.download = 'design.png';
        link.click();
        this.hasUnsavedChanges = false;
        // 关闭对话框
      this.showDownloadDialog = false;
      },
      openDownloadDialog() {
      // 打开下载确认的对话框
      this.showDownloadDialog = true;
    },
    confirmDownload() {
      // 下载逻辑
      this.downloadImage();

      
    },
      downloadImage() {
        this.saveImage();
      },
      openContextMenu(event) {
      const activeObjects = this.canvas.getActiveObjects();
      if (activeObjects.length > 0) {
        this.contextMenuX = event.clientX;
        this.contextMenuY = event.clientY;
        this.showContextMenu = true;
      }
    },
      closeContextMenu() {
        this.showContextMenu = false;
      },
      copySelected() {
        const activeObject = this.canvas.getActiveObject();
        if (activeObject) {
          activeObject.clone((cloned) => {
            this.canvas.discardActiveObject();
            cloned.set({
              left: activeObject.left + 10,
              top: activeObject.top + 10,
            });
            this.canvas.add(cloned);
            this.canvas.setActiveObject(cloned);
            this.canvas.renderAll();
          });
        }
        this.closeContextMenu();
      },
      deleteSelectedObjects() {
      const activeObjects = this.canvas.getActiveObjects();
      if (activeObjects.length > 0) {
        activeObjects.forEach((obj) => this.canvas.remove(obj));
        this.canvas.discardActiveObject();
        this.canvas.renderAll();
      }
      this.closeContextMenu();
    },
    },
  };
  </script>

  <style scoped>
  .color-picker {
  width: 50px;
  height: 30px;
  margin-top: 10px;
}

/* 画笔大小滑块 */
.brush-size-slider {
  width: 80%; /* 滑块宽度调整 */
}

  /* 颜色选择器样式 */
  .color-picker {
    width: 50px;
    height: 30px;
    margin-top: 10px;
  }

  /* 绘制工具按钮容器样式 */
  .toolbar-draw-options {
    display: flex;
    justify-content: flex-start; 
    width: 100%;
  }

  /* 调整滑块容器样式 */
  .toolbar-slider-container {
    display: flex;
    justify-content: center; /* 滑块在容器中居中 */
    margin-top: 10px;
  }

  .brush-size-slider,
  .line-width-slider {
    width: 80%; /* 滑块宽度调整 */
  }

  /* 活动按钮样式 */
  .toolbar-options .active-button {
    background-color: #ff5722; /* 激活状态的颜色 */
    color: white;
  }

  .full-screen-editor {
    display: flex;
    flex-direction: column;
    height: 100vh;
  }

  .top-toolbar {
    display: flex;
    justify-content: space-between;
    padding: 10px;
    background-color: #f0f0f0;
  }

  .top-toolbar-left,
  .top-toolbar-center,
  .top-toolbar-right {
    display: flex;
  }

  .icon-button {
    margin-right: 10px;
    background: none;
    border: none;
  }

  .main-content {
    display: flex;
    flex: 1;
  }

  .sidebar {
    width: 100px;
    background-color: #e0e0e0;
  }

  .toolbar {
    width: 150px;
    background-color: #c0c0c0;
  }

  .canvas-container {
    flex: 1;
    position: relative;
    background-color: #f0f0f0;
    overflow: hidden;
  }

  .context-menu {
    position: absolute;
    list-style: none;
    padding: 0;
    margin: 0;
    background: white;
    border: 1px solid #ccc;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
    z-index: 1000;
  }

  .context-menu li {
    padding: 8px 12px;
    cursor: pointer;
  }

  .context-menu li:hover {
    background-color: #f0f0f0;
  }

  .full-screen-editor {
    display: flex;
    flex-direction: column;
    height: 100vh;
    background-color: #f0f2f5;
  }

  /* 顶部工具栏样式 */
  .top-toolbar {
    height: 90px;
    background-color: #333;
    color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }

  .top-toolbar-left {
    display: flex;
    align-items: center;
    gap: 10px;
  }

  .icon-button {
    background: none;
    border: none;
    padding: 10px;
    cursor: pointer;
    transition: background-color 0.3s;
  }

  .icon-button:hover {
    background-color: #444;
    border-radius: 5px;
  }

  .icon-button img {
    width: 24px;
    height: 24px;
  }

  .top-toolbar-center,
  .top-toolbar-right {
    display: flex;
    align-items: center;
  }

  .top-toolbar-right {
    gap: 10px;
  }

  .top-toolbar-actions button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
  }

  .top-toolbar-actions button:hover {
    background-color: #45a049;
  }

  /* 主内容区域样式 */
  .main-content {
    display: flex;
    flex-grow: 1;
  }

  /* 左侧模块栏样式 */
  .sidebar {
    width: 120px;
    background-color: #ffffff;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px 10px;
  }

  /* 模块栏样式 */
  .sidebar-header {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
  }

  .sidebar button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 10px 15px;
    margin-bottom: 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
  }

  .sidebar button:hover {
    background-color: #45a049;
  }

  .back-button {
    margin-top: auto;
    background-color: #f44336;
    border: none;
    color: white;
    padding: 10px 15px;
    border-radius: 5px;
  }

  .back-button:hover {
    background-color: #e53935;
  }

  /* 中间工具栏区域样式 */
  .toolbar {
    width: 280px;
    background-color: #333;
    color: #fff;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px 10px;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
  }

  .toolbar-content {
    display: flex;
    flex-direction: column;
    width: 100%;
  }

  .toolbar-content h3 {
    margin: 20px 0 10px;
    font-size: 16px;
    text-align: left;
    width: 100%;
    padding-left: 10px;
    color: #f1f1f1;
  }

  .toolbar-options {
    display: flex;
    flex-wrap: wrap; /* 允许换行 */
    gap: 10px; /* 设置每个按钮之间的间距 */
  }

  .toolbar-options button {
    background-color: #555;
    border: none;
    color: white;
    padding: 10px;
    width: calc(33.333% - 10px); /* 每行三个按钮 */
    text-align: center;
    font-size: 14px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
    box-sizing: border-box; /* 包含 padding 和 border */
  }

  .toolbar-options button:hover {
    background-color: #777;
  }


  /* 右侧画布区域样式 */
  .canvas-container {
    flex-grow: 1;
    position: relative;
    background-color: #fff;
    margin: 20px;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  }

  canvas {
    border: none;
  }

  </style>
