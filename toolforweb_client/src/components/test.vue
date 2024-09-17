<template>
  <el-button @click="addMess">追加值</el-button>
  <el-button @click="changeMess">改变值</el-button>
  {{ mess }}
</template>

<script lang="ts" setup>
import {computed, ref} from "vue";

const mess = ref([]);

function addMess() {
  const object = {"key": "1", "value": "mess1"};
  mess.value.push(object);
}

function changeMess() {
   mess.value[mess.value.length-1]["key"] = "mess2";
}

</script>