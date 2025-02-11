<template>
  <el-row type="flex" align="middle" style="height: 50px;">
    <el-col>
      <el-button @click="DBQueryListStore.uploadData(queryListInfo)">保存</el-button>
      <el-button @click="initQueryInfoList()">加载</el-button>
      <el-button @click="deleteQueryInfoList()">删除</el-button>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <el-table
          :data="filterQueryList"
          border
          style="width: 100%"
          @cell-click="showUnitInput"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID">
          <template #default="point">
            <el-text> {{ point.$index + 1 }}</el-text>
          </template>
        </el-table-column>
        <el-table-column prop="businessName" label="机能名">
          <template #default="point">
            <el-input
                v-if="tableRowEditId === point.column.id && tableColumnEditIndex === point.$index"
                v-model="point.row.businessName"
                ref="inputRef"
                @blur="setCellValue"
                @keyup.enter="setCellValue"
                @mouseenter ="cellFocus"
            />
            <span v-else>{{ point.row.businessName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="queryName" label="查询名称">
          <template #default="point">
            <el-input
                v-if="tableRowEditId === point.column.id && tableColumnEditIndex === point.$index"
                v-model="point.row.queryName"
                ref="inputRef"
                @blur="setCellValue"
                @keyup.enter="setCellValue"
                @mouseenter ="cellFocus"
            />
            <span v-else>{{ point.row.queryName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="query" label="查询内容">
          <template #default="point">
            <el-dialog
                v-if="tableRowEditId === point.column.id && tableColumnEditIndex === point.$index"
                v-model="SqlEditorVisible" title="SQL编辑器" :before-close="editorClose" :append-to-body='true'>
              <SqlEditor :queryFromDBQueryList="point.row" @getQueryFromEdit="getRowQueryFromSqlEditor" ref="invokeRef" />
            </el-dialog>
            <span v-else>点击查看详细内容</span>
          </template>
        </el-table-column>
        <el-table-column prop="result" label="查询结果" />
        <el-table-column prop="status" label="状态">
          <template #default="point">
            <el-text v-if = "point.row.status == Constant.DBOperate.Select" type="info">
              既存
            </el-text>
            <el-text v-else-if="point.row.status == Constant.DBOperate.Insert" type="primary">
              新规
            </el-text>
            <el-text v-else-if="point.row.status == Constant.DBOperate.Delete"  type="danger">
              删除
            </el-text>
            <el-text v-else-if="point.row.status == Constant.DBOperate.Update"  type="success">
              变更
            </el-text>
          </template>
        </el-table-column>
        <el-table-column align="right" width="300">
          <template #header>
            <el-input v-model="search" size="small" placeholder="根据机能名检索" />
          </template>
          <template #default="scope">
            <el-button
                type="primary"
                size="small"
                @click="deleteRow(scope.$index)"
            >
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <el-button class="mt-4" style="width: 100%" @click="addItem">
        添加新行
      </el-button>
    </el-col>
  </el-row>

</template>

<script lang="ts" setup>
import {computed, onMounted, ref} from "vue";
import SqlEditor from "./SqlEditor.vue";
import {ElMessageBox} from "element-plus";
import Enumerable from 'linq';
import { useDBQueryListStore } from "../../ts/PiniaStore"
import * as Constant from "../../ts/Constant"
import * as Type from "../../ts/Type"
import * as Message from "../../ts/ParseResponse"

const query = ref('');

const DBQueryListStore = useDBQueryListStore();

const queryListInfo = ref<Array<Type.QueryInfoItem>>([]);

const search = ref('');

const inputRef = ref();
const invokeRef = ref();

let tableRowEditId = ref() // 控制可编辑的每一行
let tableColumnEditIndex = ref<number>(0); //控制可编辑的每一列
let SqlEditorVisible = ref(true);

const filterQueryList = computed(() =>
    queryListInfo.value.filter(
        (data) =>
            !search.value ||
            data.BusinessName.toLowerCase().includes(search.value.toLowerCase()) ||
            data.BusinessName === Constant.String.Empty
    )
)

const maxQueryListId = computed(() =>
    {
      return queryListInfo.value.length === 0 ? 0 : Enumerable.from(queryListInfo.value).max(query => query.No) + 1;
    }
)

function deleteRow (index: number) {
  queryListInfo.value[index].Status = Constant.DBOperate.Delete;
}

function addItem() {
  const tab: Type.QueryTabItem = {
    Tab: "Tab1",
    No: 1,
    Value: Constant.String.Empty,
  }

  const queryInfo: Type.QueryInfoItem = {
    No: maxQueryListId.value,
    BusinessName: Constant.String.Empty,
    QueryName: Constant.String.Empty,
    Query: Array<Type.QueryTabItem>({
      Tab: "Tab1",
      No: 1,
      Value: Constant.String.Empty}),
    Status: Constant.DBOperate.Insert,
    Result: []
  }

  queryListInfo.value.push(queryInfo);
}

function showUnitInput(row: any, column:any) {
  if (column.property === undefined) {
    return;
  }

  SqlEditorVisible.value = true;
  //赋值给定义的变量
  tableRowEditId.value = column.id;
  tableColumnEditIndex.value = filterQueryList.value.indexOf(row);

  query.value = row.query;
  filterQueryList.value[tableColumnEditIndex.value].Status = Constant.DBOperate.Update;
}

function cellFocus() {
  inputRef.value.focus();
}

function setCellValue() {
  tableRowEditId.value = null;
  tableColumnEditIndex.value = -1;
}

function getRowQueryFromSqlEditor(queryData: Type.QueryInfoItem) {
  
  if ( tableColumnEditIndex.value !== undefined) {
    tableColumnEditIndex.value
    queryListInfo.value[tableColumnEditIndex.value].Query = queryData.Query;
    queryListInfo.value[tableColumnEditIndex.value].Result = queryData.Result;
  } else {
    Message.error("没有被选中的行数据");
     return;
  }

}

function editorClose(done: () => void) {
  ElMessageBox.confirm('是否保存保存修改后的内容',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      })
      .then(() => {
        invokeRef.value.returnQueryToDBQueryList();
        setCellValue();
        done()
      })
      .catch(() => {
        setCellValue();
        done()
      })
}

function initQueryInfoList() {
  queryListInfo.value.length = 0;
  const data = DBQueryListStore.reloadData();
  data.then((rows) => {
      rows.forEach(row => {
        queryListInfo.value.push({
          No: row.No,
          BusinessName: row.BusinessName,
          QueryName: row.QueryName,
          Query: row.Query,
          Status: row.Status,
          Result: row.Result
        })
      });
  },(reason) => {

  })
}

function deleteQueryInfoList() {
  DBQueryListStore.delete();
}

onMounted(() => {
  initQueryInfoList();
})
</script>