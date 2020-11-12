package com.iaiai.cobra.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.UploadFileParams;
import com.iaiai.cobra.admin.system.service.UploadFileService;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.repository.beans.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller
 * Author: iaiai
 * Create Time: 2020/1/22 2:42 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/uploadFile")
public class UploadFileController extends BaseController {

    @Autowired
    protected UploadFileService uploadFileService;

//    @Permission(code = "systemUploadFileIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(@RequestBody UploadFileParams params){
        if(params==null)params = new UploadFileParams();
        if(params.getPage()==null||params.getPage()<=0){
            params.setPage(1);
        }
        if(params.getLimit()==null||params.getLimit()<=0){
            params.setLimit(defaultPageSize);
        }

        Page _page = new Page<>(params.getPage(), params.getLimit());
        IPage<UploadFile> page = uploadFileService.queryAll(_page,params!=null?params.getSearch():null);
        return success(page);
    }

}
