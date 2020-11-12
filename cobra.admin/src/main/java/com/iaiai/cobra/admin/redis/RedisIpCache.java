package com.iaiai.cobra.admin.redis;

import com.iaiai.cobra.admin.core.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.redis
 * Author: iaiai
 * Create Time: 2020/11/6 上午9:55
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Component
public class RedisIpCache {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setIpAddress(String ip,String ipAddress){
        redisTemplate.opsForValue().set(Attribute.Global.ipAddress + ":" + ip, ipAddress);
    }

    public String getIpAddress(String ip){
        return redisTemplate.opsForValue().get(Attribute.Global.ipAddress + ":" + ip);
    }

}
