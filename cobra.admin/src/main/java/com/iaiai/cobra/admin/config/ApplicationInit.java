package com.iaiai.cobra.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.config
 * Author: iaiai
 * Create Time: 2020/7/8 11:17 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@Component
public class ApplicationInit {

    @PostConstruct
    public void init() {
        //可以初始化一些东西
    }

}
