<template>
  <div ref="sqlEditor" :style="{height: '800px'}"/>
</template>

<script lang="ts" setup>
import * as monaco from "monaco-editor";
import {onMounted, ref} from "vue";
import { format } from 'sql-formatter';
import { language as sqlLanguage } from 'monaco-editor/esm/vs/basic-languages/sql/sql.js';

const sqlEditor = ref();
let editor: monaco.editor.IStandaloneCodeEditor;
// const {keywords} = sqlLanguage;
// const {operators} = sqlLanguage;
// const {builtinFunctions} = sqlLanguage;
// const {builtinVariables} = sqlLanguage;

onMounted(() => {
  initSqlEditor();
})

function initSqlEditor() {
  editor = monaco.editor.create(sqlEditor.value,
      {
        value: "", // 编辑器初始显示文字
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
  // ctrl + Q自动格式化
  editor.addCommand(monaco.KeyMod.CtrlCmd | monaco.KeyCode.KeyQ, sqlFormatter);

  monaco.languages.registerCompletionItemProvider('pgsql', {
    provideCompletionItems: function () {
      let suggestions = [];
      sqlLanguage["keywords"].forEach(item => {
        suggestions.push({
          label: item,
          kind: monaco.languages.CompletionItemKind.Keyword,
          insertText: item
        });
      })
      sqlLanguage.operators.forEach(item => {
        suggestions.push({
          label: item,
          kind: monaco.languages.CompletionItemKind.Operator,
          insertText: item
        });
      })
      sqlLanguage["builtinFunctions"].forEach(item => {
        suggestions.push({
          label: item,
          kind: monaco.languages.CompletionItemKind.Function,
          insertText: item
        });
      })
      sqlLanguage["builtinVariables"].forEach(item => {
        suggestions.push({
          label: item,
          kind: monaco.languages.CompletionItemKind.Variable,
          insertText: item
        });
      })
      return {
        suggestions:suggestions
      };
    },
  });
}

// sql格式化
function sqlFormatter() {
  editor.setValue(format(editor.getValue()));
}

// 自动补全提示
function registerCompletion() {
  monaco.languages.registerCompletionItemProvider('sql', {
    provideCompletionItems: (model, position, context, token) => {
      let suggestions: any[] = []
      const { lineNumber, column } = position
      const textBeforePointer = model.getValueInRange({
        startLineNumber: lineNumber,
        startColumn: 0,
        endLineNumber: lineNumber,
        endColumn: column,
      })

      suggestions.push(getKeySuggestions())
      return {
        suggestions
      }
    },
  })
}

function getKeySuggestions() {
  language
}
</script>

