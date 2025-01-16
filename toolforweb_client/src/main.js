

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import ToolForWeb from '@/ToolForWeb.vue'
import router from "@/components/ts/Router.js";
import axios from "@/components/ts/AxiosRequest.ts";
import { createPinia } from  "pinia"

const app = createApp(ToolForWeb)

app.config.globalProperties.$axios = axios;
app.use(ElementPlus)
app.use(router)
app.use(createPinia())
app.mount('#app')
