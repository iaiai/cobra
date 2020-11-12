package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.UploadFileService;
import com.iaiai.cobra.repository.beans.UploadFile;
import com.iaiai.cobra.repository.model.UploadFileMapper;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2020/1/22 2:45 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("uploadFileService")
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile> implements UploadFileService {

    public int add(UploadFile uploadFile){
        return getBaseMapper().insert(uploadFile);
    }

    public IPage<UploadFile> queryAll(Page page, String search){
        return getBaseMapper().queryAll(page,search);
    }

}
