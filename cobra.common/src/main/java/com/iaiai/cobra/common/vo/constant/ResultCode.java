package com.iaiai.cobra.common.vo.constant;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xproject.xcommons.constants
 * Author: iaiai
 * Create Time: 2017/11/6 下午8:01
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum ResultCode {

    NO_PERMISSION(997),    //无权限
    LOGIN(998),    //超时需要重新登录
    FAIL(999),
    SUCCESS(1000),
    ;

    private int value;

    private ResultCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
