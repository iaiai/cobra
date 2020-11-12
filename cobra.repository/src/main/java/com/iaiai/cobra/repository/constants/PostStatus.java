package com.iaiai.cobra.repository.constants;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.constants
 * Author: iaiai
 * Create Time: 2020/9/5 9:20 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum PostStatus {

    disable(0,"禁用"),
    enable(1,"启用"),
    ;

    private int key;

    private String value;

    PostStatus(int key, String value) {
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
