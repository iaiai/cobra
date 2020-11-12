package com.iaiai.cobra.repository.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.beans
 * Author: iaiai
 * Create Time: 2020/1/24 7:24 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news")
public class News {

    private String id;

    private String typeId;  //分类id

    private String title;   //标题

    private String cover; //图片

    private String content; //内容

    private Integer contentType;    //内容类型,1:html，2: Markdown

    @TableField("`show`")
    private Integer show;   //是否显示，1显示，0不显示

    private Date releaseStartTime;  //发布开始时间

    private Date releaseEndTime;    //发布结束时间

    private Integer del;    //删除，0未删除，1已删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
