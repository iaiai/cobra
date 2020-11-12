package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.UploadFile;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2020/1/22 2:43 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface UploadFileService extends IService<UploadFile> {

    int add(UploadFile uploadFile);

    //查询上传的文件
    IPage<UploadFile> queryAll(Page page, String search);

}
