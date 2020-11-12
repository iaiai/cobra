package com.iaiai.cobra.admin.intercepter;

import com.iaiai.cobra.admin.core.Attribute;
import com.iaiai.cobra.admin.core.util.IpUtil;
import com.iaiai.cobra.admin.intercepter.req.RequestWrapper;
import com.iaiai.cobra.admin.intercepter.req.ResponseWrapper;
import com.iaiai.cobra.admin.system.service.LogService;
import com.iaiai.cobra.common.util.ClientUtil;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.repository.beans.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Component("logIntercepter")
public class LogIntercepter implements HandlerInterceptor {

    private long startTime; //接口访问时间
    private long endTime; //接口访问时间
    private String body;

    @Autowired
    private IpUtil ipUtil;

    @Autowired
    protected LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        startTime = System.currentTimeMillis();

        //判断请求是否是上传的文档，如果上传文档则不记录内容
        String contentType = request.getHeader("content-type");
        if(contentType!=null&&!contentType.toLowerCase().equals("multipart/form-data")) {
            body = new RequestWrapper(request).getBody();
            if(body!=null && body.equals("null")){
                body = null;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse)response);
//        log.debug("....{}",new String(responseWrapper.getContent(),request.getCharacterEncoding()));
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        endTime = System.currentTimeMillis();
        long processorTime = endTime-startTime;
        String url = request.getRequestURL().toString();
        log(request,response);
        log.debug("------> 接口请求耗时(毫秒): {} / {}",processorTime,url);
    }

    private void log(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String ip = ClientUtil.getIpAddress(request);
        String ipAddress = ipUtil.getIpAddress(ip);

        Log log = new Log();
        log.setId(StringUtil.get32UUID());
        log.setHeaders(ClientUtil.getHeaderReturnString(request));
        log.setUrl(request.getRequestURL().toString());
        log.setMethod(request.getMethod());
        log.setParams(request.getQueryString());
        log.setIpForward(ClientUtil.getClientIp(request));
        log.setIp(ip);
        log.setIpAddress(ipAddress);
        log.setUserAgent(request.getHeader("user-agent"));
        log.setParamsBody(body);
        log.setResultHeaders(ClientUtil.getHeaderReturnString(response));
        log.setStartTime(new Date(startTime));
        log.setEndTime(new Date(endTime));
        log.setDuration(endTime-startTime);
        log.setCreateTime(new Date());

        //判断是否返回的是文件，获取不到返回的数据体，网上查需要用ResponseBodyAdvice这种方式来获取返回的数据
        //@RestController的类和带@ResponseBody的方法在被调用后response会直接写入输出流，在postHandle和afterCompletion这两个方法执行之前就已经把数据返回，导致这两个方法里面的response根本获取不到响应数据。
        if(response.getHeader("content-disposition")==null || !response.getHeader("content-disposition").startsWith("attachment")){
//            ResponseWrapper responseWrapper = new ResponseWrapper(Thread.currentThread().getId(), response);
//            String result = new String(responseWrapper.toByteArray(), responseWrapper.getCharacterEncoding());

//            ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse)response);
//            log.setResultBody(new String(responseWrapper.getContent(),request.getCharacterEncoding()));
        }

        logService.save(log);

        request.getSession().setAttribute(log.getIp(),log.getIpAddress());
        request.getSession().setAttribute(Attribute.Session.ip,log.getIp());
        request.getSession().setAttribute(Attribute.Session.requestUrl,log.getUrl());
        request.getSession().setAttribute(Attribute.Session.userAgent,log.getUserAgent());
        request.getSession().setAttribute(Attribute.Session.lastRequestTime, DateFormatUtils.format(log.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
    }

}
