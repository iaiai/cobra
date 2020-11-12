package com.iaiai.cobra.platform.wx.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.wx.bean
 * Author: iaiai
 * Create Time: 2020/6/4 5:56 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 *
 * 参考: https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
 */
@Data
public class WxCode2Session {

    private String openid;  //用户唯一标识

    private String sessionKey;  //会话密钥

    private String unionid; //用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回

    private Integer errcode; //错误码

    private String errmsg;  //错误信息
}
