import AsInst from './axios'
import methodMap from './api'

class Http {
}

Http.install = function (Vue) {
    /**
     * 全局请求接口
     * @param method 方法
     * @param data 参数
     * @param loading 是否提示
     */
    Vue.prototype.$http = function (method, data, loading = true) {
        let m = methodMap[method]
        if (m) {
            if (data) {
                let optsType = typeof (data)
                if (optsType === null || optsType !== 'object') {
                    data = {}
                }
            }
            if (typeof m.method === 'undefined') {
                console.log('method 错误', '缺少请求 method 方法', '\n')
                return false
            }

            if (m.method.toLowerCase() === 'get') {
                return Vue.prototype.$get(m.url, data, loading)
            } else if (m.method.toLowerCase() === 'post') {
                return Vue.prototype.$post(m.url, data, loading)
            } else {
                return false
            }
        } else {
            console.log('url 错误', '返回结果：err = ', '无法请求，无效的请求！', '\n')
        }
    }

    /**
     * POST 请求 无提示
     * @param url 请求URL
     * @param data 请求数据
     * @param loading 是否显示 modal
     * @returns {Promise}
     */
    Vue.prototype.$post = function (url, data, loading = true) {
        if (loading && typeof (loading) === 'boolean') {
            this.$Spin.show({
                render: (h) => {
                    return h('div', [
                        h('Icon', {
                            'class': 'spin-icon-load',
                            props: {
                                type: 'ios-loading',
                                size: 18
                            }
                        }),
                        h('div', 'Loading')
                    ])
                }
            })
        }

        return new Promise((resolve, reject) => {
            AsInst.post(url, data, {
                emulateJSON: true,
                headers: {
                    token: window.localStorage.getItem("token") ? window.localStorage.getItem("token") : '',
                },
            }).then((response) => {
                if (loading && typeof (loading) === 'boolean') {
                    this.$Spin.hide()
                }

                if (response.data.code == 999) {
                    this.$Message.error({
                        background: true,
                        duration: 30,
                        closable: true,
                        content: response.data.msg
                    })
                    console.log('Customize Notice', response.data)
                    return
                }
                resolve(response.data)
            }).catch((error) => {
                if (loading && typeof (loading) === 'boolean') {
                    this.$Spin.hide()
                }

                if (error.response != null) {
                    if (error.response.status === 200) {
                        if (!error.data) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: '接口输出异常...'
                            })
                            console.log('Customize Notice', error)
                        }
                    } else if (error.response.status === 500) {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '后端服务请求500错误,如一直出现错误,请联系我们'
                        })
                        console.log('Customize Notice', error)
                    } else {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '服务请求出错: ' + error.response.status
                        })
                        console.log('Customize Notice', error)
                    }
                } else {
                    if (error.status == 200) {
                        if (error.data.code == 999) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: error.data.msg
                            })
                            console.log('Customize Notice', error)
                        }
                    }
                }
                reject(error)
            })
        })
    }

    /**
     * GET 请求 无提示
     * @param url 请求URL
     * @param data 请求数据
     * @returns {Promise}
     */
    Vue.prototype.$get = function (url, data, loading = true) {
        if (loading && typeof (loading) === 'boolean') {
            this.$Spin.show({
                render: (h) => {
                    return h('div', [
                        h('Icon', {
                            'class': 'spin-icon-load',
                            props: {
                                type: 'ios-loading',
                                size: 18
                            }
                        }),
                        h('div', 'Loading')
                    ])
                }
            })
        }

        return new Promise((resolve, reject) => {
            AsInst.get(url, {
                params: data
            }, {
                emulateJSON: true,
                headers: {
                    token: window.localStorage.getItem("token") ? window.localStorage.getItem("token") : '',
                },
            }).then((response) => {
                if (loading && typeof (loading) === 'boolean') {
                    this.$Spin.hide()
                }

                resolve(response.data)
            }).catch((error) => {
                if (loading && typeof (loading) === 'boolean') {
                    this.$Spin.hide()
                }

                if (error.response != null) {
                    if (error.status === 200) {
                        if (!error.data) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: '接口输出异常...'
                            })
                            console.log('Customize Notice', error)
                        } else if (error.data && error.data.code === 999) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: error.data.msg
                            })
                            console.log('Customize Notice', error)
                        }
                    } else if (error.status === 500) {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '后端服务请求500错误,如一直出现错误,请联系我们'
                        })
                        console.log('Customize Notice', error)
                    } else {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '服务请求出错: ' + error.message
                        })
                        console.log('Customize Notice', error)
                    }
                } else {
                    this.$Message.error({
                        background: true,
                        duration: 30,
                        closable: true,
                        content: '服务请求出错: ' + error.message
                    })
                    console.log('Customize Notice', error)
                }
                reject(error)
            })
        })
    }

    /**
     * POST 请求 无提示
     * @param url 请求URL
     * @param data 请求数据
     * @param loading 是否显示 modal
     * @returns {Promise}
     */
    Vue.prototype.$download = function (url, data, loading = true) {
        if (loading && typeof (loading) === 'boolean') {
            this.$Spin.show({
                render: (h) => {
                    return h('div', [
                        h('Icon', {
                            'class': 'spin-icon-load',
                            props: {
                                type: 'ios-loading',
                                size: 18
                            }
                        }),
                        h('div', 'Loading')
                    ])
                }
            })
        }

        return new Promise((resolve, reject) => {
            AsInst.post(url, data, {
                emulateJSON: true,
                responseType: 'arraybuffer',
                headers: {
                    token: window.localStorage.getItem("token") ? window.localStorage.getItem("token") : '',
                },
            }).then((response) => {
                if (loading && typeof (loading) === 'boolean') {
                    this.$Spin.hide()
                }
                resolve(response)
            }).catch((error) => {
                reject(error)
            })
        })
    }

    /**
     * 全局请求接口
     * @param method 方法
     * @param data 参数
     * @param loading 是否提示
     */
    Vue.prototype.$url = function (url, data) {
        return new Promise((resolve, reject) => {
            AsInst.get(url, {
                params: data
            }, {
                emulateJSON: true,
                headers: {
                    token: window.localStorage.getItem("token") ? window.localStorage.getItem("token") : '',
                },
            }).then((response) => {
                resolve(response.data)
            }).catch((error) => {
                if (error.response != null) {
                    if (error.status === 200) {
                        if (!error.data) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: '接口输出异常...'
                            })
                            console.log('Customize Notice', error)
                        } else if (error.data && error.data.code === 999) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: error.data.msg
                            })
                            console.log('Customize Notice', error)
                        }
                    } else if (error.status === 500) {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '后端服务请求500错误,如一直出现错误,请联系我们'
                        })
                        console.log('Customize Notice', error)
                    } else {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '服务请求出错: ' + error.message
                        })
                        console.log('Customize Notice', error)
                    }
                } else {
                    this.$Message.error({
                        background: true,
                        duration: 30,
                        closable: true,
                        content: '服务请求出错: ' + error.message
                    })
                    console.log('Customize Notice', error)
                }
                reject(error)
            })
        })
    }

    /**
     * 上传图片
     * @param method 方法
     * @param opts 参数
     * @param toast 是否提示
     * @returns {string}
     */
    Vue.prototype.$uploadFile = function (url, formdata) {
        return new Promise((resolve, reject) => {
            AsInst.post(url, formdata, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    token: window.localStorage.getItem("token") ? window.localStorage.getItem("token") : '',
                },
                emulateJSON: true,
            }).then(res => {
                if (res.data.code == 999) {
                    this.$Message.error({
                        background: true,
                        duration: 30,
                        closable: true,
                        content: res.data.msg
                    })
                    console.log('Customize Notice', res.data)
                }
                resolve(res)
            }).catch(error => {
                if (error.data != null) {
                    if (error.status === 200) {
                        if (!error.data) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: '接口输出异常...'
                            })
                            console.log('Customize Notice', error)
                            reject('接口输出异常...')
                        } else if (error.data && error.data.code === 999) {
                            this.$Message.error({
                                background: true,
                                duration: 30,
                                closable: true,
                                content: error.data.msg
                            })
                            console.log('Customize Notice', error)
                            reject(error.data.msg)
                        }
                    } else if (error.status === 500) {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '后端服务请求500错误,如一直出现错误,请联系我们'
                        })
                        console.log('Customize Notice', error)
                        reject('后端服务请求500错误,如一直出现错误,请联系我们')
                    } else {
                        this.$Message.error({
                            background: true,
                            duration: 30,
                            closable: true,
                            content: '服务请求出错: ' + error.message
                        })
                        console.log('Customize Notice', error)
                        reject(error.message)
                    }
                } else {
                    this.$Message.error({
                        background: true,
                        duration: 30,
                        closable: true,
                        content: '服务请求出错: ' + error.message
                    })
                    console.log('Customize Notice', error)
                    reject(error.message)
                }
            })
        })
    }
}

export default Http
