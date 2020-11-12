package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.pojo.MenuPojo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2019/12/4 4:20 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface MenuService extends IService<Menu> {

    //根据父级查询(为空则是顶层)
    List<Menu> queryByParent(String parentId);

    //查询所有，包含子，包含不显示的
    List<MenuPojo> queryAll();

    //只查询菜单
    List<Menu> queryMenuByParent(String parentId);

    int edit(Menu menu);

    Menu load(String id);

    Menu queryByCode(String code);

    int del(String id);

    void dels(String[] ids);

    //根据角色查询出菜单
    List<Menu> queryByRole(String roleId);

    //根据角色查询出显示菜单
    List<Menu> queryShowByRole(String roleId);

}
