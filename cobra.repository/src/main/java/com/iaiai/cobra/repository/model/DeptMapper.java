package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaiai.cobra.repository.beans.Dept;
import com.iaiai.cobra.repository.pojo.DeptPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2019/12/13 9:05 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface DeptMapper extends BaseMapper<Dept> {

    List<Dept> queryAll();

    List<Dept> queryByChilder(@Param("id") String id);

    List<Dept> queryByUser(@Param("userId") String userId);

    //查询显示
    List<DeptPojo> queryByShow();

}
