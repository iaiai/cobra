package com.iaiai.cobra.admin.system.controller;

import com.iaiai.cobra.admin.annotation.Permission;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.system.controller.params.PostParams;
import com.iaiai.cobra.admin.system.service.PostService;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Post;
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
 * Package: com.iaiai.cobra.admin.system.controller
 * Author: iaiai
 * Create Time: 2019/12/12 11:09 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping("/system/post")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @Permission(code = "systemPostIndex")
    @PostMapping(value = "/index.json")
    public ResultVo index(){
        List<Post> list = postService.queryAll();
        return success(list);
    }

    @Permission(code = "system:post:add")
    @PostMapping(value = "/add.do")
    public ResultVo add(@RequestBody PostParams params){
        if(params==null
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Post post = new Post();
        post.setId(StringUtil.get32UUID());
        post.setName(params.getName());
        post.setRemark(params.getRemark());
        post.setSeq(params.getSeq());
        post.setStatus(params.getStatus());
        post.setCode(params.getCode());
        post.setDel(Delete.NO.getValue());
        post.setCreateTime(new Date());
        post.setModifyTime(post.getCreateTime());
        postService.add(post);

        return success();
    }

    @Permission(code = "system:post:edit")
    @PostMapping(value = "/edit.do")
    public ResultVo edit(@RequestBody PostParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || StringUtils.isEmpty(params.getName())
                || params.getSeq()==null
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Post post = postService.load(params.getId());
        post.setName(params.getName());
        post.setRemark(params.getRemark());
        post.setSeq(params.getSeq());
        post.setStatus(params.getStatus());
        post.setCode(params.getCode());
        postService.edit(post);

        return success();
    }

    @Permission(code = "system:post:edit:status")
    @PostMapping(value = "/edit/status.do")
    public ResultVo editStatus(@RequestBody PostParams params){
        if(params==null
                || StringUtils.isEmpty(params.getId())
                || params.getStatus()==null){
            return fail("参数异常");
        }

        Post post = postService.load(params.getId());
        post.setStatus(params.getStatus());
        postService.edit(post);

        return success();
    }

    @Permission(code = "system:post:del")
    @PostMapping(value = "/del.do")
    public ResultVo del(@RequestBody PostParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        postService.del(params.getId());

        return success();
    }

    @Permission(code = "system:post:del")
    @PostMapping(value = "/dels.do")
    public ResultVo dels(@RequestBody PostParams params){
        if(params==null || StringUtils.isEmpty(params.getId())){
            return fail("参数异常");
        }

        String[] ids = params.getId().split(",");
        postService.dels(ids);

        return success();
    }

    @Permission(code = "system:post:query")
    @PostMapping(value = "/query.json")
    public ResultVo query(){
        List<Post> list = postService.queryEnable();
        return success(list);
    }

}
