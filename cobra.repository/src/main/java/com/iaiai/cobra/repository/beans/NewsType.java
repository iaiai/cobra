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
 * Create Time: 2020/1/23 3:38 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news_type")
public class NewsType {

    private String id;

    private String parentId;    //父级id

    private String name;    //类型名

    private String icon;    //图标

    @TableField("`show`")
    private Integer show;   //是否显示，1显示，0不显示，默认为1

    private Integer seq;    //排序

    private String remark;  //备注

    private Integer del;    //删除，1已删除，0未删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
