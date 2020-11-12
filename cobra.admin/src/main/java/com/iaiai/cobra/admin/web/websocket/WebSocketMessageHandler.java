package com.iaiai.cobra.admin.web.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.iaiai.cobra.admin.redis.RedisUserCache;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.admin.system.service.ConfigService;
import com.iaiai.cobra.admin.web.controller.service.MsgService;
import com.iaiai.cobra.admin.web.websocket.vo.MessageRequestVo;
import com.iaiai.cobra.admin.web.websocket.vo.MessageResponseVo;
import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.constant.YesOrNo;
import com.iaiai.cobra.repository.beans.Config;
import com.iaiai.cobra.repository.beans.Msg;
import com.iaiai.cobra.repository.constants.ConfigKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.websocket
 * Author: iaiai
 * Create Time: 2020/10/29 9:30 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    public static class Router{
        public final static String beat = "/beat"; //心跳
        public final static String login = "/login"; //登录
        public final static String sendMsg = "/send/msg"; //发送消息
    }

    //用这种方式存储集群分布分出问题
    private static final Map<String, WebSocketSession> connectMap = new ConcurrentHashMap<>();

    //返回所有的连接
    public static Map<String, WebSocketSession> getConnectionMap(){
        return connectMap;
    }

    @Autowired
    private MsgService msgService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private RedisUserCache redisUserCache;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.debug("【websocket:{}】 create",session.getId());

        connectMap.put(session.getId(),session);
        this.refAttributeReq(session,null);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = new String(message.asBytes(), "UTF-8");
        log.debug("【websocket:{}】收到消息:{}",session.getId(),msg);

        MessageRequestVo<String> result = null;
        try{
            result = JsonUtil.getInstance().getMapper().readValue(msg, new TypeReference<MessageRequestVo<String>>() {});
        }catch (Exception e){
            e.printStackTrace();

            MessageResponseVo vo = new MessageResponseVo();
            vo.setCode(StringUtil.getUUID());
            vo.setErrorMsg(e.getLocalizedMessage());
            vo.setUrl("/error");
            vo.setIsError(1);
            sendMessage(session,vo);
        }
        this.refAttributeReq(session,result);

        try {
            if (result.getUrl().equals(Router.beat)) {
                //心跳
                MessageResponseVo vo = new MessageResponseVo();
                vo.setCode(result.getCode());
                vo.setUrl(result.getUrl());
                sendMessage(session, vo);
                return;
            }

            if (result.getUrl().equals(Router.login)) {
                String token = result.getResult();
                String userId = getUserId(token);

                //判断websocket配置
                Config config = configService.queryByKey(ConfigKey.websocketSingle.getKey());
                if(config!=null){
                    if(config.getValue().trim().equals(String.valueOf(YesOrNo.YES.getValue()))){
                        //判断只允许一个用户连接websocket
                        if(connectMap!=null && connectMap.size()>0){
                            Iterator<Map.Entry<String,WebSocketSession>> iterator = connectMap.entrySet().iterator();
                            while (iterator.hasNext()){
                                Map.Entry<String,WebSocketSession> entry = iterator.next();
                                WebSocketSession _session = entry.getValue();
                                if(_session.getAttributes().containsKey("userId") && _session.getAttributes().get("userId").toString().equals(userId)){
                                    //把之前用户T掉
                                    _session.close();
                                }
                            }
                        }
                    }
                }

                //登录，保存token
                session.getAttributes().put("token", token);
                session.getAttributes().put("userId", userId);  //用户id

                MessageResponseVo vo = new MessageResponseVo();
                vo.setCode(result.getCode());
                vo.setUrl(result.getUrl());
                sendMessage(session, vo);

                //查询未读消息
                this.queryMessage(session);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();

            MessageResponseVo vo = new MessageResponseVo();
            vo.setCode(result.getCode());
            vo.setUrl(result.getUrl());
            vo.setErrorMsg(e.getLocalizedMessage());
            vo.setIsError(1);
            sendMessage(session,vo);
        }
    }

    private String getUserId(String token) throws IOException {
        String json = redisUserCache.getLoginByToken(token);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        OnlineVo onlineVo = JsonUtil.getInstance().deserialize(json, OnlineVo.class);
        return onlineVo.getUser().getId();
    }

    //查询未读消息
    private void queryMessage(WebSocketSession session) {
        try {
            List<Msg> msgs = msgService.queryNoRead(session.getAttributes().get("userId").toString());
            if(msgs!=null && msgs.size()>0){
                for (int i = 0; i < msgs.size(); i++) {
                    MessageResponseVo vo = new MessageResponseVo();
                    vo.setUrl(Router.sendMsg);
                    vo.setCode(StringUtil.getUUID());
                    vo.setResult(msgs.get(i));
                    this.sendMessage(session,vo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("【websocket:{}】 close.",session.getId());
        connectMap.remove(session.getId());
    }

    private void refAttributeReq(WebSocketSession session,MessageRequestVo<String> result){
        session.getAttributes().put("time", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SSS"));
        if(result!=null){
            session.getAttributes().put("url",result.getUrl());
        }
    }

    private static void refAttributeRes(WebSocketSession session,MessageResponseVo result){
        session.getAttributes().put("time", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SSS"));
        if(result!=null){
            session.getAttributes().put("url",result.getUrl());
        }
    }

    /**
     * 发送消息
     * @param session
     * @param vo
     */
    private void sendMessage(WebSocketSession session,MessageResponseVo vo) throws Exception {
        String msg = JsonUtil.getInstance().serialize(vo);
        log.debug("【websocket:{}】发送消息:{}",session.getId(),msg);
        session.sendMessage(new TextMessage(msg));

        this.refAttributeRes(session,vo);
    }

    /**
     * 发送消息
     * @param sessionId
     * @param vo
     */
    public static void sendMessage(String sessionId,MessageResponseVo vo) throws Exception {
        WebSocketSession session = getConnectionMap().get(sessionId);

        String msg = JsonUtil.getInstance().serialize(vo);
        log.debug("【websocket:{}】发送消息:{}",session.getId(),msg);
        session.sendMessage(new TextMessage(msg));

        refAttributeRes(session,vo);
    }

    /**
     * 返回token
     * @param sessionId
     */
    public static String getToken(String sessionId){
        WebSocketSession session = connectMap.get(sessionId);
        if(session==null)return null;

        if(session.getAttributes().containsKey("token")) {
            return session.getAttributes().get("token").toString();
        }

        return null;
    }

}
