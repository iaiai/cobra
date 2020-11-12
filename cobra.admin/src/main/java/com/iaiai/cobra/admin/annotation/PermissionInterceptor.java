package com.iaiai.cobra.admin.annotation;

import com.iaiai.cobra.admin.redis.RedisUserCache;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.annotation
 * Author: iaiai
 * Create Time: 2019/12/20 10:18 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Aspect
@Slf4j
public class PermissionInterceptor {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private RedisUserCache redisUserCache;

    @Around("@annotation(permission)")
    public Object around(ProceedingJoinPoint pjp, Permission permission) throws Throwable {
        //判断是否有权限
        String json = redisUserCache.getLoginByToken(request.getHeader("token"));
        if (StringUtils.isEmpty(json)) {
            //无权限
            ResultVo result = new ResultVo();
            result.setCode(ResultCode.NO_PERMISSION.getValue());
            return result;
        }

        OnlineVo onlineVo = JsonUtil.getInstance().deserialize(json, OnlineVo.class);

        Set<String> permissions = onlineVo.getPermissions();
        if (!permissions.contains(permission.code())) {
            //无权限
            ResultVo result = new ResultVo();
            result.setCode(ResultCode.NO_PERMISSION.getValue());
            return result;
        }

        return pjp.proceed(pjp.getArgs());
    }

}
