<template>
  <el-tabs v-model="activeName" @before-leave="handleBeforeLeave">
    <el-tab-pane label="用户管理" name="first">用户管理</el-tab-pane>
    <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
    <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
  </el-tabs>
</template>

<script>
import { ref } from 'vue';

export default {
  setup() {
    const activeName = ref('first');

    const handleBeforeLeave = (newTab, oldTab) => {
      console.log('即将离开的标签页的key:', oldTab);
      console.log('即将激活的标签页的key:', newTab);
    };

    return {
      activeName,
      handleBeforeLeave,
    };
  },
};
</script>