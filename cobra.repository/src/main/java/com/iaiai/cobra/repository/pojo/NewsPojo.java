package com.iaiai.cobra.repository.pojo;

import com.iaiai.cobra.repository.beans.News;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.pojo
 * Author: iaiai
 * Create Time: 2020/1/24 9:49 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class NewsPojo extends News {

    private String typeName;    //分类名

}
