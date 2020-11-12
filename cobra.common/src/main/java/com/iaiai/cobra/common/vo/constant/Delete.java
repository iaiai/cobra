package com.iaiai.cobra.common.vo.constant;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xproject.xcommons.constants
 * Author: iaiai
 * Create Time: 2017/11/6 下午7:52
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum Delete {
    NO(0),
    YES(1);

    private int value;

    Delete(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
