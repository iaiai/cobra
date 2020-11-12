package com.iaiai.cobra.admin.web.controller.params;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller.params
 * Author: iaiai
 * Create Time: 2020/10/30 11:46 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class WebSocketParams {

    private String title;   //发送的标题

    private String content; //发送的内容

    private String token;   //发送人的token

    private String sessionId;   //发送的websocket的sessionid

}
