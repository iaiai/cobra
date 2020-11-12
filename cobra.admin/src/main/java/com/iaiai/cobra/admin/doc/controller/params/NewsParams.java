package com.iaiai.cobra.admin.doc.controller.params;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.controller.params
 * Author: iaiai
 * Create Time: 2020/1/24 9:44 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class NewsParams {

    private String search;  //搜索内容

    private String typeId;  //分类id

    private Integer page;   //第几页

    private Integer limit;  //每面多少条

    private String id;

    private String title;   //标题

    private String cover; //图片

    private String content; //内容

    private Integer contentType;    //内容类型,1:html，2: Markdown

    private Integer show;   //是否显示，1显示，0不显示

    private String releaseStartTime;  //发布开始时间

    private String releaseEndTime;    //发布结束时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getReleaseStartTime() {
        return releaseStartTime;
    }

    public void setReleaseStartTime(String releaseStartTime) {
        this.releaseStartTime = releaseStartTime;
    }

    public String getReleaseEndTime() {
        return releaseEndTime;
    }

    public void setReleaseEndTime(String releaseEndTime) {
        this.releaseEndTime = releaseEndTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
