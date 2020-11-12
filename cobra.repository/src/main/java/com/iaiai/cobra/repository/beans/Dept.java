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
 * Create Time: 2019/12/13 9:03 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dept")
public class Dept {

    private String id;

    private String parentId;    //父级

    private String name;    //部门名

    private String remark;  //备注

    private Integer seq;    //顺序

    private Integer status; //状态,1启用，0禁用

    private String leader;  //负责人

    private String phone;   //联系电话

    private String email;   //邮箱

    private Integer del;    //删除，0未删除，1已删除

    private Date createTime;    //创建时间

    private Date modifyTime;    //最后修改时间

}
