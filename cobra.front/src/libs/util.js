/** 此文件放一些全局方法 **/
import uuidv1 from 'uuid/v1'

class Util {
}

Util.install = function (Vue) {

    /** 日期过滤插件 **/
    Vue.prototype.$formatTimestamp = function (value, format = 'yyyy-MM-dd hh:mm:ss') {
        if (!value) return

        //防止PHPthis.wearerId后台输出为10位的时间戳
        if (value.length === 10) {
            value = value * 1000
        }
        let newFormat = ''
        if (format) {
            newFormat = format
        } else {
            newFormat = 'yyyy-MM-dd hh:mm:ss'
        }
        let time = new Date(parseInt(value))
        let date = {
            'M+': time.getMonth() + 1,
            'd+': time.getDate(),
            'h+': time.getHours(),
            'm+': time.getMinutes(),
            's+': time.getSeconds(),
            'q+': Math.floor((time.getMonth() + 3) / 3),
            'S+': time.getMilliseconds()
        }
        if (/(y+)/i.test(newFormat)) {
            newFormat = newFormat.replace(RegExp.$1, (time.getFullYear() + '').substr(4 - RegExp.$1.length))
        }
        for (let k in date) {
            if (new RegExp('(' + k + ')').test(newFormat)) {
                newFormat = newFormat.replace(RegExp.$1, RegExp.$1.length === 1 ? date[k] : ('00' + date[k]).substr(('' + date[k]).length))
            }
        }
        return newFormat
    }

    /** 日期过滤插件 **/
    Vue.prototype.$formatDateTime = function (value, format = 'yyyy-MM-dd hh:mm:ss') {
        if (!value) return

        let newFormat = ''
        if (format) {
            newFormat = format
        } else {
            newFormat = 'yyyy-MM-dd hh:mm:ss'
        }
        let date = {
            'M+': value.getMonth() + 1,
            'd+': value.getDate(),
            'h+': value.getHours(),
            'm+': value.getMinutes(),
            's+': value.getSeconds(),
            'q+': Math.floor((value.getMonth() + 3) / 3),
            'S+': value.getMilliseconds()
        }
        if (/(y+)/i.test(newFormat)) {
            newFormat = newFormat.replace(RegExp.$1, (value.getFullYear() + '').substr(4 - RegExp.$1.length))
        }
        for (let k in date) {
            if (new RegExp('(' + k + ')').test(newFormat)) {
                newFormat = newFormat.replace(RegExp.$1, RegExp.$1.length === 1 ? date[k] : ('00' + date[k]).substr(('' + date[k]).length))
            }
        }
        return newFormat
    }

    /** 替换所有 **/
    Vue.prototype.$replaceAll = function (str, s1, s2) {
        return str.replace(new RegExp(s1, "gm"), s2)
    }

    /** 替换所有 **/
    Vue.prototype.$showSize = function (size) {
        if (size < 1024) return size + ""
        else if (size < (1024 * 1024)) return parseInt(size / 1024) + " KB"
        else if (size < (1024 * 1024 * 1024)) return parseInt(size / 1024 / 1024) + " MB"
    }

    /** uuid **/
    Vue.prototype.$create32UUID = function () {
        return this.$replaceAll(uuidv1(), "-", "")
    }

    /** 下载文件 **/
    Vue.prototype.$downloadFile = function (res,filename) {
        //下载文件，服务器返回文件
        if ('download' in document.createElement('a')) { // 非IE下载
            let blob = new Blob([res.data])
            const elink = document.createElement('a')
            elink.download = filename
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click()
            URL.revokeObjectURL(elink.href) // 释放URL 对象
            document.body.removeChild(elink)
        } else { // IE10+下载
            navigator.msSaveBlob(blob, filename)
        }
    }

    /** 判断是否为空 **/
    Vue.prototype.$isEmpty = function (val) {
        if(val==null) return true
        if(val==undefined) return true
        if(val=='') return true
        if(val.length<=0) return true

        return false
    }

    /** 判断非空 **/
    Vue.prototype.$isNotEmpty = function (val) {
        return !this.$isEmpty(val)
    }

    //日期格式化
    Vue.filter('formatTimestamp', function (value, format = 'yyyy-MM-dd hh:mm:ss') {
        return Vue.prototype.$formatTimestamp(value, format)
    })

    Vue.filter('formatDateTime', function (value, format = 'yyyy-MM-dd hh:mm:ss') {
        return Vue.prototype.$formatDateTime(value, format)
    })

    Vue.filter('showSize', function (size) {
        return Vue.prototype.$showSize(size)
    })

    //格式金额
    Vue.filter('formatMoney', function (value,fractionDigits = 2) {
        if (value == null || value == '') {
            return '0.00'
        }
        return parseFloat(value / 100).toFixed(fractionDigits)
    })

    //格式bankCard
    Vue.filter('formatBankCard', function (value) {
        if(value==null)return

        let tmp = ""
        let len = value.length
        if (len > 4 && len <= 8) {
            tmp = value.replace(/^(\d{4})/g, '$1 ')
        } else if (len > 8 && len <= 12) {
            tmp = value.replace(/^(\d{4})(\d{4})/g, '$1 $2 ')
        } else if (len > 12 && len <= 16) {
            tmp = value.replace(/^(\d{4})(\d{4})(\d{4})/g, '$1 $2 $3 ')
        } else if (len > 16 && len <= 20) {
            tmp = value.replace(/^(\d{4})(\d{4})(\d{4})(\d{4})/g, '$1 $2 $3 $4 ')
        } else if (len > 20 && len <= 24) {
            tmp = value.replace(/^(\d{4})(\d{4})(\d{4})(\d{4})(\d{4})/g, '$1 $2 $3 $4 $5 ')
        } else if (len > 24 && len <= 28) {
            tmp = value.replace(/^(\d{4})(\d{4})(\d{4})(\d{4})(\d{4})(\d{4})/g, '$1 $2 $3 $4 $5 $6 ')
        }
        return tmp
    })

}

export default Util
