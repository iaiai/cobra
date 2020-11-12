package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaiai.cobra.repository.beans.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2019/12/11 8:36 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}
