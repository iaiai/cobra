package com.iaiai.cobra.admin.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core
 * Author: iaiai
 * Create Time: 2020/5/18 2:34 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandleAdvice extends BaseController {

    @ExceptionHandler
    public Object handler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        log.error("Restful Http请求发生异常...", e);

        return fail(e.getLocalizedMessage());
    }

}
