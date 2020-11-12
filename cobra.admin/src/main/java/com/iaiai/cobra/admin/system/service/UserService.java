package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.User;
import com.iaiai.cobra.repository.pojo.UserPojo;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2019/12/3 7:59 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface UserService extends IService<User> {

    //查询用户，密码也查出来
    User queryByUserAndPassword(String username);

    //查询用户不包含密码
    User queryByUser(String username);

    //添加用户
    void add(User user,String[] deptIds,String[] postIds,String[] roleIds);

    //编辑用户
    void edit(User user,String[] deptIds,String[] postIds,String[] roleIds);

    //编辑用户
    int edit(User user);

    //查询部门下用户，不包含子，如果为空则查全部
    IPage<UserPojo> queryByDept(Page page, String deptId, String search);

    User load(String id);

    int del(String id);

    void dels(String[] ids);

    //修改密码，password必须是加过密的
    int editPassword(String id,String password);

}
