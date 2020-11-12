package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2019/12/9 9:20 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface RoleService extends IService<Role> {

    //查询所有的
    List<Role> queryAll();

    //查询正常的
    List<Role> queryNormal();

    int add(Role role);

    int edit(Role role);

    Role load(String id);

    int del(String id);

    void dels(String[] ids);

    List<Role> queryByUser(String userId);

}
