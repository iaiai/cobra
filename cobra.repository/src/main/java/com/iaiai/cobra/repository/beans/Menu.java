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
 * Create Time: 2019/12/4 4:16 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("menu")
public class Menu {

    private String id;

    private String parentId;    //父级

    private String name;    //菜单名

    private String code;    //编码

    private Integer type;   //类型，1目录，2菜单，3功能，4子页面

    private String url; //url

    private String filePath;    //文件地址

    @TableField("`show`")
    private Integer show;   //是否显示,0未显示，1显示

    private Integer seq;    //顺序

    private String icon;    //图标

    private Integer cache;  //是否缓存，0不缓存，1缓存

    private Integer del;    //删除，0未删除，1已删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

    private String remark;  //备注

}
