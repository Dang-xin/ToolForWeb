<template>
  <el-table
      :data="filterQueryListInfo"
      border
      style="width: 100%"
      @cell-click="showUnitInput"
      >
    <el-table-column type="selection" width="55" />
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
        <SqlEditor :queryFromDBQueryList="query" @getQueryFromEdit="getRowQueryFromSqlEditor" ref="invokeRef" />
        </el-dialog>
        <span v-else>点击查看详细内容</span>
      </template>
    </el-table-column>
    <el-table-column prop="result" label="查询结果" />
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
  <el-button class="mt-4" style="width: 100%" @click="addItem">
    添加新行
  </el-button>
</template>

<script lang="ts" setup>
import {computed, ref} from "vue";
import SqlEditor from "@/components/pages/DBTools/SqlEditor.vue";
import {ElMessageBox} from "element-plus";

interface queryInfoItem {
  businessName: string
  queryName: string
  query: string
  result: string
}

const query = ref('');

const queryListInfo = ref<Array<queryInfoItem>>([]);
const search = ref('');

const inputRef = ref();
const invokeRef = ref();

let tableRowEditId = ref() // 控制可编辑的每一行
let tableColumnEditIndex = ref<number>(); //控制可编辑的每一列
let SqlEditorVisible = ref(true);

const filterQueryListInfo = computed(() =>
    queryListInfo.value.filter(
        (data) =>
            !search.value ||
            data.businessName.toLowerCase().includes(search.value.toLowerCase()) ||
            data.businessName === ""
    )
)

function deleteRow (index: number) {
  queryListInfo.value.splice(index, 1)
}

function addItem() {
  queryListInfo.value.push({
    businessName: "",
    queryName: "",
    query: "",
    result: ""
  })
}

function showUnitInput(row: any, column: any) {
  SqlEditorVisible.value = true;
  //赋值给定义的变量
  tableRowEditId.value = column.id;
  tableColumnEditIndex.value = filterQueryListInfo.value.indexOf(row);

  query.value = row.query;
}

function cellFocus() {
  inputRef.value.focus();
}


function setCellValue() {
  tableRowEditId.value = null;
  tableColumnEditIndex.value = -1;
}

function getRowQueryFromSqlEditor(query) {
  queryListInfo.value[tableColumnEditIndex].query = query;
  alert(queryListInfo.value)
}

function editorClose(done: () => void) {
  ElMessageBox.confirm('是否保存保存修改后的内容')
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

</script>