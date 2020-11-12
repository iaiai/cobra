package com.iaiai.cobra.admin.system.controller.params;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.params
 * Author: iaiai
 * Create Time: 2020/1/22 3:00 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class UploadFileParams {

    private String search;

    private Integer page;   //第几页

    private Integer limit;  //每面多少条

}
