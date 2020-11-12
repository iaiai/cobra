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
 * Create Time: 2020/5/25 9:39 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description: 文档
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("doc_file")
public class DocFile {

    private String id;

    private String directoryId; //目录id

    private String filename;    //文件名

    private String content; //内容

    private Integer fileType;   //文件内容类型，1:markdown

    private Integer del;    //是否删除，0未删除，1已删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
