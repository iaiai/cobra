package com.iaiai.cobra.admin.system.controller.params;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.params
 * Author: iaiai
 * Create Time: 2019/12/19 2:30 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class UserParams {

    private String id;

    private String username;    //登录名

    private String password;    //密码

    private String nickname;    //昵称

    private String phone;   //手机号

    private String email;   //邮箱

    private String remark;  //备注

    private Integer sex;    //性别

    private Integer status; //是否启用，0未启用，1启用

    private String deptId;  //部门，此是单选

    private String postIds; //岗位多选

    private String roleIds; //角色多选

    private String face;    //头像

    private String newPassword; //新密码

}
