package com.iaiai.cobra.admin.web.controller.params;

import com.iaiai.cobra.admin.core.BaseParams;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller.params
 * Author: iaiai
 * Create Time: 2020/11/1 8:36 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class MsgParams extends BaseParams {

    private String search;  //搜索

    private String id;

}
