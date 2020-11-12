package com.iaiai.cobra.admin.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.UserPageParams;
import com.iaiai.cobra.admin.web.controller.params.MsgParams;
import com.iaiai.cobra.admin.web.controller.params.WebSocketParams;
import com.iaiai.cobra.admin.web.controller.service.MsgService;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.YesOrNo;
import com.iaiai.cobra.repository.beans.Msg;
import com.iaiai.cobra.repository.pojo.UserPojo;
import lombok.extern.slf4j.Slf4j;
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
 * Create Time: 2020/11/1 8:35 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/msg")
public class MsgController extends BaseController {

    @Autowired
    private MsgService msgService;

    @PostMapping("/index.json")
    public ResultVo index(@RequestBody MsgParams params){
        if(params==null)params = new MsgParams();
        if(params.getPage()==null||params.getPage()<=0){
            params.setPage(1);
        }
        if(params.getLimit()==null||params.getLimit()<=0){
            params.setLimit(defaultPageSize);
        }
        Page _page = new Page<>(params.getPage(), params.getLimit());
        IPage<Msg> msgIPage = msgService.queryPage(_page,getUserId());

        return success(msgIPage);
    }

    @PostMapping("/detail.json")
    public ResultVo detail(@RequestBody MsgParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        Msg msg = msgService.getById(params.getId());

        if(msg.getRead()==YesOrNo.NO.getValue()) {
            //设置为已读状态
            msg.setRead(YesOrNo.YES.getValue());
            msg.setReadTime(new Date());
            msgService.updateById(msg);
        }

        return success(msg);
    }

    @PostMapping("/setting/read.do")
    public ResultVo read(){
        msgService.editReadAll(getUserId());
        return success();
    }

}
