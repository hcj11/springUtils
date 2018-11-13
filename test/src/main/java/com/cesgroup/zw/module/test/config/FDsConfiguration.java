package com.cesgroup.zw.module.test.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * 多数据库源配置，
 * Created by houchunjian on 2018/9/10 0010
 */
@Configuration
@MapperScan(basePackages = "com.cesgroup.zw.module.*.dao")
public class FDsConfiguration {
    public static final String DATA_SOURCE = "fds";

    public static final String TRANSCATION_MANAGER = "ftm";

    public static final String SQLSESSION_FACTORY = "fsf";

    @Value("${spring.fs.datasource.url}")
    public String url;
    @Value("${spring.fs.datasource.userName}")
    public String userName;
    @Value("${spring.fs.datasource.password}")
    public String password;
    @Value("${spring.fs.datasource.driver-class-name}")
    public String driver_class_name;

    @Bean(name = DATA_SOURCE)
    @Primary
    public DataSource dataSource() {

        DruidDataSource build = DataSourceBuilder.create().username(userName)
                .password(password).driverClassName(driver_class_name)
                .url(url)
                .type(DruidDataSource.class)
                .build();
        build.setMaxActive(20);
        build.setMinIdle(5);
        build.setMaxWait(60000);
        build.setValidationQuery("SELECT 1");
        build.setQueryTimeout(6000);
        build.setTransactionQueryTimeout(6000);
        build.setRemoveAbandoned(true);
        return  build;
    }

    @Bean(name = TRANSCATION_MANAGER)
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier(DATA_SOURCE) DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean(name = SQLSESSION_FACTORY)
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE) DataSource ds) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        return bean.getObject();
    }
}
