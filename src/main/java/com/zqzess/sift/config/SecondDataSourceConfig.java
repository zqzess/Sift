package com.zqzess.sift.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/07/27 10:22
 * @Package com.zqzess.sift.config
 * @Version :
 * @Desc :
 * @GitHUb Https://github.com/zqzess
 * ========================
 **/
@Configuration
@MapperScan(basePackages = "com.zqzess.sift.mapper.second", sqlSessionTemplateRef = "secondSqlSessionTemplate")
public class SecondDataSourceConfig {
    @Value("${spring.datasource.second.url:}")
    private String url;
    @Value("${spring.datasource.second.mapper-locations:}")
    private String mapperLocations;
    @Value("${spring.datasource.second.driver-class-name:}")
    private String driverClassName;
    @Value("${spring.datasource.second.type-aliases-package:}")
    private String typeAliasesPackage;
    @Value("${spring.datasource.second.username:}")
    private String username;
    @Value("${spring.datasource.second.password:}")
    private String password;

    @Bean(name = "secondDataSource")
    public DataSource secondDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        // dataSource.setUsername(username);
        // dataSource.setPassword(password);
        // dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "secondDataSourceTransactionManager")
    public DataSourceTransactionManager secondDataSourceTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
