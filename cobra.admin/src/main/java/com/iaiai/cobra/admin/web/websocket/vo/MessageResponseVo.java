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
public class MessageResponseVo<T> {

    private String code;    //唯一标识码

    private String url; //路由
    private T result;   //数据

    private int isError = 0;    //是否出错，0没有，1出错
    private String errorMsg;    //异常信息

}
