package com.iaiai.cobra.admin.doc.controller.params;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.controller.params
 * Author: iaiai
 * Create Time: 2020/1/23 6:11 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class NewsTypeParams {

    private String id;

    private Integer show;   //是否显示，0未显示，1显示

    private String parentId;    //父id

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
