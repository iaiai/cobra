package com.iaiai.cobra.admin.doc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.News;
import com.iaiai.cobra.repository.pojo.NewsPojo;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.service
 * Author: iaiai
 * Create Time: 2020/1/24 9:41 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface NewsService extends IService<News> {

    //查询新闻
    IPage<NewsPojo> queryAll(Page page, String typeId, String search);

    int add(News news);

    int edit(News news);

    int editShow(String id,Integer show);

    News load(String id);

    int del(String id);

    void dels(String[] ids);

}
