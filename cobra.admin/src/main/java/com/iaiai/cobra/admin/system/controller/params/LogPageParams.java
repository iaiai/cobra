package com.iaiai.cobra.admin.system.controller.params;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.params
 * Author: iaiai
 * Create Time: 2019/12/25 7:22 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class LogPageParams {

    private Integer page;   //第几页

    private Integer limit;  //每面多少条

    private String startTime;   //开始时间

    private String endTime; //结束时间

    private String search;  //搜索内容

}
