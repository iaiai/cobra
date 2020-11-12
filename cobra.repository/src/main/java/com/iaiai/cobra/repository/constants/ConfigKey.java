package com.iaiai.cobra.repository.constants;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.constants
 * Author: iaiai
 * Create Time: 2020/11/1 3:35 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum ConfigKey {

    websocketSingle("websocket-single","同一个用户是否只允许一个websock接收，1是，0可多个"),
    systemLoginSingle("system-login-single","同一个系统用户登录是否只允许一个，1是，0可多个"),
    ;

    private String key;

    private String remark;

    ConfigKey(String key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    public String getKey() {
        return key;
    }

    public String getRemark(){
        return remark;
    }

}
