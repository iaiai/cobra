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
 * Create Time: 2020/5/25 9:36 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description: 文档目录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("doc_directory")
public class DocDirectory {

    private String id;

    private String name;    //目录名

    private String parentId;    //上级id

    private Integer del;    //是否删除，0未删除，1已删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
