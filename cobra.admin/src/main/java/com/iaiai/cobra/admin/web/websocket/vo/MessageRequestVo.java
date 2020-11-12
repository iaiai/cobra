package com.iaiai.cobra.admin.web.websocket.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.websocket.vo
 * Author: iaiai
 * Create Time: 2020/10/26 5:11 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class MessageRequestVo<T> {

    private String url; //请求地址
    private T result;   //请求数据

    private String code;    //唯一标识码

}
