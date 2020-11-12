package com.iaiai.cobra.admin.web.controller.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.Msg;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller.service
 * Author: iaiai
 * Create Time: 2020/10/26 2:10 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface MsgService extends IService<Msg> {

    /**
     * 查询所有的未读信息
     * @param userId
     * @return
     */
    List<Msg> queryNoRead(String userId);

    IPage<Msg> queryPage(Page page,String userId);

    /**
     * 设置所有的读取状态
     */
    void editReadAll(String userId);

}
