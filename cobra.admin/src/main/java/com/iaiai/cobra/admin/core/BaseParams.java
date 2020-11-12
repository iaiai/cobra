package com.iaiai.cobra.admin.core;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core
 * Author: iaiai
 * Create Time: 2019/12/13 10:17 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class BaseParams {

    private Integer page = 1;   //默认第一页

    private Integer limit = 20; //默认20

}
