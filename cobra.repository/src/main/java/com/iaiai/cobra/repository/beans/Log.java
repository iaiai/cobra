package com.iaiai.cobra.repository.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.beans
 * Author: iaiai
 * Create Time: 2019/12/24 3:52 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("log")
public class Log {

    private String id;

    private Date startTime; //请求时间

    private Date endTime;   //结束时间

    private String headers; //头信息

    private String ip;  //ip

    private Long duration;   //时长

    private String params;  //参数

    private String paramsBody;  //body体

    private String resultHeaders;   //结果头

    private String resultBody;  //结果数据体

    private String method;  //请求方式

    private String userAgent;   //ua头

    private String ipForward;   //IP列表

    private String ipAddress;   //ip反查后地址

    private String url; //请求的url

    private String terminalType;    //终端类型

    private Date createTime;    //时间

}
