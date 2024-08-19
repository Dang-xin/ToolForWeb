<template>
  <el-row style="height: 5%;">
    <el-col>
      <el-button type="primary" size="small">运行</el-button>
    </el-col>
  </el-row>
  <el-row>
    <el-col>
      <div ref="sqlEditor" style="height: 800px;" />
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import * as monaco from "monaco-editor";
import {onMounted, ref} from "vue";
import { format } from 'sql-formatter';
import { main } from '@/components/js/sqlEditor'

const sqlEditor = ref();
const editData = defineProps({
  queryFromDBQueryList: {
    type: String,
    default: ''
  }
});
const emit = defineEmits(["getQueryFromEdit"]);
let editor: monaco.editor.IStandaloneCodeEditor;
let provider: monaco.IDisposable;
let beforeEnter: string;

onMounted(() => {
  initSqlEditor();
})

function initSqlEditor() {
  editor = monaco.editor.create(sqlEditor.value,
      {
        value: editData.queryFromDBQueryList, // 编辑器初始显示文字
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
  model.onDidChangeContent(e =>  {


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
  console.log("自动补全建议生成开始")
  if (provider != null) {
    provider.dispose();
  }

  provider = monaco.languages.registerCompletionItemProvider('pgsql', {
    provideCompletionItems: (model, position, context, token) => {
      let suggestions: any[] = []
      const { lineNumber, column } = position
      const textBeforePointer = model.getValueInRange({
        startLineNumber: lineNumber,
        startColumn: 0,
        endLineNumber: lineNumber,
        endColumn: column - 1,
      });
      if (textBeforePointer === "") {
        suggestions = main("getKeyWordsSuggestions", editor);
      }
      return {
        suggestions
      }
    },
  })
}

function returnQueryToDBQueryList() {
  emit("getQueryFromEdit",editor.getValue())
}

defineExpose({
  returnQueryToDBQueryList
});


</script>

