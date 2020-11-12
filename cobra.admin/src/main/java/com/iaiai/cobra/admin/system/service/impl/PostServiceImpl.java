package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.PostService;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Post;
import com.iaiai.cobra.repository.constants.PostStatus;
import com.iaiai.cobra.repository.model.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2019/12/12 11:07 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("postService")
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    public List<Post> queryAll(){
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getDel, Delete.NO.getValue());
        queryWrapper.orderByAsc(Post::getSeq);
        return getBaseMapper().selectList(queryWrapper);
    }

    public List<Post> queryEnable(){
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getDel, Delete.NO.getValue());
        queryWrapper.eq(Post::getStatus, PostStatus.enable.getKey());
        queryWrapper.orderByAsc(Post::getSeq);
        return getBaseMapper().selectList(queryWrapper);
    }

    public int add(Post post){
        return getBaseMapper().insert(post);
    }

    public int edit(Post post){
        return getBaseMapper().updateById(post);
    }

    public Post load(String id){
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getId, id);
        return getBaseMapper().selectById(id);
    }

    public int del(String id){
        Post post = new Post();
        post.setId(id);
        post.setModifyTime(new Date());
        post.setDel(Delete.YES.getValue());
        return getBaseMapper().updateById(post);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dels(String[] ids){
        for (int i = 0; i < ids.length; i++) {
            Post post = new Post();
            post.setId(ids[i]);
            post.setModifyTime(new Date());
            post.setDel(Delete.YES.getValue());
            getBaseMapper().updateById(post);
        }
    }

    public List<Post> queryByUser(String userId){
        return getBaseMapper().queryByUser(userId);
    }

}
