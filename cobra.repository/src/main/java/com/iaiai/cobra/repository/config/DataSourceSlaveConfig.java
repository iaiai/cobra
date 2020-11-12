package com.iaiai.cobra.repository.config;

//import com.github.pagehelper.PageInterceptor;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//import java.util.Properties;

//@Configuration
//@MapperScan(basePackages = "com.iaiai.cobra.repository", sqlSessionTemplateRef  = "sqlSessionSlave")
//@PropertySource("classpath:database.properties")
public class DataSourceSlaveConfig {

//    @Bean(name = "slaveDataSource")
//    @ConfigurationProperties(prefix = "cobra.datasource.slave")
//    public DataSource slaveDataSource() {
//        return DataSourceBuilder.create().driverClassName("org.mariadb.jdbc.Driver").build();
//    }
//
//    public PageInterceptor pageInterceptor(){
//        PageInterceptor interceptor = new PageInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("helperDialect", "mysql");
//        interceptor.setProperties(properties);
//        return interceptor;
//    }
//
//    @Bean(name = "slaveSqlSessionFactory")
//    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setPlugins(new Interceptor[]{pageInterceptor()});
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/iaiai/cobra/repository/**/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "sqlSessionSlave")
//    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}
