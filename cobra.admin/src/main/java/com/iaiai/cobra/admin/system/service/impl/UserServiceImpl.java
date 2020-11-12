package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.UserService;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.User;
import com.iaiai.cobra.repository.beans.UserDept;
import com.iaiai.cobra.repository.beans.UserPost;
import com.iaiai.cobra.repository.beans.UserRole;
import com.iaiai.cobra.repository.model.UserDeptMapper;
import com.iaiai.cobra.repository.model.UserMapper;
import com.iaiai.cobra.repository.model.UserPostMapper;
import com.iaiai.cobra.repository.model.UserRoleMapper;
import com.iaiai.cobra.repository.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2019/12/3 8:00 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeptMapper userDeptMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public User queryByUserAndPassword(String username){
        return getBaseMapper().queryByUserAndPassword(username);
    }

    public User queryByUser(String username){
        return getBaseMapper().queryByUser(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(User user,String[] deptIds,String[] postIds,String[] roleIds){
        getBaseMapper().insert(user);

        for (int i = 0; i < deptIds.length; i++) {
            UserDept userDept = new UserDept();
            userDept.setId(StringUtil.get32UUID());
            userDept.setUserId(user.getId());
            userDept.setDeptId(deptIds[i]);
            userDept.setDel(Delete.NO.getValue());
            userDept.setCreateTime(new Date());
            userDept.setModifyTime(userDept.getCreateTime());
            userDeptMapper.insert(userDept);
        }

        for (int i = 0; i < postIds.length; i++) {
            UserPost userPost = new UserPost();
            userPost.setId(StringUtil.get32UUID());
            userPost.setUserId(user.getId());
            userPost.setPostId(postIds[i]);
            userPost.setDel(Delete.NO.getValue());
            userPost.setCreateTime(new Date());
            userPost.setModifyTime(userPost.getCreateTime());
            userPostMapper.insert(userPost);
        }

        for (int i = 0; i < roleIds.length; i++) {
            UserRole userRole = new UserRole();
            userRole.setId(StringUtil.get32UUID());
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleIds[i]);
            userRole.setDel(Delete.NO.getValue());
            userRole.setCreateTime(new Date());
            userRole.setModifyTime(userRole.getCreateTime());
            userRoleMapper.insert(userRole);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(User user,String[] deptIds,String[] postIds,String[] roleIds){
        getBaseMapper().updateById(user);

        //判断部门
        List<UserDept> userDepts = userDeptMapper.queryByUser(user.getId());
        List<UserDept> currentUserDepts = new ArrayList<>();
        for (int i = 0; i < userDepts.size(); i++) {
            boolean bol = false;
            for (int j = 0; j < deptIds.length; j++) {
                if(deptIds[j].equals(userDepts.get(i).getDeptId())){
                    bol = true;
                    currentUserDepts.add(userDepts.get(i));
                    break;
                }
            }
            if(!bol){
                //无此部门，直接删除
                UserDept userDept = new UserDept();
                userDept.setId(userDepts.get(i).getId());
                userDept.setModifyTime(new Date());
                userDept.setDel(Delete.YES.getValue());
                userDeptMapper.updateById(userDept);
            }
        }
        for (int i = 0; i < deptIds.length; i++) {
            boolean bol = false;
            for (int j = 0; j < currentUserDepts.size(); j++) {
                if(currentUserDepts.get(j).getDeptId().equals(deptIds[i])){
                    bol = true;
                    break;
                }
            }
            if(!bol){
                //如果里面没有则添加
                UserDept userDept = new UserDept();
                userDept.setId(StringUtil.get32UUID());
                userDept.setUserId(user.getId());
                userDept.setDeptId(deptIds[i]);
                userDept.setDel(Delete.NO.getValue());
                userDept.setCreateTime(new Date());
                userDept.setModifyTime(userDept.getCreateTime());
                userDeptMapper.insert(userDept);
            }
        }

        //岗位
        List<UserPost> userPosts = userPostMapper.queryByUser(user.getId());
        List<UserPost> currentUserPosts = new ArrayList<>();
        for (int i = 0; i < userPosts.size(); i++) {
            boolean bol = false;
            for (int j = 0; j < postIds.length; j++) {
                if(postIds[j].equals(userPosts.get(i).getPostId())){
                    bol = true;
                    currentUserPosts.add(userPosts.get(i));
                    break;
                }
            }
            if(!bol){
                //无此部门，直接删除
                UserPost userPost = new UserPost();
                userPost.setId(userPosts.get(i).getId());
                userPost.setDel(Delete.YES.getValue());
                userPost.setModifyTime(new Date());
                userPostMapper.updateById(userPost);
            }
        }
        for (int i = 0; i < postIds.length; i++) {
            boolean bol = false;
            for (int j = 0; j < currentUserPosts.size(); j++) {
                if(currentUserPosts.get(j).getPostId().equals(postIds[i])){
                    bol = true;
                    break;
                }
            }
            if(!bol){
                //如果里面没有则添加
                UserPost userPost = new UserPost();
                userPost.setId(StringUtil.get32UUID());
                userPost.setUserId(user.getId());
                userPost.setPostId(postIds[i]);
                userPost.setDel(Delete.NO.getValue());
                userPost.setCreateTime(new Date());
                userPost.setModifyTime(userPost.getCreateTime());
                userPostMapper.insert(userPost);
            }
        }

        //角色
        List<UserRole> userRoles = userRoleMapper.queryByUser(user.getId());
        List<UserRole> currentUserRoles = new ArrayList<>();
        for (int i = 0; i < userRoles.size(); i++) {
            boolean bol = false;
            for (int j = 0; j < roleIds.length; j++) {
                if(roleIds[j].equals(userRoles.get(i).getRoleId())){
                    bol = true;
                    currentUserRoles.add(userRoles.get(i));
                    break;
                }
            }
            if(!bol){
                //无此部门，直接删除
                UserRole userRole = new UserRole();
                userRole.setId(userRoles.get(i).getId());
                userRole.setDel(Delete.YES.getValue());
                userRole.setModifyTime(new Date());
                userRoleMapper.updateById(userRole);
            }
        }
        for (int i = 0; i < roleIds.length; i++) {
            boolean bol = false;
            for (int j = 0; j < currentUserRoles.size(); j++) {
                if(currentUserRoles.get(j).getRoleId().equals(roleIds[i])){
                    bol = true;
                    break;
                }
            }
            if(!bol){
                //如果里面没有则添加
                UserRole userRole = new UserRole();
                userRole.setId(StringUtil.get32UUID());
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleIds[i]);
                userRole.setDel(Delete.NO.getValue());
                userRole.setCreateTime(new Date());
                userRole.setModifyTime(userRole.getCreateTime());
                userRoleMapper.insert(userRole);
            }
        }
    }

    public int edit(User user){
        return getBaseMapper().updateById(user);
    }

    public IPage<UserPojo> queryByDept(Page page, String deptId, String search){
        return getBaseMapper().queryByDept(page,deptId,search);
    }

    public User load(String id){
        return getBaseMapper().load(id);
    }

    public int del(String id){
        User user = new User();
        user.setId(id);
        user.setModifyTime(new Date());
        user.setDel(Delete.YES.getValue());
        return getBaseMapper().updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dels(String[] ids){
        for (int i = 0; i < ids.length; i++) {
            User user = new User();
            user.setId(ids[i]);
            user.setModifyTime(new Date());
            user.setDel(Delete.YES.getValue());
            getBaseMapper().updateById(user);
        }
    }

    public int editPassword(String id,String password){
        User user = new User();
        user.setId(id);
        user.setModifyTime(new Date());
        user.setPassword(password);

        return getBaseMapper().updateById(user);
    }

}
