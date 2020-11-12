package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.MenuService;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.model.MenuMapper;
import com.iaiai.cobra.repository.pojo.MenuPojo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2019/12/4 4:20 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    public int edit(Menu menu){
        return getBaseMapper().updateById(menu);
    }

    public List<Menu> queryByParent(String parentId){
        return getBaseMapper().queryByParent(parentId);
    }

    public List<MenuPojo> queryAll(){
        return getBaseMapper().queryAll();
    }

    public List<Menu> queryMenuByParent(String parentId){
        return getBaseMapper().queryMenuByParent(parentId);
    }

    public Menu load(String id){
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId, id);
        return getBaseMapper().selectOne(queryWrapper);
    }

    public Menu queryByCode(String code){
        return getBaseMapper().queryByCode(code);
    }

    public int del(String id){
        Menu menu = new Menu();
        menu.setId(id);
        menu.setModifyTime(new Date());
        menu.setDel(Delete.YES.getValue());
        return getBaseMapper().updateById(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dels(String[] ids){
        for (int i = 0; i < ids.length; i++) {
            Menu menu = new Menu();
            menu.setId(ids[i]);
            menu.setModifyTime(new Date());
            menu.setDel(Delete.YES.getValue());
            getBaseMapper().updateById(menu);
        }
    }

    public List<Menu> queryByRole(String roleId){
        return getBaseMapper().queryByRole(roleId);
    }

    public List<Menu> queryShowByRole(String roleId){
        return getBaseMapper().queryShowByRole(roleId);
    }

}
