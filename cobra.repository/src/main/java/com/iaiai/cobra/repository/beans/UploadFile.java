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
 * Create Time: 2019/12/30 4:04 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("upload_file")
public class UploadFile {

    private String id;

    private String filename;    //文件名

    private String suffix;  //后缀

    private String path;    //存储地址

    private String webUrl;  //访问地址

    private Long size;   //文件大小

    private Integer del;    //删除，0未删除，1已删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
