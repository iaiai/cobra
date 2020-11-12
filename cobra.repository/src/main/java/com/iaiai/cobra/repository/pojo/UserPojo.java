package com.iaiai.cobra.repository.pojo;

import com.iaiai.cobra.repository.beans.User;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.pojo
 * Author: iaiai
 * Create Time: 2019/12/19 4:46 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class UserPojo extends User {

    private String deptId;  //部门id

    private String deptName;    //部门名

}
