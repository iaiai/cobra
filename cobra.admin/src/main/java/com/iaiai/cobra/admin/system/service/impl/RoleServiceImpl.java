package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.RoleService;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Role;
import com.iaiai.cobra.repository.constants.RoleStatus;
import com.iaiai.cobra.repository.model.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2019/12/9 9:20 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    public List<Role> queryAll(){
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getDel, Delete.NO.getValue());
        queryWrapper.orderByAsc(Role::getSeq);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<Role> queryNormal(){
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getDel, Delete.NO.getValue());
        queryWrapper.eq(Role::getStatus, RoleStatus.enable.getKey());
        queryWrapper.orderByAsc(Role::getSeq);
        return getBaseMapper().selectList(queryWrapper);
    }

    public int add(Role role){
        return getBaseMapper().insert(role);
    }

    public int edit(Role role){
        return getBaseMapper().updateById(role);
    }

    public Role load(String id){
        return getBaseMapper().selectById(id);
    }

    public int del(String id){
        Role role = new Role();
        role.setId(id);
        role.setModifyTime(new Date());
        role.setDel(Delete.YES.getValue());
        return getBaseMapper().updateById(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dels(String[] ids){
        for (int i = 0; i < ids.length; i++) {
            Role role = new Role();
            role.setId(ids[i]);
            role.setModifyTime(new Date());
            role.setDel(Delete.YES.getValue());
            getBaseMapper().updateById(role);
        }
    }

    public List<Role> queryByUser(String userId){
        return getBaseMapper().queryByUser(userId);
    }

}
