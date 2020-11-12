package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.PermissionService;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.beans.Permission;
import com.iaiai.cobra.repository.model.PermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2019/12/11 8:38 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    public List<Permission> queryByRole(String roleId){
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getRoleId, roleId);
        return getBaseMapper().selectList(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void settingPermission(String roleId,String[] menuIds){
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getRoleId, roleId);
        List<Permission> permissions = getBaseMapper().selectList(queryWrapper);

        List<Permission> currentPermissions = new ArrayList<>();    //存储删除之后的数据
        //选判断里面没有的，如果没有则删除掉
        if(permissions!=null&&permissions.size()>0){
            for (int i = 0; i < permissions.size(); i++) {
                boolean bol = false;
                for (int j = 0; j < menuIds.length; j++) {
                    if(permissions.get(i).getMenuId().equals(menuIds[j])){
                        bol = true;
                        currentPermissions.add(permissions.get(i));
                        break;
                    }
                }
                if(!bol){
                    //无此权限删除
                    Permission permission = new Permission();
                    permission.setId(permissions.get(i).getId());
                    permission.setModifyTime(new Date());
                    permission.setDel(Delete.YES.getValue());
                    getBaseMapper().updateById(permission);
                }
            }
        }
        for (int i = 0; i < menuIds.length; i++) {
            boolean bol = false;
            for (int j = 0; j < currentPermissions.size(); j++) {
                if(currentPermissions.get(j).getMenuId().equals(menuIds[i])){
                    bol = true;
                    break;
                }
            }
            if(!bol){
                Permission permission = new Permission();
                permission.setId(StringUtil.get32UUID());
                permission.setMenuId(menuIds[i]);
                permission.setRoleId(roleId);
                permission.setDel(Delete.NO.getValue());
                permission.setCreateTime(new Date());
                permission.setModifyTime(permission.getCreateTime());
                getBaseMapper().insert(permission);
            }
        }
    }

}
