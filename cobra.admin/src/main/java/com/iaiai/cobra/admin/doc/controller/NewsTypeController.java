package com.iaiai.cobra.admin.doc.controller;

import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.doc.controller.params.NewsTypeParams;
import com.iaiai.cobra.admin.doc.service.NewsTypeService;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.NewsType;
import com.iaiai.cobra.repository.pojo.NewsTypePojo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.news.controller
 * Author: iaiai
 * Create Time: 2020/1/23 3:44 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/news/type")
public class NewsTypeController extends BaseController {

    @Autowired
    private NewsTypeService newsTypeService;

    @Permission(code = "newsTypeIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(){
        List<NewsTypePojo> list = newsTypeService.queryAll();
        return success(list);
    }

    @Permission(code = "newsTypeIndex")
    @PostMapping(value = "/list.json")
    public ResultVo list(@RequestBody(required = false) NewsTypeParams params){
        List<NewsType> list = newsTypeService.queryByParent(params!=null?params.getParentId():null);
        return success(list);
    }

    @Permission(code = "news:type:add")
    @PostMapping(value = "/add.do")
    public ResultVo add(@RequestBody NewsType newsType){
        if(StringUtils.isEmpty(newsType.getName()) || newsType.getSeq()==null || newsType.getShow()==null){
            return fail("参数异常");
        }

        newsType.setId(StringUtil.get32UUID());
        newsType.setDel(Delete.NO.getValue());
        newsType.setCreateTime(new Date());
        newsType.setModifyTime(newsType.getCreateTime());
        newsTypeService.add(newsType);

        return success(newsType);
    }

    @Permission(code = "news:type:edit")
    @PostMapping(value = "/edit.do")
    public ResultVo edit(@RequestBody NewsType newsType){
        if(StringUtils.isEmpty(newsType.getName()) || newsType.getSeq()==null || newsType.getShow()==null){
            return fail("参数异常");
        }

        newsTypeService.edit(newsType);

        return success(newsType);
    }

    @Permission(code = "news:type:edit:show")
    @PostMapping(value = "/edit/show.do")
    public ResultVo editShow(@RequestBody NewsTypeParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getShow()==null){
            return fail("参数异常");
        }

        newsTypeService.editShow(params.getId(),params.getShow());

        return success();
    }

    @Permission(code = "news:type:del")
    @PostMapping(value = "/del.do")
    public ResultVo del(@RequestBody NewsTypeParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        newsTypeService.del(params.getId());

        return success();
    }

    @Permission(code = "news:type:del")
    @PostMapping(value = "/dels.do")
    public ResultVo dels(@RequestBody NewsTypeParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        String[] ids = params.getId().split(",");
        newsTypeService.dels(ids);

        return success();
    }

}
