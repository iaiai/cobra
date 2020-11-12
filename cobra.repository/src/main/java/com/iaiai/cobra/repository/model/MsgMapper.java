package com.iaiai.cobra.repository.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.beans.Msg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.model
 * Author: iaiai
 * Create Time: 2020/10/26 2:00 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Repository
public interface MsgMapper extends BaseMapper<Msg> {

    void editReadAll(@Param("userId") String userId);

}
