package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.LogService;
import com.iaiai.cobra.repository.beans.Log;
import com.iaiai.cobra.repository.model.LogMapper;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2019/12/24 4:09 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    public IPage<Log> queryAll(Page page, String startTime, String endTime, String search){
        return getBaseMapper().queryAll(page,startTime,endTime,search);
    }

}
