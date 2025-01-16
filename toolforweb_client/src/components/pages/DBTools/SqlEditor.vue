<template>
  <el-row>
    <el-col :span="2">
      <el-select
          v-model="selectedSqlName"
          placeholder="请选择"
          size="small"
          @visible-change="changeSqlSelect()"
      >
        <el-option
            v-for="item in sqlSelect"
            :key="item[Constant.queryItem.Number]"
            :label="item[Constant.queryItem.Number]"
            :value="item[Constant.queryItem.Value]"
        >
          <el-tooltip placement="right">
            <template #content> <div v-html="item[Constant.queryItem.ValueToHtml]"> </div></template>
            <span>{{ item[Constant.queryItem.Number] }}</span>
          </el-tooltip>
        </el-option>
      </el-select>
    </el-col>
    <el-col :offset="1" :span="15">
      <el-button-group>
        <el-button type="primary" size="small">实行</el-button>
        <el-button type="primary" size="small" @click="main.getAllTables(selectSqlValue)">单表查询</el-button>
        <el-button type="primary" size="small" @click="main.parseSql(selectSqlValue)">解析SQL</el-button>
      </el-button-group>
    </el-col>
  </el-row>
  <el-row style="margin-top: 1%">
    <el-col>
      <el-tabs
          v-model="editableTabsNumber"
          type="card"
          editable
          @edit="handleTabsEdit"
          @tab-click="changeTab"
      >
        <el-tab-pane
            v-for="item in tabEditData"
            :key="item[Constant.queryItem.Number]"
            :label="item[Constant.queryItem.Tab]"
            :name="item[Constant.queryItem.Number]"
        >
        </el-tab-pane>
      </el-tabs>
    </el-col>
    <el-col>
      <div ref="sqlEditor" style="height: 800px;" />
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import * as monaco from "monaco-editor";
import {computed, onMounted, ref} from "vue";
import { format } from 'sql-formatter';
import * as main from '@/components/ts/SqlEditor'
import * as Util from '@/components/ts/CommonUtil'
import * as Constant from '@/components/ts/Constant'
import {TabPaneName, TabsPaneContext} from "element-plus";

const sqlEditor = ref();
let editData = defineProps({
  queryFromDBQueryList: {
    type: Object,
  }
});

let tabEditData = ref<object[]>(editData.queryFromDBQueryList[Constant.QueryInfoItem.Query]);
let editableTabsNumber = ref(1);

const emit = defineEmits(["getQueryFromEdit"]);
let editor: monaco.editor.IStandaloneCodeEditor;
let provider: monaco.IDisposable;

let selectedSqlName = ref('');
let sqlSelect = ref<object[]>([]);

const selectSqlValue = computed(() => {
  return selectedSqlName.value;
});
const tabEditCount = computed(() => {
  if (tabEditData.value != null && tabEditData.value.length > 0) {
    return  tabEditData.value.reduce((max, item) => {
      return item[Constant.queryItem.Number] > max ? item[Constant.queryItem.Number] : max;
    }, tabEditData.value[0][Constant.queryItem.Number]);
  } else {
    return 1;
  }
});

onMounted(() => {
  initSqlEditor();
})

function initSqlEditor() {
  editor = monaco.editor.create(sqlEditor.value,
      {
        value: tabEditData.value.length > 0 ? tabEditData.value[0][Constant.queryItem.Value] : Constant.String.Empty , // 编辑器初始显示文字
        language: "pgsql", // 语言支持自行查阅demo
        automaticLayout: true, // 自适应布局
        theme: "vs", // 官方自带三种主题vs, hc-black, or vs-dark
        foldingStrategy: "indentation",
        contextmenu: true, // 启用上下文菜单
        renderLineHighlight: "all", // 行亮
        autoIndent: "advanced", // 控制当用户键入、粘贴、移动或缩进行时，编辑器是否应自动调整缩进
        selectOnLineNumbers: true, // 显示行号
        minimap: { // 代码缩略图
          enabled: false,
        },
        readOnly: false, // 只读
        fontSize: 14, // 字体大小
        scrollBeyondLastLine: false, // 取消代码后面一大段空白
        overviewRulerBorder: false, // 不要滚动条的边
      }
  )
  registerCompletion()

  const model = editor.getModel();
  // 监听输入内容的变化
  model.onDidChangeContent(event =>  {
    event.changes.forEach(change => {
      // 获取变更的内容
      const changedContent = change.text;
    });

  });

  // ctrl + Q自动格式化
  editor.addCommand(monaco.KeyMod.CtrlCmd | monaco.KeyCode.KeyQ, sqlFormatter);
}

// sql格式化
function sqlFormatter() {
  editor.setValue(format(editor.getValue()));
}

// 自动补全提示
function registerCompletion() {
  if (provider != null) {
    provider.dispose();
  }

  provider = monaco.languages.registerCompletionItemProvider('pgsql', {
    provideCompletionItems: (model, position) => {
      let suggestions: any[] = []
      const { lineNumber, column } = position
      const textBeforePointer = model.getValueInRange({
        startLineNumber: lineNumber,
        startColumn: 0,
        endLineNumber: lineNumber,
        endColumn: column - 1,
      });
      if (textBeforePointer === "") {
        suggestions = main.getKeyWordsSuggestions();
      }
      return {
        suggestions
      }
    },
  })
}

function returnQueryToDBQueryList() {
  let activeValue = tabEditData.value.find(tab => tab[Constant.queryItem.Number] === editableTabsNumber.value);
  activeValue[Constant.queryItem.Value] = editor.getValue();

  emit("getQueryFromEdit",editData.queryFromDBQueryList);
}

const handleTabsEdit = (
    targetName: TabPaneName | undefined,
    action: 'remove' | 'add'
) => {
  if (action === 'add') {
    const newTabName = tabEditCount.value + 1
    tabEditData.value.push({
      [Constant.queryItem.Tab]: 'New Tab',
      [Constant.queryItem.Number]: newTabName,
      [Constant.queryItem.Value]: Constant.String.Empty
    });

    let oldActiveValue = tabEditData.value.find(tab => tab[Constant.queryItem.Number] === editableTabsNumber.value);
    let newActiveValue = tabEditData.value.find(tab => tab[Constant.queryItem.Number] === newTabName);
    oldActiveValue[Constant.queryItem.Value] = editor.getValue();
    editableTabsNumber.value = newTabName
    editor.setValue(newActiveValue[Constant.queryItem.Value]);

  } else if (action === 'remove') {
    const tabs = tabEditData.value
    let activeName = editableTabsNumber.value
    if (activeName === targetName) {
      for(const tab of tabs) {
        if (tab[Constant.queryItem.Number] === targetName) {
          const nextTab = tabEditData.value.find(tab => tab[Constant.queryItem.Number] === targetName + 1 || tab[Constant.queryItem.Number] === targetName - 1);
          if (nextTab) {
            activeName = nextTab[Constant.queryItem.Number]
          }
        }
      }
    }

    editableTabsNumber.value = activeName
    tabEditData.value = tabs.filter((tab) => tab[Constant.queryItem.Number] !== targetName)
  }
}

const changeTab = (pane: TabsPaneContext) => {
  let oldActiveValue = tabEditData.value.find(tab => tab[Constant.queryItem.Number] === editableTabsNumber.value);
  let newActiveValue = tabEditData.value.find(tab => tab[Constant.queryItem.Number] === pane.props.name);
  oldActiveValue[Constant.queryItem.Value] = editor.getValue();
  editor.setValue(newActiveValue[Constant.queryItem.Value]);
  changeSqlSelect();
}

const changeSqlSelect = () => {
  const sqlEditorValue = editor.getValue();
  const sqlList = sqlEditorValue.split(";")

  sqlSelect.value.splice(0, sqlSelect.value.length);

  sqlList.forEach((value, index) => {
    if (Util.isNotEmpty(value)) {
      let sql = format(value);
      let html = Util.strToHtml(sql);
      sqlSelect.value.push({
        [Constant.queryItem.Number]: `SQL${index + 1}`,
        [Constant.queryItem.Value]: sql,
        [Constant.queryItem.ValueToHtml] : html
      });
    }
  });
}

defineExpose({
  returnQueryToDBQueryList
});

</script>