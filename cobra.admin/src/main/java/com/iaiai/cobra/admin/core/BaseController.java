package com.iaiai.cobra.admin.core;

import com.iaiai.cobra.admin.redis.RedisUserCache;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.vo.ListResultVo;
import com.iaiai.cobra.common.vo.ObjResultVo;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.rbt.lgates.core
 * Author: iaiai
 * Create Time: 2019/7/24 11:48 AM
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
public class BaseController {

    @Value("${cobra.config.page-size}")
    public int defaultPageSize; //默认页面

    @Autowired
    protected HttpServletRequest request;

//    @Autowired
//    protected HttpServletResponse response;

    @Autowired
    private RedisUserCache redisUserCache;

    protected HttpSession getSession(){
        return request.getSession();
    }

    protected ResultVo success(){
        ResultVo result = new ResultVo();
        result.setCode(ResultCode.SUCCESS.getValue());
        return result;
    }

    protected ResultVo success(Object obj){
        ObjResultVo result = new ObjResultVo();
        result.setCode(ResultCode.SUCCESS.getValue());
        result.setResult(obj);
        return result;
    }

    protected ResultVo success(List list){
        ListResultVo result = new ListResultVo();
        result.setCode(ResultCode.SUCCESS.getValue());
        result.setResult(list);
        return result;
    }

    protected ResultVo fail(String msg){
        ResultVo result = new ResultVo();
        result.setCode(ResultCode.FAIL.getValue());
        result.setMsg(msg);
        return result;
    }

    protected ResultVo other(ResultCode resultCode, String msg){
        ResultVo result = new ResultVo();
        result.setCode(resultCode.getValue());
        result.setMsg(msg);
        return result;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    protected OnlineVo getUser() {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String json = redisUserCache.getLoginByToken(token);
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        OnlineVo onlineVo;

        try {
            onlineVo = JsonUtil.getInstance().deserialize(json, OnlineVo.class);
        } catch (Exception e) {
            return null;
        }

        return onlineVo;
//        return ((User)request.getSession().getAttribute(Attribute.Session.userKey));
    }

    protected String getUserId() {
        return getUser().getUser().getId();
    }

    protected String getToken(){
        return request.getHeader("token");
    }

}
