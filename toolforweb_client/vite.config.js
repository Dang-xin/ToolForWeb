import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import Icons from "unplugin-icons/vite";
import IconsResolver from 'unplugin-icons/resolver'
import Components from 'unplugin-vue-components/vite'
import monacoEditorPlugin from 'vite-plugin-monaco-editor'

import {
  ElementPlusResolver,
  VueUseComponentsResolver,
  VueUseDirectiveResolver
} from "unplugin-vue-components/resolvers";

export default defineConfig({
  plugins: [
    vue(),
    Components({
      //imports指定组件所在目录，默认为src/components
      dirs: ['src/components', 'src/views'],
      //需要去解析的文件
      include: [/\.vue$/, /\.vue\?vue/, /\.md$/],
      resolvers: [
        ElementPlusResolver(),
        VueUseComponentsResolver(),
        VueUseComponentsResolver(),
        VueUseDirectiveResolver(),
        IconsResolver({
          // 自动引入的Icon组件统一前缀，默认为icon，设置false为不需要前缀
          prefix: 'icon',
          // 当图标集名字过长时，可使用集合别名
          alias: {
            system: 'system-icons'
          },
        })
      ]
    }),
    Icons({
      compiler: 'vue3',
      autoInstall: true,
    }),
    monacoEditorPlugin({
      languageWorkers: ['editorWorkerService', 'typescript', 'json', 'html']
    }),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  }
})
