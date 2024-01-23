package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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

    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.hikari.jdbc-url}")
    private String jdbcUrl;
    @Value("${spring.datasource.hikari.username}")
    private String username;
    @Value("${spring.datasource.hikari.password}")
    private String password;
    
    @Bean(value = "dataSource1")
    @Primary
    public DataSource dataSource() {
    	HikariConfig config = new HikariConfig();
    	config.setDriverClassName(driverClassName);
    	config.setJdbcUrl(jdbcUrl);
    	config.setUsername(username);
    	config.setPassword(password);

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
                .persistenceUnit("test")
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

    /*
     * mybatis 설정
     */

    // @Bean
    // public SqlSessionFactory sessionFactory(DataSource dataSource)throws Exception{
    //     SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    //     bean.setDataSource(dataSource);
    //     bean.setConfigLocation(new ClassPathResource(""));
        
    //     return bean.getObject();
    // }
    
    // @Bean
    // public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
    //     return new SqlSessionTemplate(sqlSessionFactory);
    // }
    
    // @Bean
    // public PlatformTransactionManager transactionManager(DataSource dataSource){
    //     return new DataSourceTransactionManager(dataSource);
    // }
}
