package com.iaiai.cobra.repository.constants;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.constants
 * Author: iaiai
 * Create Time: 2020/5/8 10:58 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum UserStatus {

    disable(0,"禁用"),
    enable(1,"启用"),
    ;

    private int key;

    private String value;

    UserStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue(){
        return value;
    }

}
