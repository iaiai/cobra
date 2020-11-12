import Main from '@/views/main'

export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'xxx系统'
    },
    component: resolve => require(['@/views/login'],resolve),    //懒加载的方式
}

export const page403 = {
    path: '/403',
    name: 'error-403',
    meta: {
        title: '403-权限不足'
    },
    component: resolve => require(['@/views/error-page/403.vue'],resolve),    //懒加载的方式
}

export const page404 = {
    path: '/404',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: resolve => require(['@/views/error-page/404.vue'],resolve),    //懒加载的方式
}

export const page500 = {
    path: '/500',
    name: 'error-500',
    meta: {
        title: '500-服务端错误'
    },
    component: resolve => require(['@/views/error-page/500.vue'],resolve),    //懒加载的方式
}

var appRouter = [{
    path: '/',
    name: '_index',
    redirect: '/home',
    component: Main,
    children:[],

    // children:[{
    //     path: '/home',
    //     name: 'home',
    //     meta: {
    //         title: '首页',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/home'],resolve),    //懒加载的方式
    // },{
    //     path: '/system/user/index',
    //     name: 'systemUserIndex',
    //     meta: {
    //         title: '用户管理',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/system/user/index'],resolve),    //懒加载的方式
    // },{
    //     path: '/system/menu/index',
    //     name: 'systemMenuIndex',
    //     meta: {
    //         title: '菜单管理',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/system/menu/index'],resolve),    //懒加载的方式
    // },{
    //     path: '/system/role/index',
    //     name: 'systemRoleIndex',
    //     meta: {
    //         title: '角色管理',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/system/role/index'],resolve),    //懒加载的方式
    // },{
    //     path: '/system/post/index',
    //     name: 'systemPostIndex',
    //     meta: {
    //         title: '岗位管理',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/system/post/index'],resolve),    //懒加载的方式
    // },{
    //     path: '/system/dept/index',
    //     name: 'systemDeptIndex',
    //     meta: {
    //         title: '部门管理',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/system/dept/index'],resolve),    //懒加载的方式
    // },{
    //     path: '/component/froala/index',
    //     name: 'componentFroalaIndex',
    //     meta: {
    //         title: 'Froala大文本编辑器',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/components/froala/index'],resolve),    //懒加载的方式
    // },{
    //     path: '/component/neditor/index',
    //     name: 'componentNeditorIndex',
    //     meta: {
    //         title: 'neditor大文本编辑器',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/components/neditor/index'],resolve),    //懒加载的方式
    // },{
    //     path: '/component/wangeditor/index',
    //     name: 'componentWangeditorIndex',
    //     meta: {
    //         title: 'wangeditor大文本编辑器',
    //         notCache: true, //从字面意义上很容易误解为不缓存，但是no-cache代表不缓存过期的资源
    //     },
    //     component: resolve => require(['@/views/components/wangeditor/index'],resolve),    //懒加载的方式
    // }],
}]

//动态路由
let menus = JSON.parse(window.localStorage.getItem("menus"))
if(menus){
    //如果不为空则动态添加路由
    for(let i=0;i<menus.length;i++) {
        if(menus[i].url) {
            appRouter[0].children.push({
                path: menus[i].url,
                name: menus[i].code,
                meta: {
                    title: menus[i].name,
                    notCache: true,
                },
                component: resolve => require(['@/views' + menus[i].filePath], resolve),    //懒加载的方式
            })
        }
    }
}

export {appRouter}

export const routers = [
    loginRouter,
    ...appRouter,
    page403,
    page500,
    page404,
]