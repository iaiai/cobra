package com.iaiai.cobra.repository.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.beans
 * Author: iaiai
 * Create Time: 2019/12/3 1:09 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private String id;

    private String username;    //登录名

    private String password;    //密码

    private String nickname;    //昵称

    private String realname;    //真实姓名(暂时无用)

    private Integer sex;    //性别

    private String email;   //邮箱

    private Integer status; //状态,1正常，0禁用

    private String phone;   //手机号

    private String remark;  //备注

    private String face;    //头像

    private Integer del;    //是否删除，0未删除，1已删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
