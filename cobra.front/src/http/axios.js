import axios from 'axios'
import Qs from 'qs'
import {router} from '../router'

const config = {
    // baseURL: '/api',
    timeout: 60000, //60秒请求超时
    withCredentials: false, //是否允许跨域，允许携带cookie，因为地图需要反查，所以这里改为false
    headers: {
        // 'Content-Type': 'application/json;application/x-www-form-urlencoded;charset=utf-8',  //json的话必须要加，如果是form-data格式不能加这句
        'Content-Type': 'application/json;charset=utf-8',  //json的话必须要加，如果是form-data格式不能加这句
        'X-Requested-With': 'XMLHttpRequest',
    },
    transformRequest: [function (data) {
        if (data instanceof FormData) {
            //formData为上传图片时自己封装的提交对象
            return data
        }

        // 这里可以在发送请求之前对请求数据做处理，比如form-data格式化等，这里可以使用开头引入的Qs（这个模块在安装axios的时候就已经安装了，不需要另外安装）
        return JSON.stringify(data)
        // return Qs.stringify(data)
    }],
    //返回数据类型
    responseType: 'json'
}

const AsInst = axios.create(config)

//请求拦截器
AsInst.interceptors.request.use((config) => {
    return config
}, (err) => {
    return Promise.reject(err)
})

//响应拦截器
AsInst.interceptors.response.use(response => {
    if (response.headers["content-disposition"] != null && response.headers["content-disposition"].indexOf("attachment") == 0) {
        //文件下载
        return response
    }

    //检查数据是否返回NULL
    if (response.data === null) {
        return Promise.reject(response)
    }
    if (response.data.code === 999) {
        return Promise.reject(response)
    }
    //检查是否有权限
    if (response.data.code === 997) {
        router.push({
            name: 'error-403',
        })
        return Promise.reject(response)
    }
    //检查登陆信息是否还存在
    if (response.data.code === 998) {
        router.push({
            name: 'login',
        })
        return Promise.reject(response)
    }
    return response
}, (error) => {
    // 下面是接口回调的status ,因为我做了一些错误页面,所以都会指向对应的报错页面
    if (error.response != null && error.response.status === 404) {
        router.push({
            name: 'error-404'
        })
        return
    }
    //请求错误时做些事
    return Promise.reject(error)
})
export default AsInst
