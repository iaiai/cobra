package com.iaiai.cobra.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.UserParams;
import com.iaiai.cobra.admin.system.service.UserService;
import com.iaiai.cobra.admin.web.controller.params.MsgParams;
import com.iaiai.cobra.admin.web.controller.service.MsgService;
import com.iaiai.cobra.common.util.CheckPassword;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.repository.beans.Msg;
import com.iaiai.cobra.repository.beans.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller
 * Author: iaiai
 * Create Time: 2020/11/1 9:58 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/self")
public class SelfController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/detail.json")
    public ResultVo detail(){
        User user = userService.getById(getUserId());
        return success(user);
    }

    @PostMapping("/edit.do")
    public ResultVo edit(@RequestBody User user){
        if(user==null || StringUtils.isEmpty(user.getNickname())){
            return fail("参数异常");
        }

        user.setId(getUserId());
        user.setModifyTime(new Date());
        userService.updateById(user);

        return success();
    }

    @PostMapping("/edit/password.do")
    public ResultVo edit(@RequestBody UserParams params){
        if(params==null || StringUtils.isEmpty(params.getPassword()) || StringUtils.isEmpty(params.getNewPassword())){
            return fail("参数异常");
        }

        if(!CheckPassword.checkPasswordRule(params.getPassword())){
            return fail("密码格式错误");
        }

        if(!CheckPassword.checkPasswordRule(params.getNewPassword())){
            return fail("密码格式错误");
        }

        User user = userService.queryByUserAndPassword(getUser().getUsername());

        //判断密码
        if (!user.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex(params.getPassword())))) {
            return fail("密码错误");
        }

        userService.editPassword(user.getId(),DigestUtils.md5Hex(DigestUtils.md5Hex(params.getNewPassword())));

        return success();
    }

}
