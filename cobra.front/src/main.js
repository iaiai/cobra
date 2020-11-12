import Vue from 'vue'
import App from './app.vue'
import packageUtil from '../package.json'
import {router} from './router'
import store from './store/index'
import WSocket from '@/libs/socket-io.js'
import Http from './http/http'
Vue.use(Http)

//全局css
import './commons/common.less'

//froala大文本编辑器，这个因为是收费的，所以这里不用这个了
//参数地址：https://froala.com/wysiwyg-editor/docs/framework-plugins/vue/
// require('froala-editor/js/froala_editor.pkgd.min.js')
// require('froala-editor/css/froala_editor.pkgd.min.css')
// require('froala-editor/css/froala_style.min.css')
// import VueFroala from 'vue-froala-wysiwyg'
// Vue.use(VueFroala)

//*************************neditor大文本编辑器 start***************************//
import VueNeditorWrap from 'vue-neditor-wrap'
Vue.use(VueNeditorWrap)
//*************************neditor大文本编辑器 end***************************//

//工具类
import Util from './libs/util'
Vue.use(Util)

//iview
import ViewUI from 'view-design'
import 'view-design/dist/styles/iview.css'
Vue.use(ViewUI)

//事件总线触发
import VueBus from 'vue-bus'
Vue.use(VueBus)

//iconfont图标
import './assets/iconfont/iconfont.css'

//***************font-awesome-start********************//
// import fontawesome from '@fortawesome/fontawesome'
// import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
// import solid from '@fortawesome/fontawesome-free-solid'
// import regular from '@fortawesome/fontawesome-free-regular'
// import brands from '@fortawesome/fontawesome-free-brands'
//
// fontawesome.library.add(solid)
// fontawesome.library.add(regular)
// fontawesome.library.add(brands)
//
// Vue.component('font-awesome-icon', FontAwesomeIcon)
//***************font-awesome-end********************//

//***************mavonEditor-start********************//
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)
//***************mavonEditor-end********************//

// import VueUeditorWrap from 'vue-ueditor-wrap'
// Vue.use(VueUeditorWrap)

//****************Vue粒子特效-start************************//
import VueParticles from 'vue-particles'
Vue.use(VueParticles)
//****************Vue粒子特效-end************************//

import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/darcula.css'   //这个是你设置了什么主题，就要对应的在main.js中引入主题的css
// import 'codemirror/theme/idea.css'   //这个是你设置了什么主题，就要对应的在main.js中引入主题的css
// import 'codemirror/theme/material.css'   //这个是你设置了什么主题，就要对应的在main.js中引入主题的css
// import 'codemirror/theme/lucario.css'   //这个是你设置了什么主题，就要对应的在main.js中引入主题的css
// import 'codemirror/theme/mbo.css'   //这个是你设置了什么主题，就要对应的在main.js中引入主题的css
// import 'codemirror/theme/eclipse.css'   //这个是你设置了什么主题，就要对应的在main.js中引入主题的css
// import 'codemirror/theme/rubyblue.css'   //这个是你设置了什么主题，就要对应的在main.js中引入主题的css
Vue.use(codemirror)

// 引入echarts，由于全局引入会将所有的echarts图表打包，导致体积过大，不建议这种方式，在vue文件中按需引用
import echarts from 'echarts'
Vue.prototype.$echarts = echarts

//设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = false

//可根据env来判断是否是测试还是正式，development为测试，其它为正式
const env = process.env.NODE_ENV

import {MESSAGE_ADD} from '@/store/module/msg'

new Vue({
    router,
    store,
    render: h => h(App),
    data:{
        config: {
            theme: {
                topBackgroundColor: '#047ee9', //顶部背景色
                topTextColor: '#ffffff',   //颜色
                menuBackgroundColor: '#ffffff', //左侧菜单背景色
                menuTextColor: 'theme-dark', //左侧菜单文字颜色
            },  //风格
        },
        system: {
            description: packageUtil.description,
            version: packageUtil.version,
            copyright: packageUtil.copyright.replace("{{year}}",new Date().getFullYear()+""),
            companyName: packageUtil.companyName,
        },

        page:{
            paginations: [20, 50, 100, 200],
            defaultLimit: 50,
        },

        permissions:[], //权限
        websocket: null,    //websocket
        websocketURL: "ws://" + document.domain + ':8001' + "/websocket.ws",
        user:null,  //登录后的用户信息
    },
    mounted(){
        if(!this.initRouter()) {   //初始化路由
            return
        }
        this.initPermissions()  //初始化权限
        this.initTheme()    //初始化风格
        this.initUser()    //初始化风格

        this.createWebSocket() //调用起动websocket
    },
    methods:{
        isEnvironment(){
            //判断运行环境,是否测试
            return env=='development'?true:false
        },
        getToken(){
            //有些地方可能需要读取token
            return window.localStorage.getItem("token")?window.localStorage.getItem("token"):''
        },
        getHeaderObj(header){
            //有些地方请求单独处理需要头信息，这里直接封装一下需要的头
            if(!header) header = {}

            header.token = this.getToken()
            return header
        },
        initRouter(){
            if(router.options.routes[1].children.length<=0){
                this.$router.push({
                    name: 'login'
                })
                return false
            }

            return true
        },
        initUser(){
            //用户
            this.user = JSON.parse(window.localStorage.getItem("user"))
        },
        initPermissions(){
            //权限
            this.permissions = JSON.parse(window.localStorage.getItem("permissions"))
        },
        //判断是否有权限，如果有返回true，如果没有则返回false
        isPermission(code){
            if(!code){
                return false
            }
            if(!this.permissions){
                return false
            }
            if(this.permissions.length<=0){
                return false
            }

            let bol = false
            for(let i=0;i<this.permissions.length;i++){
                if(this.permissions[i]===code){
                    bol = true
                    break
                }
            }
            return bol
        },
        //判断多个，只要有一个就为true
        isPermissions(...codes){
            for(let i=0;i<codes.length;i++){
                if(this.isPermission(codes[i])){
                    return true
                }
            }
            return false
        },

        //初始化风格
        initTheme(){
            if(window.localStorage.getItem("theme")==null){
                //设置默认值
                this.config.theme = {
                    topBackgroundColor: '#047ee9', //顶部背景色
                    topTextColor: '#ffffff',   //颜色
                    menuBackgroundColor: '#ffffff', //左侧菜单背景色
                    menuTextColor: 'theme-dark', //左侧菜单文字颜色
                }
                return
            }
            this.config.theme = JSON.parse(window.localStorage.getItem("theme"))
        },

        createWebSocket(){
            //创建websocket
            if(this.websocket!=null)return

            //如果未登录则什么也不处理
            if(this.$isEmpty(this.getToken()))return

            // this.websocket = new WSocket(this.$root, this.websocketURL + '/' + this.getToken(), msg => {
            this.websocket = new WSocket(this.$root, this.websocketURL, this.getToken(), msg => {
                this.$Notice.info({
                    title: msg.result.title,
                    desc: msg.result.content,
                    duration: 5,
                })

                //判断是否有重复的，如果有则不处理，如果没有则再添加
                var bol = false
                for(var i=0;i<this.$store.state.msg.messages.length;i++){
                    if(this.$store.state.msg.messages[i].id==msg.result.id){
                        bol = true
                    }
                }
                if(!bol) {
                    this.$store.dispatch(MESSAGE_ADD, msg.result)
                }
            }, ()=>{
                console.log('开启了WebSocket连接')
            }, ()=>{
                console.log('关闭了WebSocket连接')
            }).connection()
        },
        logout(){
            //清空所有缓存数据
            window.localStorage.clear()
            window.localStorage.setItem("theme", JSON.stringify(this.config.theme))

            //退出websocket
            if(this.websocket){
                this.websocket.closeConnection()
            }
        },

        //检查上传是否成功还是失败
        checkUpload(data,showErr = true){
            if(data.code==999){
                this.$message({
                    type: 'error',
                    duration: 30000,
                    showClose: true,
                    message: data.msg
                })
                return false
            }

            return true
        },
    },
}).$mount('#app')



/////增加全局属性，数据删除
Array.prototype.remove = function(from, to) {
    var rest = this.slice((to || from) + 1 || this.length)
    this.length = from < 0 ? this.length + from : from
    return this.push.apply(this, rest)
}
