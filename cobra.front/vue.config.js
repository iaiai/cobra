const packageUtil = require('./package.json')
const path = require('path')

//当前目录
const resolve = dir => {
    return path.join(__dirname, dir)
}

module.exports = {
    // 基本路径
    publicPath: './',
    // 生产环境是否生成 sourceMap 文件
    productionSourceMap: false,

    // build时放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录
    assetsDir:'assets',

    // 服务器端口号
    devServer: {
        /**相关参数可参考 https://cloud.tencent.com/developer/section/1477376**/

        open: false,    //当open启用时，开发服务器将打开浏览器。
        port: 1235,
        https: false,
        hot:true,   //启用webpack的热模块更换功能
        hotOnly: false, //在生成失败的情况下，启用热模块替换（请参阅devServer.hot），而不刷新页面作为回退。
        proxy:{
            "/api": {
                target: "http://localhost:8001", // 域名
                ws: false, // 是否启用websockets
                changOrigin: true, //开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样服务端和服务端进行数据的交互就不会有跨域问题
                pathRewrite: {
                    '^/api' : '',     // rewrite path
                },
            }
        },
    },

    // eslint-loader 是否在保存的时候检查
    lintOnSave: false,

    chainWebpack: config => {
        config.resolve.alias.set('@', resolve('src')) // 定义了一个别名，但好像写不写都没什么用，直接就可以用@/views这个
    },

    css: {
        loaderOptions: {
            //css引入less
            less: {
                lessOptions:{
                    javascriptEnabled: true,
                }
            }
        }
    },

    configureWebpack: config => {
        config.resolve = {
            extensions: ['.js', '.vue', '.json',".css"],
            alias: {
                'vue$': 'vue/dist/vue.esm.js',
                '@': resolve('src'),
            }
        }
    },

    //vue-awesome
    transpileDependencies: [
        /\bvue-awesome\b/
    ],

    pages: {
        index: {
            entry: 'src/main.js',
            template: 'public/index.html',
            title: packageUtil.description,
        }
    },
}