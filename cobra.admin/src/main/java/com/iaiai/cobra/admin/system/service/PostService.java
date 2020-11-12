package com.iaiai.cobra.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaiai.cobra.repository.beans.Post;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service
 * Author: iaiai
 * Create Time: 2019/12/12 11:07 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public interface PostService extends IService<Post> {

    List<Post> queryAll();

    //查询启用的
    List<Post> queryEnable();

    int add(Post post);

    int edit(Post post);

    Post load(String id);

    int del(String id);

    void dels(String[] ids);

    List<Post> queryByUser(String userId);

}
