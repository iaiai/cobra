package com.iaiai.cobra.admin.system.controller.params;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.params
 * Author: iaiai
 * Create Time: 2019/12/19 5:01 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class UserPageParams {

    //部门id
    private String deptId;

    //第几页，默认为1
    private Integer page;

    //每面多少条，默认10条
    private Integer limit;

    //搜索
    private String search;

}
