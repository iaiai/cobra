import Vue from 'vue'
import VueRouter from 'vue-router'
// import ViewUI from 'view-design'

Vue.use(VueRouter)

import {routers} from './router'

export const router = new VueRouter({
    // mode: 'history', //是否是用锚点的方式跳转界面
    base: process.env.BASE_URL,
    routes: routers
})

router.beforeEach((to, from, next) => {
    //在这里可以处理跳转前的一些事情
    // ViewUI.LoadingBar.start()
    next()
})

router.afterEach((to, from) => {
    //跳转之后的一些事情
    window.scrollTo(0, 0)
    // ViewUI.LoadingBar.finish()
})