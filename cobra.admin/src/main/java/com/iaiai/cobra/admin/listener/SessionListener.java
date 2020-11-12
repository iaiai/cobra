package com.iaiai.cobra.admin.listener;

import com.iaiai.cobra.admin.core.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.listener
 * Author: iaiai
 * Create Time: 2019/12/26 4:47 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener {

//    public static AtomicInteger userCount = new AtomicInteger(0);

    /**
     * 用户登录，创建session，用户数增加
     * @param event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        Attribute.Session.add(event.getSession());
    }

    /**
     * 用户下线，销毁session，用户数减少
     * @param event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        Attribute.Session.remove(event.getSession().getId());
    }

}
