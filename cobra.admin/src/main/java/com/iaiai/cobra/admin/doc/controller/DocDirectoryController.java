package com.iaiai.cobra.admin.doc.controller;

import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.doc.service.DocDirectoryService;
import com.iaiai.cobra.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.doc.controller
 * Author: iaiai
 * Create Time: 2020/5/25 9:48 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/doc/directory")
public class DocDirectoryController extends BaseController {

    @Autowired
    private DocDirectoryService docDirectoryService;

    @PostMapping(value = "/index.json")
    public ResultVo index(){
        return success();
    }

}
