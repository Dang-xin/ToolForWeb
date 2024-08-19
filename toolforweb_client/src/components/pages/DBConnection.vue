<template>
  <el-row>
    <el-col :offset="6" :span="9">
      <el-form
          ref="DBConnectionInfoRef"
          :model="DBConnectionInfo"
          :rules="DBConnectionInfoRules"
          label-width="auto"
          class="demo-ruleForm"
          :size="DBConnectionInfoSize"
          status-icon
      >
        <el-form-item label="数据库连接名" prop="DBConnectionName">
          <el-autocomplete
              v-model="DBConnectionInfo.DBConnectionName"
              :fetch-suggestions="DBConnectionNameSuggestions"
              clearable
              @select="reloadDBConnectionInfo"
          />
        </el-form-item>
        <el-form-item label="数据库类型" prop="DBType">
          <el-select v-model="DBConnectionInfo.DBType" placeholder="数据库类型">
            <el-option label="mysql" value="mysql" />
            <el-option label="postgres" value="postgres" />
            <el-option label="oracle" value="oracle" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据库服务器" prop="server">
          <el-input v-model="DBConnectionInfo.server" />
        </el-form-item>
        <el-form-item label="数据库端口号" prop="port">
          <el-input v-model="DBConnectionInfo.port" />
        </el-form-item>
        <el-form-item label="数据库名" prop="dataBase">
          <el-input v-model="DBConnectionInfo.dataBase" />
        </el-form-item>
        <el-form-item label="用户名" prop="userID">
          <el-input v-model="DBConnectionInfo.userID" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="DBConnectionInfo.password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="connectDB(DBConnectionInfoRef)">连接</el-button>
          <el-button type="info" @click="saveDBConnectionInfoToLocalStorage">保存</el-button>
          <el-button type="danger" @click="deleteDBConnectionInfoToLocalStorage">删除</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue'
import type {ComponentSize, FormInstance, FormRules} from 'element-plus'
import request from "@/components/js/axiosRequest.js";
import {parseResponse} from "@/components/js/parseResponse.js"

interface DBConnectionInfoItem {
  DBConnectionName: string
  DBType: string
  server: string
  port: number
  dataBase: string
  userID: string
  password: string
}

const DBConnectionInfoSize = ref<ComponentSize>('default')
const DBConnectionInfoRef = ref<FormInstance>()
const DBConnectionInfosNameSuggestion = ref([])
let DBConnectionInfo = reactive<DBConnectionInfoItem>({
  DBConnectionName: '',
  DBType: '',
  server: '',
  port: undefined,
  dataBase: '',
  userID: '',
  password: ''
})

const DBConnectionInfoRules = ref<FormRules<DBConnectionInfoItem>>({
  DBConnectionName: [
    { required: true, message: '请输入数据库连接名', trigger: 'change' }
  ],
  DBType: [
    { required: true, message: '请输入数据库类型', trigger: 'change' }
  ],
  server: [
    { required: true, message: '请输入数据库服务器地址', trigger: 'change' }
  ],
  port: [
    { required: true, message: '请输入数据库端口号', trigger: 'change' }
  ],
  dataBase: [
    { required: true, message: '请输入数据库名', trigger: 'change' }
  ],
  userID: [
    { required: true, message: '请输入用户名', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入数密码', trigger: 'change' }
  ]
})

function connectDB(formEl: FormInstance | undefined) {
  if (!formEl) return
  formEl.validate((valid, fields) => {
    if (valid) {
      request({
        url: "/DBConnect",
        method: "post",
        data: DBConnectionInfo
      }).then((res) => {
        parseResponse(res);
      });
    } else {
      console.log('error submit!', fields)
    }
  })
}

function saveDBConnectionInfoToLocalStorage() {
  let DBConnectionInfosFromLocalStorage = localStorage.getItem('DBConnectionInfos');
  let DBConnectionInfosToLocalStorage;
  if (DBConnectionInfosFromLocalStorage != null) {
    DBConnectionInfosToLocalStorage = JSON.parse(DBConnectionInfosFromLocalStorage);
    DBConnectionInfosToLocalStorage[DBConnectionInfo.DBConnectionName] = DBConnectionInfo;
  } else {
    DBConnectionInfosToLocalStorage = {};
    DBConnectionInfosToLocalStorage[DBConnectionInfo.DBConnectionName] = DBConnectionInfo;
  }
  localStorage.setItem('DBConnectionInfos', JSON.stringify(DBConnectionInfosToLocalStorage));
  DBConnectionInfosNameSuggestion.value = getConnectionInfosNameSuggestionFromLocalStorage()
}

function deleteDBConnectionInfoToLocalStorage() {
  const DBConnectionName = DBConnectionInfo.DBConnectionName;
  let DBConnectionInfosFromLocalStorage = JSON.parse(localStorage.getItem('DBConnectionInfos'));
  delete DBConnectionInfosFromLocalStorage[DBConnectionName];
  localStorage.setItem('DBConnectionInfos', JSON.stringify(DBConnectionInfosFromLocalStorage));
  DBConnectionInfosNameSuggestion.value = getConnectionInfosNameSuggestionFromLocalStorage()
}

function DBConnectionNameSuggestions(input, cb) {
  const results = input
      ? DBConnectionInfosNameSuggestion.value.filter(getSuggestionsByInput(input))
      : DBConnectionInfosNameSuggestion.value
  cb(results)
}

function getSuggestionsByInput(input: string) {
  return (DBConnectionInfoForSuggestion) => {
    return (
        DBConnectionInfoForSuggestion.value.indexOf(input) === 0
    )
  }
}

function getConnectionInfosNameSuggestionFromLocalStorage() {
  const DBConnectionInfosFromLocalStorage = JSON.parse(localStorage.getItem('DBConnectionInfos'));
  const DBConnectionInfosNameList = [];
  for(let DBConnectionName in DBConnectionInfosFromLocalStorage){
    let suggestionMap = {};
    suggestionMap['value'] = DBConnectionInfosFromLocalStorage[DBConnectionName].DBConnectionName;
    DBConnectionInfosNameList.push(suggestionMap);
  }

  return DBConnectionInfosNameList;
}

function reloadDBConnectionInfo() {
  const DBConnectionInfosFromLocalStorage = JSON.parse(localStorage.getItem('DBConnectionInfos'));
  const DBConnectionInfoFromLocalStorage = DBConnectionInfosFromLocalStorage[DBConnectionInfo.DBConnectionName];

  DBConnectionInfo.DBConnectionName = DBConnectionInfoFromLocalStorage.DBConnectionName
  DBConnectionInfo.DBType = DBConnectionInfoFromLocalStorage.DBType
  DBConnectionInfo.server = DBConnectionInfoFromLocalStorage.server
  DBConnectionInfo.port = DBConnectionInfoFromLocalStorage.port
  DBConnectionInfo.dataBase = DBConnectionInfoFromLocalStorage.dataBase
  DBConnectionInfo.userID = DBConnectionInfoFromLocalStorage.userID
  DBConnectionInfo.password = DBConnectionInfoFromLocalStorage.password
}

onMounted(() => {
  DBConnectionInfosNameSuggestion.value = getConnectionInfosNameSuggestionFromLocalStorage()
})
</script>
