//package com.ez.tablebase.rest;
///*
// * Created by ErikZ on 16/05/2018.
// */
//
//import com.ez.tablebase.rest.database.CategoryEntity;
//import com.ez.tablebase.rest.database.PathEntity;
//import com.ez.tablebase.rest.database.EntryEntity;
//import com.ez.tablebase.rest.database.TableEntity;
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.*;
//import org.springframework.core.env.Environment;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Properties;
//
//import static org.hibernate.cfg.Environment.*;
//
//@PropertySource("classpath:db.properties")
//@EnableTransactionManagement
//public class DBConfiguration
//{
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public SessionFactory sessionFactory()
//    {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//        // Setting JDBC properties
//        sessionFactory.setDataSource(getDataSource());
//
//        // Setting Hibernate properties
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        try {
//            sessionFactory.afterPropertiesSet();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        sessionFactory.setAnnotatedClasses(TableEntity.class, CategoryEntity.class, PathEntity.class, EntryEntity.class);
//
//        return sessionFactory.getObject();
//    }
//
//    @Bean
//    public DataSource getDataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(env.getProperty("database.driver"));
//        dataSource.setUrl(env.getProperty("database.url"));
//        dataSource.setUsername(env.getProperty("database.root"));
//        dataSource.setPassword(env.getProperty("database.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public HibernateTransactionManager hibTransMan(){
//        return new HibernateTransactionManager(sessionFactory());
//    }
//
//    private Properties hibernateProperties() {
//        Properties props = new Properties();
//        props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
//        props.put(DIALECT, env.getProperty("hibernate.dialect"));
//        return props;
//    }
//}
