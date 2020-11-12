package com.iaiai.cobra.admin.system.controller;

import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.service.MenuService;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.pojo.MenuPojo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller
 * Author: iaiai
 * Create Time: 2019/12/4 4:15 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Permission(code = "systemMenuIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(@RequestBody(required = false) Menu params){
        List<Menu> list = menuService.queryByParent(params!=null?params.getParentId():null);
        return success(list);
    }

    @Permission(code = "system:menu:add")
    @PostMapping(value = "/add.do")
    public ResultVo add(@RequestBody Menu params){
        if(params==null
                || params.getType()==null
                || StringUtils.isEmpty(params.getCode())
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getShow()==null){
            return fail("参数异常");
        }

        //检查编码是否有重复的
        Menu menu = menuService.queryByCode(params.getCode());
        if(menu!=null){
            return fail("有重复的编码，添加失败");
        }

        menu = new Menu();
        menu.setId(StringUtil.get32UUID());
        menu.setParentId(params.getParentId());
        menu.setType(params.getType());
        menu.setName(params.getName());
        menu.setCode(params.getCode());
        menu.setSeq(params.getSeq());
        menu.setShow(params.getShow());
        menu.setIcon(params.getIcon());
        menu.setUrl(params.getUrl());
        menu.setFilePath(params.getFilePath());
        menu.setCache(params.getCache());
        menu.setDel(Delete.NO.getValue());
        menu.setCreateTime(new Date());
        menu.setModifyTime(menu.getCreateTime());
        menu.setRemark(params.getRemark());
        menuService.save(menu);

        return success(menu);
    }

    @Permission(code = "system:menu:edit")
    @PostMapping(value = "/edit.do")
    public ResultVo edit(@RequestBody Menu params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getType()==null
                || StringUtils.isEmpty(params.getCode())
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getShow()==null){
            return fail("参数异常");
        }

        Menu menu = menuService.load(params.getId());
        menu.setType(params.getType());
        menu.setName(params.getName());
        menu.setCode(params.getCode());
        menu.setSeq(params.getSeq());
        menu.setShow(params.getShow());
        menu.setIcon(params.getIcon());
        menu.setUrl(params.getUrl());
        menu.setFilePath(params.getFilePath());
        menu.setCache(params.getCache());
        menu.setModifyTime(new Date());
        menu.setRemark(params.getRemark());
        menuService.edit(menu);

        return success();
    }

    @Permission(code = "system:menu:edit:status")
    @PostMapping(value = "/edit/show.do")
    public ResultVo editShow(@RequestBody Menu params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getShow()==null){
            return fail("参数异常");
        }

        Menu menu = menuService.load(params.getId());
        menu.setShow(params.getShow());
        menu.setModifyTime(new Date());
        menuService.edit(menu);

        return success();
    }

    @Permission(code = "system:menu:del")
    @PostMapping(value = "/del.do")
    public ResultVo del(@RequestBody Menu params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        menuService.del(params.getId());

        return success();
    }

    @Permission(code = "system:menu:del")
    @PostMapping(value = "/dels.do")
    public ResultVo dels(@RequestBody Menu params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        String[] ids = params.getId().split(",");
        menuService.dels(ids);

        return success();
    }

    //查询所有，包含子，包含不显示的
    @Permission(code = "systemMenuIndex")
    @PostMapping(value = "/all.json")
    public ResultVo all(){
        List<MenuPojo> list = menuService.queryAll();
        return success(list);
    }

}
