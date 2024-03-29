package com.example.course.dbConfig;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.course")
public class CourseConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource-course")
  public DataSource courseDataSource() {
    return DataSourceBuilder.create().build();
  }

}