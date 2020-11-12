package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.Permission;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2019/12/11 8:38 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryByRole(String roleId);

    //设置角色权限
    void settingPermission(String roleId,String[] menuIds);

}
