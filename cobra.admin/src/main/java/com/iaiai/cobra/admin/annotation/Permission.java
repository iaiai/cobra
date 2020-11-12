package com.iaiai.cobra.admin.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.annotation
 * Author: iaiai
 * Create Time: 2019/12/20 10:09 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    //menu中存储的code值来判断是否有此权限
    String code();

}
