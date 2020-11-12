package com.iaiai.cobra.admin.doc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.doc.service.NewsTypeService;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.NewsType;
import com.iaiai.cobra.repository.model.NewsTypeMapper;
import com.iaiai.cobra.repository.pojo.NewsTypePojo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.service.impl
 * Author: iaiai
 * Create Time: 2020/1/23 3:45 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("newsTypeService")
public class NewsTypeServiceImpl extends ServiceImpl<NewsTypeMapper, NewsType> implements NewsTypeService {

    public List<NewsTypePojo> queryAll(){
        return getBaseMapper().queryAll();
    }

    public List<NewsType> queryByParent(String parentId){
        return getBaseMapper().queryByParent(parentId);
    }

    public int add(NewsType newsType){
        return getBaseMapper().insert(newsType);
    }

    public int edit(NewsType newsType){
        return getBaseMapper().updateById(newsType);
    }

    public int editShow(String id,Integer show){
        NewsType newsType = new NewsType();
        newsType.setId(id);
        newsType.setShow(show);
        newsType.setModifyTime(new Date());
        return getBaseMapper().updateById(newsType);
    }

    public int del(String id){
        NewsType newsType = new NewsType();
        newsType.setId(id);
        newsType.setDel(Delete.YES.getValue());
        newsType.setModifyTime(new Date());
        return getBaseMapper().updateById(newsType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dels(String[] ids){
        for (int i = 0; i < ids.length; i++) {
            NewsType newsType = new NewsType();
            newsType.setId(ids[i]);
            newsType.setDel(Delete.YES.getValue());
            newsType.setModifyTime(new Date());
            getBaseMapper().updateById(newsType);
        }
    }

}
