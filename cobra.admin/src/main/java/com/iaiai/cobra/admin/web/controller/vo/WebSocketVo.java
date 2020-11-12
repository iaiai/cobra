package com.iaiai.cobra.admin.web.controller.vo;

import com.iaiai.cobra.admin.system.controller.vo.UserVo;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller.vo
 * Author: iaiai
 * Create Time: 2020/10/29 2:15 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class WebSocketVo {

    private String url; //最后请求地址

    private String sessionId;   //sessionId

    private String time;    //时间

    private String token;   //token

    private UserVo user;  //当前用户

    private String ip;  //ip

    private Integer port;   //端口

    private String ipAddress;   //ip反查地址

}
