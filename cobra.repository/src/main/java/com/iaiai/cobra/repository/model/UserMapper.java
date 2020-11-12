package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.repository.beans.User;
import com.iaiai.cobra.repository.pojo.UserPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2019/12/3 1:15 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User load(@Param("id") String id);

    User queryByUserAndPassword(@Param("username") String username);

    User queryByUser(@Param("username") String username);

    IPage<UserPojo> queryByDept(Page page,
                                @Param("deptId") String deptId,
                                @Param("search") String search);

}
