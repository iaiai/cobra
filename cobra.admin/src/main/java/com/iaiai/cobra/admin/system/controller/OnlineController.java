package com.iaiai.cobra.admin.system.controller;

import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.redis.RedisUserCache;
import com.iaiai.cobra.admin.system.controller.params.OnlineParams;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller
 * Author: iaiai
 * Create Time: 2019/12/26 8:31 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/online")
public class OnlineController extends BaseController {

    @Autowired
    private RedisUserCache redisUserCache;

    @PostMapping(value = "/index.json")
    public ResultVo index() throws IOException {
        List<OnlineVo> list = new ArrayList<>();
        Set<String> keys = redisUserCache.getOnlineKey();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String token = key.substring(key.lastIndexOf(":") + 1);
            String json = redisUserCache.getLoginByToken(token);
            if(StringUtils.isNotEmpty(json)) {
                OnlineVo onlineVo = JsonUtil.getInstance().deserialize(json, OnlineVo.class);
                onlineVo.setToken(token);
                list.add(onlineVo);
            }
        }

        return success(list);
    }

    @Permission(code = "system:online:exit")
    @PostMapping(value = "/exit.do")
    public ResultVo exit(@RequestBody OnlineParams params) {
        if (params == null || StringUtils.isEmpty(params.getToken()) || StringUtils.isEmpty(params.getUserId())) {
            return fail("参数异常");
        }

        redisUserCache.delLogin(params.getToken(),params.getUserId());

        return success();
    }

}
