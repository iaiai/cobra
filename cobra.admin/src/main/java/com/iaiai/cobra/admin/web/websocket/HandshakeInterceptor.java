package com.iaiai.cobra.admin.web.websocket;

import com.iaiai.cobra.admin.core.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.websocket
 * Author: iaiai
 * Create Time: 2020/10/29 9:29 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Autowired
    private IpUtil ipUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpSession session = servletRequest.getServletRequest().getSession(false);
        if (session != null) {
            //使用userName区分WebSocketHandler，以便定向发送消息
            attributes.put("sessionId",session.getId());

            String ip = request.getRemoteAddress().getHostName();
            int port = request.getRemoteAddress().getPort();

            String ipAddress = ipUtil.getIpAddress(ip);

            attributes.put("ip",ip);
            attributes.put("port",port);
            attributes.put("ipAddress",ipAddress);
        }

        // 握手前的处理逻辑
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        // 握手后的处理逻辑
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
