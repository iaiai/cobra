/** 这个文件只允许放表单验证方法 **/

//验证手机号
export function isPhone(rule, value, callback) {
    if (!value) {
        return callback()
    }

    var pattern = /^1[3456789]\d{9}$/
    if (pattern.test(value)) {
        return callback()
    }
    return callback(new Error('输入的手机号错误'))
}

// 验证是否整数
export function isInteger(rule, value, callback) {
    if (!value) {   //为空不验证
        return callback()
    }
    if (!Number(value)) {
        return callback(new Error('必须全部为数字'))
    } else {
        const re = /^[0-9]*[1-9][0-9]*$/
        const rsCheck = re.test(value)

        if (!rsCheck) {
            return callback(new Error('必须全部为数字'))
        } else {
            return callback()
        }
    }
}

export function dateNotNull(rule, value, callback) {
    if (!value) {
        return callback(new Error('日期不可以为空'))
    } else {
        return callback()
    }
}

export function timeNotNull(rule, value, callback) {
    if (!value) {
        return callback(new Error('时间不可以为空'))
    } else {
        return callback()
    }
}

//验证是否是邮箱
export function isEmail(rule, value, callback) {
    if (value) {
        var reg = new RegExp("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{1,})+$") //正则表达式

        if (!reg.test(value)) {
            return callback(new Error('邮箱输入错误'))
        } else {
            return callback()
        }
    } else {
        return callback()
    }
}

//验证密码
export function checkPasswordRule(rule, value, callback) {
    //如果是空不管，此方法不验证空值，只验证不为空时是否正确
    if (value) {
        if (value.length < 8) {
            return callback(new Error('密码必须大于等于8位'))
        }

        var REG_NUMBER = new RegExp(".*\\d+.*")
        var REG_UPPERCASE = new RegExp(".*[A-Z]+.*")
        var REG_LOWERCASE = new RegExp(".*[a-z]+.*")
        var REG_SYMBOL = new RegExp(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*")

        var i = 0
        if (REG_NUMBER.test(value)) i++;
        if (REG_UPPERCASE.test(value)) i++;
        if (REG_LOWERCASE.test(value)) i++;
        if (REG_SYMBOL.test(value)) i++;

        if (i  < 4 ) return callback(new Error('密码格式错误'))

        return callback()
    }
}
