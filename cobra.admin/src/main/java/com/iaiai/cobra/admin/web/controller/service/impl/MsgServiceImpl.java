package com.iaiai.cobra.admin.web.controller.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.web.controller.service.MsgService;
import com.iaiai.cobra.common.vo.constant.YesOrNo;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.beans.Msg;
import com.iaiai.cobra.repository.model.MsgMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller.service.impl
 * Author: iaiai
 * Create Time: 2020/10/26 2:10 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("msgService")
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {

    public List<Msg> queryNoRead(String userId){
        LambdaQueryWrapper<Msg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Msg::getUserId, userId);
        queryWrapper.eq(Msg::getRead, YesOrNo.NO.getValue());
        queryWrapper.orderByAsc(Msg::getCreateTime);
        return getBaseMapper().selectList(queryWrapper);
    }

    public IPage<Msg> queryPage(Page page,String userId){
        LambdaQueryWrapper<Msg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Msg::getUserId, userId);
        queryWrapper.orderByDesc(Msg::getCreateTime);
        return getBaseMapper().selectPage(page,queryWrapper);
    }

    public void editReadAll(String userId){
        getBaseMapper().editReadAll(userId);
    }

}
