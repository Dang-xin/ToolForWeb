<template>
  <el-container style="height: 800px">
    <!--  布局容器-侧部   -->
    <el-aside width="8%">
      <h4 class="mb-2">网页版工具</h4>
      <el-menu
          default-active="$route.path"
          model="vertical"
          router
      >
        <el-menu-item index="/">
          <el-icon><icon-fluent-mdl2-access-logo/></el-icon>
          <span>数据库连接信息</span>
        </el-menu-item>
        <el-sub-menu index="2">
          <template #title>
            <el-icon><icon-fa-solid-tools/></el-icon>
            <span>数据库操作</span>
          </template>
          <el-menu-item index="/DBTools">查询列表</el-menu-item>
          <el-menu-item index="/DBTools/SqlEditor">编辑器</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/Test">
          <el-icon></el-icon>
          <span>测试</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!--  布局容器-头部   -->
      <el-header>
      </el-header>
      <!--  布局容器-主体   -->
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>
<script setup>
</script>