package com.iaiai.cobra.admin.doc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.NewsType;
import com.iaiai.cobra.repository.pojo.NewsTypePojo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.service
 * Author: iaiai
 * Create Time: 2020/1/23 3:45 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface NewsTypeService extends IService<NewsType> {

    //包含子，不显示的也查
    List<NewsTypePojo> queryAll();

    //根据父级查询(为空则是顶层)
    List<NewsType> queryByParent(String parentId);

    int add(NewsType newsType);

    int edit(NewsType newsType);

    int editShow(String id,Integer show);

    int del(String id);

    void dels(String[] ids);

}
