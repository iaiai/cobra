package com.iaiai.cobra.repository.constants;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.constants
 * Author: iaiai
 * Create Time: 2019/12/20 9:35 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public enum MenuType {

    directory(1,"目录"),
    menu(2,"菜单"),
    function(3,"功能"),
    childPage(4,"子页面"),
    ;

    private int key;

    private String value;

    MenuType(int key,String value) {
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
