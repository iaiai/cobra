package com.iaiai.cobra.admin.doc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.doc.service.DocDirectoryService;
import com.iaiai.cobra.repository.beans.DocDirectory;
import com.iaiai.cobra.repository.model.DocDirectoryMapper;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.doc.service.impl
 * Author: iaiai
 * Create Time: 2020/5/25 9:49 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("docDirectoryService")
public class DocDirectoryServiceImpl extends ServiceImpl<DocDirectoryMapper, DocDirectory> implements DocDirectoryService {

}
