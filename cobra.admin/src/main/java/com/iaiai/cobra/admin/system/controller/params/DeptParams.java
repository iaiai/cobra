package com.iaiai.cobra.admin.system.controller.params;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.params
 * Author: iaiai
 * Create Time: 2019/12/13 10:15 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class DeptParams {

    private String id;

    private String parentId;

    private String name;

    private Integer seq;

    private String remark;

    private Integer status;

    private String leader;

    private String phone;

    private String email;

}
