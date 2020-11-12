package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.Dept;
import com.iaiai.cobra.repository.pojo.DeptPojo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2019/12/13 9:06 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface DeptService extends IService<Dept> {

    //包含子，禁用也查
    List<Dept> queryAll();

    //查询子
    List<Dept> queryByChilder(String id);

    //查询用户的部门
    List<Dept> queryByUser(String userId);

    //查询子
    List<DeptPojo> queryByShow();

    int add(Dept dept);

    int edit(Dept dept);

    Dept load(String id);

    int del(String id);

    void dels(String[] ids);

}
