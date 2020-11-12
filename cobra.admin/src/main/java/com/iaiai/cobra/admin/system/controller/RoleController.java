package com.iaiai.cobra.admin.system.controller;

import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.RoleParams;
import com.iaiai.cobra.admin.system.service.PermissionService;
import com.iaiai.cobra.admin.system.service.RoleService;
import com.iaiai.cobra.common.util.ExcelFormatUtil;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Permission;
import com.iaiai.cobra.repository.beans.Role;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
 * Create Time: 2019/12/9 9:10 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @com.iaiai.cobra.admin.annotation.Permission(code = "systemRoleIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(){
        List<Role> list = roleService.queryAll();
        return success(list);
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:query")
    @PostMapping(value = "/query.json")
    public ResultVo query(){
        List<Role> list = roleService.queryNormal();
        return success(list);
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:add")
    @PostMapping(value = "/add.do")
    public ResultVo add(@RequestBody RoleParams params){
        if(params==null
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Role role = new Role();
        role.setId(StringUtil.get32UUID());
        role.setName(params.getName());
        role.setRemark(params.getRemark());
        role.setSeq(params.getSeq());
        role.setStatus(params.getStatus());
        role.setDel(Delete.NO.getValue());
        role.setCreateTime(new Date());
        role.setModifyTime(role.getCreateTime());
        roleService.add(role);

        return success();
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:edit")
    @PostMapping(value = "/edit.do")
    public ResultVo edit(@RequestBody RoleParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Role role = roleService.load(params.getId());
        role.setName(params.getName());
        role.setRemark(params.getRemark());
        role.setSeq(params.getSeq());
        role.setStatus(params.getStatus());
        roleService.edit(role);

        return success();
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:edit:status")
    @PostMapping(value = "/edit/status.do")
    public ResultVo editStatus(@RequestBody RoleParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Role role = roleService.load(params.getId());
        role.setStatus(params.getStatus());
        roleService.edit(role);

        return success();
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:del")
    @PostMapping(value = "/del.do")
    public ResultVo del(@RequestBody RoleParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        roleService.del(params.getId());

        return success();
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:del")
    @PostMapping(value = "/dels.do")
    public ResultVo dels(@RequestBody RoleParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        String[] ids = params.getId().split(",");
        roleService.dels(ids);

        return success();
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:permission")
    @PostMapping(value = "/permission.json")
    public ResultVo permission(@RequestBody RoleParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        List<Permission> list = permissionService.queryByRole(params.getId());

        return success(list);
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:permission")
    @PostMapping(value = "/permission.do")
    public ResultVo permissionSetting(@RequestBody RoleParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        String[] ids = null;
        if(StringUtils.isNotEmpty(params.getMenus())) {
            ids = params.getMenus().split(",");
        }

        permissionService.settingPermission(params.getId(),ids);

        return success();
    }

    @com.iaiai.cobra.admin.annotation.Permission(code = "system:role:export")
    @SneakyThrows
    @PostMapping(value = "/export.do")
    public ResponseEntity<byte[]> export(){
        List<Role> list = roleService.queryAll();

        // 每一列字段名
        String[] fields = new String[] {
                "ID",
                "角色名",
                "备注",
                "状态",
                "创建时间",
        };

        // 字段名所在表格的宽度
        int[] fieldsWidth = new int[] {
                10000,
                4000,
                6000,
                3000,
                6000
        };
        String filename = "角色_"+ DateFormatUtils.format(new Date(),"yyyy_MM_dd_HH_mm_ss")+".xls";

        return ExcelFormatUtil.export(list,filename,fields,fieldsWidth,new ExcelFormatUtil.Callback(){
            @Override
            public void item(SXSSFRow row, CellStyle cellStyle, Object o) {
                Role role = (Role)o;

                int j = 0;
                SXSSFCell cell = row.createCell(j++);
                cell.setCellValue(role.getId()); // ID

                cell = row.createCell(j++);
                cell.setCellValue(role.getName()); // 角色名

                cell = row.createCell(j++);
                cell.setCellValue(role.getRemark()); // 备注

                cell = row.createCell(j++);
                switch (role.getStatus()){
                    case 0:
                        cell.setCellValue("禁用"); // 状态
                        break;
                    case 1:
                        cell.setCellValue("正常"); // 状态
                        break;
                }

                cell = row.createCell(j++);
                cell.setCellValue(DateFormatUtils.format(role.getCreateTime(),"yyyy-MM-dd HH:mm:ss")); // 创建时间
            }
        });
    }

}
