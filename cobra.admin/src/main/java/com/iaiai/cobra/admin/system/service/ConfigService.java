package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.Config;
import com.iaiai.cobra.repository.beans.Dept;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2020/11/5 下午2:21
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface ConfigService extends IService<Config> {

    List<Config> queryAll();

    int edit(Config config);

    Config queryByKey(String key);

}
