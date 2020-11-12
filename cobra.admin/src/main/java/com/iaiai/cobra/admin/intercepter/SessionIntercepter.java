package com.iaiai.cobra.admin.intercepter;

import com.iaiai.cobra.admin.core.util.IpUtil;
import com.iaiai.cobra.admin.redis.RedisUserCache;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.common.util.ClientUtil;
import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component("sessionIntercepter")
public class SessionIntercepter implements HandlerInterceptor {

    @Autowired
    private RedisUserCache redisUserCache;

    private static final long onlineTimeout = 1 * 60 * 30;  //秒,30分钟

    @Autowired
    private IpUtil ipUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String url = request.getServletPath();
        //如果是登录不走过滤
        if (url.matches("/login(.*?)") || url.matches("/img/(.*?)")) {
            return true;
        }

        String token = request.getHeader("token");

        //判断session是否登录
        if (StringUtils.isEmpty(token)) {
            //跳转到登录页
            return jumpLogin(response);
        }

        String json = redisUserCache.getLoginByToken(token);
        if (StringUtils.isEmpty(json)) {
            return jumpLogin(response);
        }

        String ip = ClientUtil.getIpAddress(request);
        String ipAddress = ipUtil.getIpAddress(ip);

        OnlineVo onlineVo = JsonUtil.getInstance().deserialize(json, OnlineVo.class);
        onlineVo.setSessionId(request.getSession().getId());
        onlineVo.setIpAddress(ipAddress);
        onlineVo.setUserAgent(request.getHeader("user-agent"));
        onlineVo.setUrl(request.getRequestURL().toString());
        onlineVo.setIp(ip);
        onlineVo.setLastTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));

        //设置在线信息
        redisUserCache.setOnline(token,onlineVo);

        return true;
    }

    //跳转到登录页
    private boolean jumpLogin(HttpServletResponse response) throws IOException {
        ResultVo result = new ResultVo();
        result.setCode(ResultCode.LOGIN.getValue());
        response.getWriter().write(JsonUtil.getInstance().serialize(result));
        return false;
    }
}
