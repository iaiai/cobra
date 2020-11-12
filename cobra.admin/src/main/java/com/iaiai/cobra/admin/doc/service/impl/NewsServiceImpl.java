package com.iaiai.cobra.admin.doc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.doc.service.NewsService;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.News;
import com.iaiai.cobra.repository.model.NewsMapper;
import com.iaiai.cobra.repository.pojo.NewsPojo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.service.impl
 * Author: iaiai
 * Create Time: 2020/1/24 9:41 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("newsService")
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    public IPage<NewsPojo> queryAll(Page page, String typeId, String search){
        return getBaseMapper().queryAll(page,typeId,search);
    }

    public int add(News news){
        return getBaseMapper().insert(news);
    }

    public int edit(News news){
        return getBaseMapper().updateById(news);
    }

    public News load(String id){
        return getBaseMapper().selectById(id);
    }

    public int editShow(String id,Integer show){
        News news = new News();
        news.setId(id);
        news.setShow(show);
        news.setModifyTime(new Date());

        return getBaseMapper().updateById(news);
    }

    public int del(String id){
        News news = new News();
        news.setId(id);
        news.setModifyTime(new Date());
        news.setDel(Delete.YES.getValue());
        return getBaseMapper().updateById(news);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dels(String[] ids){
        for (int i = 0; i < ids.length; i++) {
            News news = new News();
            news.setId(ids[i]);
            news.setModifyTime(new Date());
            news.setDel(Delete.YES.getValue());
            getBaseMapper().updateById(news);
        }
    }

}
