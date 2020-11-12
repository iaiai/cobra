package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.pojo.MenuPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2019/12/4 4:18 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> queryByParent(@Param("parentId") String parentId);

    List<MenuPojo> queryAll();

    List<Menu> queryMenuByParent(@Param("parentId") String parentId);

    Menu queryByCode(@Param("code") String code);

    List<Menu> queryByRole(@Param("roleId") String roleId);

    List<Menu> queryShowByRole(@Param("roleId") String roleId);

}
