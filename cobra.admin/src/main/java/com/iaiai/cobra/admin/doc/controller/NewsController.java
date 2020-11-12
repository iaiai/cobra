package com.iaiai.cobra.admin.doc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.doc.controller.params.NewsParams;
import com.iaiai.cobra.admin.doc.service.NewsService;
import com.iaiai.cobra.admin.doc.service.NewsTypeService;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.News;
import com.iaiai.cobra.repository.beans.NewsType;
import com.iaiai.cobra.repository.pojo.NewsPojo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.controller
 * Author: iaiai
 * Create Time: 2020/1/24 6:54 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsTypeService newsTypeService;

    @Permission(code = "newsIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(@RequestBody(required = false) NewsParams params){
        if(params==null)params = new NewsParams();
        if(params.getPage()==null||params.getPage()<=0){
            params.setPage(1);
        }
        if(params.getLimit()==null||params.getLimit()<=0){
            params.setLimit(defaultPageSize);
        }

        Page _page = new Page<>(params.getPage(), params.getLimit());
        IPage<NewsPojo> list = newsService.queryAll(_page,params.getTypeId(),params.getSearch());
        return success(list);
    }

    @PostMapping(value = "/detail.json")
    public ResultVo detail(@RequestBody(required = false) NewsParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }
        News news = newsService.getById(params.getId());
        NewsPojo pojo = new NewsPojo();
        BeanUtils.copyProperties(news,pojo);

        if(StringUtils.isNotEmpty(news.getTypeId())) {
            NewsType newsType = newsTypeService.getById(news.getTypeId());
            pojo.setTypeName(newsType.getName());
        }

        return success(pojo);
    }

    @PostMapping(value = "/add.do")
    public ResultVo add(@RequestBody NewsParams params) throws ParseException {
        if(StringUtils.isEmpty(params.getTitle())){
            return fail("参数异常");
        }

        News news = new News();
        news.setId(StringUtil.get32UUID());
        news.setDel(Delete.NO.getValue());
        news.setCreateTime(new Date());
        news.setModifyTime(news.getCreateTime());
        news.setTitle(params.getTitle());
        news.setContent(params.getContent());
        news.setContentType(params.getContentType());
        news.setCover(params.getCover());
        news.setShow(params.getShow());
        news.setTypeId(params.getTypeId());
        if(StringUtils.isNotEmpty(params.getReleaseStartTime())){
            news.setReleaseStartTime(DateUtils.parseDate(params.getReleaseStartTime(),"yyyy-MM-dd HH:mm:ss"));
        }else{
            news.setReleaseStartTime(new Date());
        }
        if(StringUtils.isNotEmpty(params.getReleaseEndTime())){
            news.setReleaseEndTime(DateUtils.parseDate(params.getReleaseEndTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        newsService.add(news);

        return success(news);
    }

    @PostMapping(value = "/edit.do")
    public ResultVo edit(@RequestBody NewsParams params) throws ParseException {
        if(StringUtils.isEmpty(params.getId()) || StringUtils.isEmpty(params.getTitle())){
            return fail("参数异常");
        }

        News news = newsService.load(params.getId());
        news.setTitle(params.getTitle());
        news.setContent(params.getContent());
        news.setCover(params.getCover());
        news.setShow(params.getShow());
        news.setTypeId(params.getTypeId());
        if(StringUtils.isNotEmpty(params.getReleaseStartTime())){
            news.setReleaseStartTime(DateUtils.parseDate(params.getReleaseStartTime(),"yyyy-MM-dd HH:mm:ss"));
        }else{
            news.setReleaseStartTime(new Date());
        }
        if(StringUtils.isNotEmpty(params.getReleaseEndTime())){
            news.setReleaseEndTime(DateUtils.parseDate(params.getReleaseEndTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        newsService.edit(news);

        return success();
    }

    @Permission(code = "news:edit:show")
    @PostMapping(value = "/edit/show.do")
    public ResultVo editShow(@RequestBody NewsParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getShow()==null){
            return fail("参数异常");
        }

        newsService.editShow(params.getId(),params.getShow());

        return success();
    }

    @Permission(code = "news:del")
    @PostMapping(value = "/del.do")
    public ResultVo del(@RequestBody NewsParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        newsService.del(params.getId());

        return success();
    }

    @Permission(code = "news:del")
    @PostMapping(value = "/dels.do")
    public ResultVo dels(@RequestBody NewsParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        newsService.dels(params.getId().split(","));

        return success();
    }

}
