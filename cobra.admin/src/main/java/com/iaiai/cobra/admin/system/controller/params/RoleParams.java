package com.iaiai.cobra.admin.system.controller.params;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.controller.params
 * Author: iaiai
 * Create Time: 2019/12/10 8:45 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class RoleParams {

    private String id;

    private String name;

    private Integer seq;

    private String remark;

    private Integer status;

    private String menus;

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
