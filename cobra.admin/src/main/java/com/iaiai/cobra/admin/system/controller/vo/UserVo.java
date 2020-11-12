package com.iaiai.cobra.admin.system.controller.vo;

import com.iaiai.cobra.repository.beans.Post;
import com.iaiai.cobra.repository.beans.Role;
import com.iaiai.cobra.repository.pojo.UserPojo;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.vo
 * Author: iaiai
 * Create Time: 2019/12/19 4:54 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class UserVo extends UserPojo {

    private List<Post> posts;   //岗位

    private List<Role> roles;   //角色

}
