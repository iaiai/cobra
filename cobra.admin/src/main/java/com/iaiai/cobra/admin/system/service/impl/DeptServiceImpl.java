package com.iaiai.cobra.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaiai.cobra.admin.system.service.DeptService;
import com.iaiai.cobra.common.vo.constant.Delete;
import com.iaiai.cobra.repository.beans.Dept;
import com.iaiai.cobra.repository.model.DeptMapper;
import com.iaiai.cobra.repository.pojo.DeptPojo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.system.service.impl
 * Author: iaiai
 * Create Time: 2019/12/13 9:06 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    public List<Dept> queryAll(){
        return getBaseMapper().queryAll();
    }

    public List<Dept> queryByChilder(String id){
        return getBaseMapper().queryByChilder(id);
    }

    public List<Dept> queryByUser(String userId){
        return getBaseMapper().queryByUser(userId);
    }

    public List<DeptPojo> queryByShow(){
        return getBaseMapper().queryByShow();
    }

    public int add(Dept dept){
        return getBaseMapper().insert(dept);
    }

    public int edit(Dept dept){
        return getBaseMapper().updateById(dept);
    }

    public Dept load(String id){
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getId, id);
        return getBaseMapper().selectOne(queryWrapper);
    }

    public int del(String id){
        Dept dept = new Dept();
        dept.setId(id);
        dept.setModifyTime(new Date());
        dept.setDel(Delete.YES.getValue());
        return getBaseMapper().updateById(dept);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dels(String[] ids){
        for (int i = 0; i < ids.length; i++) {
            Dept dept = new Dept();
            dept.setId(ids[i]);
            dept.setModifyTime(new Date());
            dept.setDel(Delete.YES.getValue());
            getBaseMapper().updateById(dept);
        }
    }

}
