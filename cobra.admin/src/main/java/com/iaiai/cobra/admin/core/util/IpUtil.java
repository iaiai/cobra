package com.iaiai.cobra.admin.core.util;

import com.iaiai.cobra.admin.core.Attribute;
import com.iaiai.cobra.admin.redis.RedisIpCache;
import com.iaiai.cobra.common.util.ip.QQWry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core.util
 * Author: iaiai
 * Create Time: 2020/6/5 9:47 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@Component
public class IpUtil {

    @Autowired
    private QQWry qqWry;

    @Autowired
    private RedisIpCache redisIpCache;

    /**
     * 获取ip地址
     * @param ip
     * @return
     * @throws Exception
     */
    public String getIpAddress(String ip) {
        String ipAddress = redisIpCache.getIpAddress(ip);
        if(StringUtils.isNotEmpty(ipAddress)){
            return ipAddress;
        }

        ipAddress = qqWry.findIP(ip).getMainInfo() + "/" + qqWry.findIP(ip).getSubInfo();
        redisIpCache.setIpAddress(ip,ipAddress);

        return ipAddress;
    }

//    public static void main(String[] args) {
//        System.out.println(DigestUtils.md5Hex(DigestUtils.md5Hex("a8748b26349a460eaff05153496d3f7a")));
//    }

}
