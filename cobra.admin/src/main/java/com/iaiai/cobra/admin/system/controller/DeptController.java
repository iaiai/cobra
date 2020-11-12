package com.iaiai.cobra.admin.system.controller;

import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.DeptParams;
import com.iaiai.cobra.admin.system.service.DeptService;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Dept;
import com.iaiai.cobra.repository.pojo.DeptPojo;
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
 * Create Time: 2019/12/13 8:39 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @Permission(code = "systemDeptIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(){
        List<Dept> list = deptService.queryAll();
        return success(list);
    }

    @Permission(code = "systemDeptIndex")
    @PostMapping(value = "/list.json")
    public ResultVo list(@RequestBody DeptParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        List<Dept> list = deptService.queryByChilder(params.getId());
        return success(list);
    }

    //查询所有显示的，连子一块显示
    @Permission(code = "system:dept:query")
    @PostMapping(value = "/query.json")
    public ResultVo query(){
        List<DeptPojo> list = deptService.queryByShow();
        return success(list);
    }

    @Permission(code = "system:dept:add")
    @PostMapping(value = "/add.do")
    public ResultVo add(@RequestBody DeptParams params){
        if(params==null
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Dept dept = new Dept();
        dept.setId(StringUtil.get32UUID());
        dept.setName(params.getName());
        dept.setRemark(params.getRemark());
        dept.setSeq(params.getSeq());
        dept.setStatus(params.getStatus());
        dept.setLeader(params.getLeader());
        dept.setPhone(params.getPhone());
        dept.setEmail(params.getEmail());
        dept.setParentId(params.getParentId());
        dept.setDel(Delete.NO.getValue());
        dept.setCreateTime(new Date());
        dept.setModifyTime(dept.getCreateTime());
        deptService.add(dept);

        return success(dept);
    }

    @Permission(code = "system:dept:edit")
    @PostMapping(value = "/edit.do")
    public ResultVo edit(@RequestBody DeptParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Dept post = deptService.load(params.getId());
        post.setName(params.getName());
        post.setRemark(params.getRemark());
        post.setSeq(params.getSeq());
        post.setStatus(params.getStatus());
        post.setLeader(params.getLeader());
        post.setPhone(params.getPhone());
        post.setEmail(params.getEmail());
        post.setModifyTime(new Date());
        deptService.edit(post);

        return success(post);
    }

    @Permission(code = "system:dept:del")
    @PostMapping(value = "/del.do")
    public ResultVo del(@RequestBody DeptParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        deptService.del(params.getId());

        return success();
    }

    @Permission(code = "system:dept:del")
    @PostMapping(value = "/dels.do")
    public ResultVo dels(@RequestBody DeptParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        String[] ids = params.getId().split(",");
        deptService.dels(ids);

        return success();
    }

    @Permission(code = "system:dept:edit:status")
    @PostMapping(value = "/edit/status.do")
    public ResultVo editStatus(@RequestBody DeptParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Dept dept = deptService.load(params.getId());
        dept.setStatus(params.getStatus());
        dept.setModifyTime(new Date());
        deptService.edit(dept);

        return success();
    }

}
