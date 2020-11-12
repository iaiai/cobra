package com.iaiai.cobra.admin.system.controller;

import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.service.ConfigService;
import com.iaiai.cobra.admin.system.service.DeptService;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.repository.beans.Config;
import com.iaiai.cobra.repository.beans.Dept;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller
 * Author: iaiai
 * Create Time: 2020/11/5 下午2:21
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/config")
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;

    @PostMapping(value = "/index.json")
    public ResultVo index(){
        return success(configService.queryAll());
    }

    @PostMapping(value = "/setting.do")
    public ResultVo setting(@RequestBody Config config){
        if(config==null
                || StringUtils.isEmpty(config.getId())
                || StringUtils.isEmpty(config.getKey())
                || StringUtils.isEmpty(config.getValue())){
            return fail("参数异常");
        }

        configService.edit(config);

        return success();
    }

}
