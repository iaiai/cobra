package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaiai.cobra.repository.beans.UserDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2019/12/19 3:45 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface UserDeptMapper extends BaseMapper<UserDept> {

    //根据用户查询
    List<UserDept> queryByUser(@Param("userId") String userId);

    //删除用户的部门
    int delByUser(@Param("userId") String userId);

}
