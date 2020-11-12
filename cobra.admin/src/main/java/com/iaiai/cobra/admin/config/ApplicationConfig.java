package com.iaiai.cobra.admin.config;

import com.iaiai.cobra.admin.annotation.PermissionInterceptor;
import com.iaiai.cobra.admin.intercepter.LogIntercepter;
import com.iaiai.cobra.admin.intercepter.SessionIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class ApplicationConfig implements WebMvcConfigurer {

    @Autowired
    private SessionIntercepter sessionIntercepter;

    @Autowired
    private LogIntercepter logIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logIntercepter).addPathPatterns("/**");
        registry.addInterceptor(sessionIntercepter).addPathPatterns("/**");
    }

    @Bean
    public PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

}
