<template>
    <div class="image-editor">
      <div class="editor-container">
        <canvas ref="canvas" class="canvas"></canvas>
        <div class="toolbar">
          <button @click="addText">添加文字</button>
          <button @click="addShape('rect')">添加矩形</button>
          <button @click="addShape('circle')">添加圆形</button>
          <button @click="undo">撤销</button>
          <button @click="saveImage">保存</button>
        </div>
      </div>
      <input type="file" @change="handleFileUpload" />
    </div>
  </template>
  
  <script>
    import { fabric } from 'fabric';
  
  export default {
    data() {
      return {
        canvas: null,
        undoStack: [],
        redoStack: []
      };
    },
    mounted() {
      this.initializeCanvas();
    },
    methods: {
      initializeCanvas() {
        this.canvas = new fabric.Canvas(this.$refs.canvas);
        this.canvas.setHeight(500);
        this.canvas.setWidth(800);
        this.canvas.on('object:added', () => this.saveState());
      },
      addText() {
        const text = new fabric.Textbox('Sample Text', {
          left: 100,
          top: 100,
          fontSize: 20
        });
        this.canvas.add(text);
        this.saveState();
      },
      addShape(type) {
        let shape;
        switch (type) {
          case 'rect':
            shape = new fabric.Rect({
              left: 100,
              top: 100,
              fill: 'red',
              width: 100,
              height: 100
            });
            break;
          case 'circle':
            shape = new fabric.Circle({
              left: 100,
              top: 100,
              fill: 'blue',
              radius: 50
            });
            break;
        }
        this.canvas.add(shape);
        this.saveState();
      },
      saveState() {
        this.undoStack.push(this.canvas.toJSON());
        this.redoStack = [];
      },
      undo() {
        if (this.undoStack.length > 0) {
          this.redoStack.push(this.canvas.toJSON());
          const previousState = this.undoStack.pop();
          this.canvas.loadFromJSON(previousState);
          this.canvas.renderAll();
        }
      },
      redo() {
        if (this.redoStack.length > 0) {
          this.undoStack.push(this.canvas.toJSON());
          const nextState = this.redoStack.pop();
          this.canvas.loadFromJSON(nextState);
          this.canvas.renderAll();
        }
      },
      handleFileUpload(event) {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = (e) => {
          fabric.Image.fromURL(e.target.result, (img) => {
            this.canvas.clear();
            this.canvas.add(img);
            this.saveState();
          });
        };
        reader.readAsDataURL(file);
      },
      saveImage() {
        const dataURL = this.canvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.href = dataURL;
        link.download = 'edited-image.png';
        link.click();
      }
    }
  };
  </script>
  
  <style scoped>
  .image-editor {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .editor-container {
    position: relative;
  }
  
  .canvas {
    border: 1px solid #ccc;
  }
  
  .toolbar {
    margin-top: 10px;
  }
  
  .toolbar button {
    margin-right: 10px;
  }
  </style>
  