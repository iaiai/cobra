package com.iaiai.cobra.admin.web.controller.params;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller.params
 * Author: iaiai
 * Create Time: 2019/12/3 5:23 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class LoginParams {

    private String username;    //登录名

    private String password;    //密码

    private String code;    //验证码

    private String accessCode;  //请求码

}
