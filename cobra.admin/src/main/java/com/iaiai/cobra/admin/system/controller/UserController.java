package com.iaiai.cobra.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.UserPageParams;
import com.iaiai.cobra.admin.system.controller.params.UserParams;
import com.iaiai.cobra.admin.system.controller.vo.UserVo;
import com.iaiai.cobra.admin.system.service.DeptService;
import com.iaiai.cobra.admin.system.service.PostService;
import com.iaiai.cobra.admin.system.service.RoleService;
import com.iaiai.cobra.admin.system.service.UserService;
import com.iaiai.cobra.common.util.ExcelFormatUtil;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Dept;
import com.iaiai.cobra.repository.beans.Post;
import com.iaiai.cobra.repository.beans.Role;
import com.iaiai.cobra.repository.beans.User;
import com.iaiai.cobra.repository.pojo.UserPojo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller
 * Author: iaiai
 * Create Time: 2019/12/3 5:16 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected DeptService deptService;

    @Autowired
    protected PostService postService;

    @Autowired
    protected RoleService roleService;

    @Permission(code = "systemUserIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(@RequestBody UserPageParams params){
        if(params==null)params = new UserPageParams();
        if(params.getPage()==null||params.getPage()<=0){
            params.setPage(1);
        }
        if(params.getLimit()==null||params.getLimit()<=0){
            params.setLimit(defaultPageSize);
        }

        Page _page = new Page<>(params.getPage(), params.getLimit());
        IPage<UserPojo> userPojos = userService.queryByDept(_page,params.getDeptId(),params.getSearch());
        return success(userPojos);
    }

    @Permission(code = "system:user:add")
    @PostMapping(value = "/add.do")
    public ResultVo add(@RequestBody UserParams params){
        //参数验证
        if(params==null
                || StringUtils.isEmpty(params.getUsername())
                || StringUtils.isEmpty(params.getNickname())
                || StringUtils.isEmpty(params.getPassword())
                || params.getSex()==null
                || StringUtils.isEmpty(params.getDeptId())
                || StringUtils.isEmpty(params.getPostIds())
                || StringUtils.isEmpty(params.getRoleIds())
                || params.getStatus()==null){
            return fail("参数异常");
        }

        //判断是否有此用户，登录名必须全局唯一
        User user = userService.queryByUser(params.getUsername());
        if(user!=null){
            return fail("此登录名已经存在，请更换登录名");
        }

        String[] deptIds = params.getDeptId().split(",");
        String[] postIds = params.getPostIds().split(",");
        String[] roleIds = params.getRoleIds().split(",");

        user = new User();
        user.setId(StringUtil.get32UUID());
        user.setUsername(params.getUsername());
        user.setNickname(params.getNickname());
        user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(params.getPassword())));
        user.setPhone(params.getPhone());
        user.setEmail(params.getEmail());
        user.setRemark(params.getRemark());
        user.setSex(params.getSex());
        user.setFace(params.getFace());
        user.setDel(Delete.NO.getValue());
        user.setStatus(params.getStatus());
        user.setCreateTime(new Date());
        user.setModifyTime(user.getCreateTime());
        userService.add(user,deptIds,postIds,roleIds);

        return success();
    }

    @Permission(code = "system:user:edit")
    @PostMapping(value = "/edit.do")
    public ResultVo edit(@RequestBody UserParams params){
        //参数验证
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || StringUtils.isEmpty(params.getNickname())
                || params.getSex()==null
                || StringUtils.isEmpty(params.getDeptId())
                || StringUtils.isEmpty(params.getPostIds())
                || StringUtils.isEmpty(params.getRoleIds())
                || params.getStatus()==null){
            return fail("参数异常");
        }

        String[] deptIds = params.getDeptId().split(",");
        String[] postIds = params.getPostIds().split(",");
        String[] roleIds = params.getRoleIds().split(",");

        //判断是否有此用户，登录名必须全局唯一
        User user = userService.load(params.getId());
        user.setNickname(params.getNickname());
        user.setPhone(params.getPhone());
        user.setEmail(params.getEmail());
        user.setRemark(params.getRemark());
        user.setSex(params.getSex());
        user.setFace(params.getFace());
        user.setStatus(params.getStatus());
        userService.edit(user,deptIds,postIds,roleIds);

        return success();
    }

    @Permission(code = "system:user:edit:status")
    @PostMapping(value = "/edit/status.do")
    public ResultVo editStatus(@RequestBody UserParams params){
        //参数验证
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getStatus()==null){
            return fail("参数异常");
        }

        //判断是否有此用户，登录名必须全局唯一
        User user = userService.load(params.getId());
        user.setStatus(params.getStatus());
        userService.edit(user);

        return success();
    }

    @Permission(code = "systemUserIndex")
    @PostMapping(value = "/info.json")
    public ResultVo info(@RequestBody UserParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        Map<String,Object> map = new HashMap<>();

        //部门
        List<Dept> depts = deptService.queryByUser(params.getId());
        map.put("depts",depts);

        //岗位
        List<Post> posts = postService.queryByUser(params.getId());
        map.put("posts",posts);

        //角色
        List<Role> roles = roleService.queryByUser(params.getId());
        map.put("roles",roles);

        return success(map);
    }

    @Permission(code = "system:user:del")
    @PostMapping(value = "/del.do")
    public ResultVo del(@RequestBody UserParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        userService.del(params.getId());
        return success();
    }

    @Permission(code = "system:user:del")
    @PostMapping(value = "/dels.do")
    public ResultVo dels(@RequestBody UserParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        String[] ids = params.getId().split(",");

        userService.dels(ids);
        return success();
    }

    @Permission(code = "system:user:edit:pwd")
    @PostMapping(value = "/edit/password.do")
    public ResultVo editPassword(@RequestBody UserParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || StringUtils.isEmpty(params.getPassword())){
            return fail("参数异常");
        }

        userService.editPassword(params.getId(),DigestUtils.md5Hex(DigestUtils.md5Hex(params.getPassword())));
        return success();
    }

    @SneakyThrows
    @Permission(code = "system:user:export")
    @PostMapping(value = "/export.do")
    public ResponseEntity<byte[]> export(@RequestBody UserPageParams params){
        if(params==null)params = new UserPageParams();
        if(params.getPage()==null||params.getPage()<=0){
            params.setPage(1);
        }
        if(params.getLimit()==null||params.getLimit()<=0){
            params.setLimit(999999999);
        }

        Page _page = new Page<>(params.getPage(), params.getLimit());
        IPage<UserPojo> userPojos = userService.queryByDept(_page,params.getDeptId(),params.getSearch());
        List<UserVo> vos = new ArrayList<>();

        userPojos.getRecords().forEach(userPojo -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userPojo,userVo);

            vos.add(userVo);
        });

        // 每一列字段名
        String[] fields = new String[] {
                "ID",
                "登录名",
                "昵称",
                "真实姓名",
                "性别",
                "邮箱",
                "状态",
                "手机号",
                "备注",
                "头像",
                "创建时间",
                "部门"
        };

        // 字段名所在表格的宽度
        int[] fieldsWidth = new int[] {
                10000,
                4000,
                3000,
                3000,
                2000,
                8000,
                2000,
                4000,
                5000,
                5000,
                6000,
                5000
        };
        String filename = "用户_"+DateFormatUtils.format(new Date(),"yyyy_MM_dd_HH_mm_ss")+".xls";

        return ExcelFormatUtil.export(vos,filename,fields,fieldsWidth,new ExcelFormatUtil.Callback(){
            @Override
            public void item(SXSSFRow row,CellStyle cellStyle, Object o) {
                UserVo user = (UserVo)o;

                int j = 0;
                SXSSFCell cell = row.createCell(j++);
                cell.setCellValue(user.getId()); // ID

                cell = row.createCell(j++);
                cell.setCellValue(user.getUsername()); // 登录名

                cell = row.createCell(j++);
                cell.setCellValue(user.getNickname()); // 昵称

                cell = row.createCell(j++);
                cell.setCellValue(user.getRealname()); // 真实姓名

                cell = row.createCell(j++);
                switch (user.getSex()){
                    case 0:
                        cell.setCellValue("未知"); // 性别
                        break;
                    case 1:
                        cell.setCellValue("男"); // 性别
                        break;
                    case 2:
                        cell.setCellValue("女"); // 性别
                        break;
                }

                cell = row.createCell(j++);
                cell.setCellValue(user.getEmail()); // 邮箱

                cell = row.createCell(j++);
                switch (user.getStatus()){
                    case 0:
                        cell.setCellValue("禁用"); // 状态
                        break;
                    case 1:
                        cell.setCellValue("正常"); // 状态
                        break;
                }

                cell = row.createCell(j++);
                cell.setCellValue(user.getPhone()); // 手机号

                cell = row.createCell(j++);
                cell.setCellValue(user.getRemark()); // 备注

                cell = row.createCell(j++);
                cell.setCellValue(user.getFace()); // 头像

                cell = row.createCell(j++);
                cell.setCellValue(DateFormatUtils.format(user.getCreateTime(),"yyyy-MM-dd HH:mm:ss")); // 创建时间

                cell = row.createCell(j++);
                cell.setCellValue(user.getDeptName()); // 部门
            }
        });
    }

}
