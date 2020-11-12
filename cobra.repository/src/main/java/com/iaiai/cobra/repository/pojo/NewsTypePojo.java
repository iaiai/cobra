package com.iaiai.cobra.repository.pojo;

import com.iaiai.cobra.repository.beans.NewsType;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.pojo
 * Author: iaiai
 * Create Time: 2020/1/23 3:50 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class NewsTypePojo extends NewsType {

    private List<NewsTypePojo> children;

}
