package com.iaiai.cobra.admin.system.controller.vo;

import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.beans.User;
import com.iaiai.cobra.repository.pojo.MenuPojo;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.vo
 * Author: iaiai
 * Create Time: 2019/12/26 10:03 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class OnlineVo {

    private String sessionId;

    private String username;    //登录名

    private String nickname;    //姓名

    private String ip;  //ip地址

    private String ipAddress;   //ip反查地址

    private String url; //访问的接口

    private String loginTime;   //登录时间

    private String lastTime;  //最后访问时间

    private String userAgent;  //ua

    private Set<String> permissions;    //权限

    private List<Menu> menu;    //菜单(包含隐藏的)

    private List<MenuPojo> treeMenu;    //菜单(不包含隐藏的)

    private User user;    //当前这个对象

    private String token;

}
