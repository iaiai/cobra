package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.repository.beans.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2019/12/24 3:57 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface LogMapper extends BaseMapper<Log> {

    IPage<Log> queryAll(Page page,
                        @Param("startTime") String startTime,
                        @Param("endTime") String endTime,
                        @Param("search") String search);

}
