package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.ConfigService;
import com.iaiai.cobra.repository.beans.Config;
import com.iaiai.cobra.repository.model.ConfigMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2020/11/5 下午2:22
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    public List<Config> queryAll(){
        return getBaseMapper().selectList(new LambdaQueryWrapper<>());
    }

    public int edit(Config config){
        return getBaseMapper().updateById(config);
    }

    public Config queryByKey(String key){
        LambdaQueryWrapper<Config> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Config::getKey, key);
        return getBaseMapper().selectOne(queryWrapper);
    }

}
