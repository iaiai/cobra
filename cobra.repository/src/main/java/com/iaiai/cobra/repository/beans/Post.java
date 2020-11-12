package com.iaiai.cobra.repository.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.beans
 * Author: iaiai
 * Create Time: 2019/12/12 10:59 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description: 岗位
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("post")
public class Post {

    private String id;

    private String name;    //岗位名

    private String code;    //编码

    private String remark;  //备注

    private Integer seq;    //顺序

    private Integer status; //状态,1启用，0禁用

    private Integer del;    //删除，1已删除，0未删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
