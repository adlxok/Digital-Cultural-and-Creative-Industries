<template>
  <div>
    <!-- 轮播图 -->
    <Carousel></Carousel>
    <StartCreation></StartCreation>
    <!-- 内容部分 -->
    <Content></Content>
    <!-- 故宫——藏品 -->
    <block></block>
    <!-- 文章部分 -->
    <Article></Article>
    <!-- 下面——滑动 -->
    <huad></huad>
    <!-- 开始创作 -->
    
  </div>
</template>

<script>
import Carousel from "./Carousel";
import Content from "./Content";
import block from "./block";
import Article from "./Article";
import huad from "./huad";
import StartCreation from "./StartCreation";

export default {
  name: "Home",
  components: {
    Carousel,
    Content,
    block,
    Article,
    huad,
    StartCreation,
  },
};
</script>

<style scoped>
</style>
