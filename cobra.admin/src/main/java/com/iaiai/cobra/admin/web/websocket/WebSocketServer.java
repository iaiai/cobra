package com.iaiai.cobra.admin.web.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.websocket.vo
 * Author: iaiai
 * Create Time: 2020/10/27 9:24 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 * 参考地址: https://blog.csdn.net/moshowgame/article/details/80275084
 * 这种方式不执行onClose，无法断开连接，弃用
 */
//@Slf4j
//@ServerEndpoint("/websocket/{token}")
//@Component
public class WebSocketServer {

//    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;
//
//    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
//
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//
//    /**
//     * 连接建立成功调用的方法
//     * @param session
//     * @param token
//     */
//    @OnOpen
//    public void onOpen(Session session,@PathParam("token") String token) throws IOException {
//        log.debug("【websocket】创建连接，session:{}，token:{}",session.getId(),token);
//        if(StringUtils.isEmpty(token)){
//            session.close();
//            return;
//        }
//
//        if(webSocketMap.containsKey(session.getId())){
//            webSocketMap.remove(session.getId());
//            webSocketMap.put(session.getId(),this); //加入set中
//        }else{
//            webSocketMap.put(session.getId(),this);
//            addOnlineCount(); //在线数加1
//        }
//
//        this.session = session;
//        this.session.getUserProperties().put("token",token);
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        if(webSocketMap.containsKey(session.getId())){
//            webSocketMap.remove(session.getId());
//            //从set中删除
//            subOnlineCount();
//        }
//        log.debug("【websocket】连接关闭，session:{},token:{}",session.getId(),session.getUserProperties().get("token"));
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     * @param message 客户端发送过来的消息
//     * @param session
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        log.debug("【websocket】接收: {}",message);
//        if(message.equals("beat request")){
//            //心跳
//            session.getBasicRemote().sendText("beat ok");
//            return;
//        }
//
//        //处理其它请求
//    }
//
//    /**
//     * 出错
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.debug("【websocket】异常 session: {}，token: {}，{}",session.getId(),session.getUserProperties().get("token"),error.getLocalizedMessage());
//    }
//
//    /**
//     * 实现服务器主动推送
//     * @param message
//     * @throws IOException
//     */
//    public void sendMessage(String message) throws IOException {
//        log.debug("【websocket】发送消息: {}，session:{}，token:{}",message,session.getId(),session.getUserProperties().get("token"));
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    /**
//     * 发送自定义消息
//     * @param message
//     * @param token
//     * @throws IOException
//     */
//    public static void sendWithToken(String message,@PathParam("token") String token) throws IOException {
//        log.debug("【websocket】发送消息:{}，token:{}",token);
//
//        if(webSocketMap.size()<=0)return;
//
//        Iterator<Map.Entry<String,WebSocketServer>> iterator = webSocketMap.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String,WebSocketServer> entry = iterator.next();
//            Session session = entry.getValue().session;
//            if(session.getUserProperties().get("token").toString().equals(token)){
//                log.debug("【websocket】发送消息:{}，session:{}，token:{}",session.getId(),token);
//                webSocketMap.get(token).sendMessage(message);
//            }
//        }
//    }
//
//    /**
//     * 发送自定义消息
//     * @param message
//     * @param sessionId
//     * @throws IOException
//     */
//    public static void sendWithSession(String message,@PathParam("sessionId") String sessionId) throws IOException {
//        log.debug("【websocket】发送消息:{}，session:{}",sessionId);
//        if(StringUtils.isNotBlank(sessionId)&&webSocketMap.containsKey(sessionId)){
//            webSocketMap.get(sessionId).sendMessage(message);
//        }else{
//            log.debug("【websocket】session: {}不在线！",sessionId);
//        }
//    }
//
//    /**
//     * 获取在线总数
//     * @return
//     */
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    /**
//     * 添加在线人数
//     */
//    public static synchronized void addOnlineCount() {
//        WebSocketServer.onlineCount++;
//    }
//
//    /**
//     * 减去在线人数
//     */
//    public static synchronized void subOnlineCount() {
//        WebSocketServer.onlineCount--;
//    }

}
