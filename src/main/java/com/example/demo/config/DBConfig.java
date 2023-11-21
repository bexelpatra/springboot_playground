package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.example.demo.repository"}
)
public class DBConfig {


    @Bean(value = "dataSource1")
    @Primary
    public DataSource dataSource() {
    	HikariConfig config = new HikariConfig();
    	config.setDriverClassName("org.mariadb.jdbc.Driver");
    	config.setJdbcUrl("jdbc:mariadb://192.168.0.44:3306/TEST_DB?characterEncoding=UTF-8&serverTimezone=UTC");
    	config.setUsername("root");
    	config.setPassword("qlalf52%");
//    	config.addDataSourceProperty("driverClassName", "org.mariadb.jdbc.Driver");
//    	config.addDataSourceProperty("jdbcUrl", "jdbc:mariadb://192.168.0.44:3306/TEST_DB?characterEncoding=UTF-8&serverTimezone=UTC");
//    	config.addDataSourceProperty("username", "root");
//    	config.addDataSourceProperty("password", "qlalf52%");

        HikariDataSource hikariDataSource = new HikariDataSource(config);
        
        return hikariDataSource;
    }
    @Bean(name="entityManager")
    public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder, @Qualifier("dataSource1")DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.example.demo.entity")
                .persistenceUnit("test_db")
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("entityManager")jakarta.persistence.EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

//    @Bean
//    public MultipartResolver multipartResolver(){
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//        commonsMultipartResolver.setMaxUploadSize((long) 524e+5);
//
//        return commonsMultipartResolver;
//    }
//    public MultipartResolver multipartResolver() {
//        return new StandardServletMultipartResolver();
//    }

}
