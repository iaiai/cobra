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
 * Create Time: 2020/10/26 1:58 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("msg")
public class Msg {

    private String id;

    private String userId;  //用户id

    private String title;   //标题

    private String content; //内容

    @TableField("`read`")
    private Integer read;   //是否读取

    private Date createTime;    //创建时间

    private Date readTime;  //读取时间

    private Integer del;    //删除，0未删除，1已删除

}
