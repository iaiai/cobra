package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaiai.cobra.repository.beans.NewsType;
import com.iaiai.cobra.repository.pojo.NewsTypePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2020/1/23 3:40 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface NewsTypeMapper extends BaseMapper<NewsType> {

    List<NewsTypePojo> queryAll();

    List<NewsType> queryByParent(@Param("parentId") String parentId);

}
