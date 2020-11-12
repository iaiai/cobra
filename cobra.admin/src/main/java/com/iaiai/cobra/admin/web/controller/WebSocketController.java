package com.iaiai.cobra.admin.web.controller;

import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.redis.RedisUserCache;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.admin.web.controller.params.WebSocketParams;
import com.iaiai.cobra.admin.web.controller.service.MsgService;
import com.iaiai.cobra.admin.web.controller.vo.WebSocketVo;
import com.iaiai.cobra.admin.web.websocket.WebSocketMessageHandler;
import com.iaiai.cobra.admin.web.websocket.vo.MessageResponseVo;
import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.common.vo.constant.YesOrNo;
import com.iaiai.cobra.repository.beans.Msg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller
 * Author: iaiai
 * Create Time: 2020/10/29 2:00 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/websocket")
public class WebSocketController extends BaseController {

    @Autowired
    private MsgService msgService;

    @Autowired
    private RedisUserCache redisUserCache;

    @PostMapping("/index.json")
    public ResultVo index(){
        Map<String,Object> map = new HashMap<>();

        Map<String, WebSocketSession> connectionMap = WebSocketMessageHandler.getConnectionMap();
        if(connectionMap==null || connectionMap.size()<=0){
            map.put("count",0); //在线人数
            return success(map);
        }

        List<WebSocketVo> list = new ArrayList<>();
        Iterator<Map.Entry<String, WebSocketSession>> iterator = connectionMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, WebSocketSession> entry = iterator.next();

            WebSocketVo webSocketVo = new WebSocketVo();
            webSocketVo.setSessionId(entry.getValue().getId());

            if(entry.getValue().getAttributes().containsKey("token")) {
                webSocketVo.setToken(entry.getValue().getAttributes().get("token").toString());
            }
            if(entry.getValue().getAttributes().containsKey("time")) {
                webSocketVo.setTime(entry.getValue().getAttributes().get("time").toString());
            }
            if(entry.getValue().getAttributes().containsKey("url")) {
                webSocketVo.setUrl(entry.getValue().getAttributes().get("url").toString());
            }
            if(entry.getValue().getAttributes().containsKey("ip")) {
                webSocketVo.setIp(entry.getValue().getAttributes().get("ip").toString());
            }
            if(entry.getValue().getAttributes().containsKey("port")) {
                webSocketVo.setPort(Integer.parseInt(entry.getValue().getAttributes().get("port").toString()));
            }
            if(entry.getValue().getAttributes().containsKey("ipAddress")) {
                webSocketVo.setIpAddress(entry.getValue().getAttributes().get("ipAddress").toString());
            }
            list.add(webSocketVo);
        }

        map.put("connections", list);
        map.put("count",list.size());
        return success(map);
    }

    @PostMapping("/send.do")
    public ResultVo send(@RequestBody WebSocketParams webSocketParams) throws Exception {
        if(webSocketParams==null || StringUtils.isEmpty(webSocketParams.getSessionId())){
            return fail("参数异常");
        }

        String token = WebSocketMessageHandler.getToken(webSocketParams.getSessionId());

        String json = redisUserCache.getLoginByToken(token);
        if (StringUtils.isEmpty(json)) {
            return fail("数据异常");
        }
        OnlineVo onlineVo = JsonUtil.getInstance().deserialize(json, OnlineVo.class);

        //添加到数据库中
        Msg msg = new Msg();
        msg.setId(StringUtil.get32UUID());
        msg.setRead(YesOrNo.NO.getValue());
        msg.setUserId(onlineVo.getUser().getId());
        msg.setContent(webSocketParams.getContent());
        msg.setTitle(webSocketParams.getTitle());
        msg.setCreateTime(new Date());
        msg.setDel(Delete.NO.getValue());
        msgService.save(msg);

        MessageResponseVo<Msg> vo = new MessageResponseVo();
        vo.setUrl(WebSocketMessageHandler.Router.sendMsg);
        vo.setCode(StringUtil.get32UUID());
        vo.setResult(msg);
        WebSocketMessageHandler.sendMessage(webSocketParams.getSessionId(),vo);

        return success();
    }

}
