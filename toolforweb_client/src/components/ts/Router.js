import { createRouter, createWebHistory } from "vue-router"
import DBConnection from "@/components/pages/DBConnection.vue";
import DBQueryList from "@/components/pages/DBTools/DBQueryList.vue";
import SqlEditor from "@/components/pages/DBTools/SqlEditor.vue";

import Test from "@/components/test.vue";

const routes =  [
    { path: '/', redirect: { name: 'DBConnection' } },
    { path: '/DBConnection', name: 'DBConnection', component: DBConnection },
    { path: '/DBTools', redirect: { name: 'DBQueryList' } },
    { path: '/DBTools/DBQueryList', name: 'DBQueryList', component: DBQueryList },
    { path: '/DBTools/SqlEditor', name: 'SqlEditor', component: SqlEditor },
    { path: '/Test', name: 'Test', component: Test },
]

const router = new createRouter({
    history: createWebHistory(),
    routes
})

export default router