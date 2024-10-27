package com.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@ComponentScan
public class DatabaseConfig  {

    private final Environment environment;

    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }


    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    };

    @Bean(name = "datasource", autowireCandidate = true)
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean(autowireCandidate = true)
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        //inject dataSource bean to sessionFactory using method parameter
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan( new String[] {
                "com.spring.mvc",
                "com.spring.mvc.entity",
                "com.spring.mvc.dao",
                "com.spring.mvc.dao.impl",
                "com.spring.mvc.service.impl"
        } );
        sessionFactory.setHibernateProperties(hibernateProperties());
        System.out.println("-->  sessionFactory=" + sessionFactory == null ? " null": " not null");
        return sessionFactory;
    }

    @Bean(autowireCandidate = true)
    HibernateTransactionManager getTransactionManager(DataSource dataSource) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        System.out.println("-->  transactionManager=" + transactionManager == null ? " null" : " not null");
        return transactionManager;
    }

}
