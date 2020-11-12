package com.iaiai.cobra.common.vo.constant;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xproject.xcommons.core.constant
 * Author: iaiai
 * Create Time: 2018/9/21 12:44 PM
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum TerminalType {

    OTHER(0, "其它"),
    PC(1, "PC"),
    PHONE(2, "手机"),
    IPAD(3, "平板"),
    ;

    private int key;
    private String val;

    private TerminalType(int key, String val) {
        this.key = key;
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public int getKey() {
        return key;
    }

}
