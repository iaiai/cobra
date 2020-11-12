package com.iaiai.cobra.common.vo.constant;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.common.vo.constant
 * Author: iaiai
 * Create Time: 2020/11/1 4:25 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum YesOrNo {

    NO(0),
    YES(1);

    private int value;

    YesOrNo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
