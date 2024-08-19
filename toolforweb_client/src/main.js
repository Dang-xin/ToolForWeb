

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import ToolForWeb from '@/ToolForWeb.vue'
import router from "@/components/js/router.js";
import axios from "@/components/js/axiosRequest.js";

const app = createApp(ToolForWeb)

app.config.globalProperties.$axios = axios;
app.use(ElementPlus)
app.use(router)
app.mount('#app')
