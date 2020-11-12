package com.iaiai.cobra.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.LogPageParams;
import com.iaiai.cobra.admin.system.service.LogService;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.repository.beans.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller
 * Author: iaiai
 * Create Time: 2019/12/24 9:55 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @PostMapping(value = "/index.json")
    public ResultVo index(@RequestBody LogPageParams params){
        if(params==null)params = new LogPageParams();
        if(params.getPage()==null||params.getPage()<=0){
            params.setPage(1);
        }
        if(params.getLimit()==null||params.getLimit()<=0){
            params.setLimit(defaultPageSize);
        }

        Page _page = new Page<>(params.getPage(), params.getLimit());
        IPage<Log> list = logService.queryAll(_page,params.getStartTime(),params.getEndTime(),params.getSearch());
        return success(list);
    }

}
