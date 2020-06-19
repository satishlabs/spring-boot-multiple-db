package com.satish.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "bookEntityManagerFactory",basePackages = {
		"com.satish.repository.BookRepository"}, transactionManagerRef = "booktransactionManager")
public class BookDBConfig {
	
	@Bean(name = "bookdatasource")
	@ConfigurationProperties(prefix = "spring.book.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build(); //
	}

	@Bean(name = "bookEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("bookdatasource")DataSource dataSource) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hb2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return builder.dataSource(dataSource).properties(properties).packages("com.satish.model").persistenceUnit("Book").build();
	}
	
	@Bean(name = "booktransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("bookEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
