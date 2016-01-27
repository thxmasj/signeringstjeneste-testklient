package no.difi.signature.testclient.configuration;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.ValidatorFactory;
import java.util.concurrent.Executor;


@Configuration
@ComponentScan( { "no.difi.signature.testclient.web", "no.difi.signature.testclient.service", "no.difi.signature.testclient.validation" } )
@PropertySources( {
	@PropertySource(value = "classpath:configuration.properties"), // Defaults
	@PropertySource(value = "file:/etc/opt/signatureclient/configuration.properties", ignoreResourceNotFound = true) // Optional overrides
})
@EnableWebMvc
@EnableJpaRepositories(basePackages = "no.difi.signature.testclient.repository")
@EnableTransactionManagement
@EnableScheduling
//@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = true, order = 3)
public class SignatureClientConfiguration extends WebMvcConfigurerAdapter implements AsyncConfigurer {

	@Autowired
	private Environment environment;

	@Override
	public Executor getAsyncExecutor() {
		//TODO:
		return null;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver urlBasedViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/");
		viewResolver.setSuffix(".jspx");
		return viewResolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		MultipartResolver multipartResolver = new CommonsMultipartResolver();
		return multipartResolver;
	}

	@Bean
	public ValidatorFactory validator() {
		ValidatorFactory validatorFactory = new LocalValidatorFactoryBean();
		return validatorFactory;
	}


	@Bean
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(environment.getProperty("database.driver"));
		dataSource.setUrl(environment.getProperty("database.url"));
		dataSource.setUsername(environment.getProperty("database.username"));
		dataSource.setPassword(environment.getProperty("database.password"));
		// Connection pool configuration - refer to http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html
		dataSource.setMaxActive(environment.getProperty("database.pool.maxActive", Integer.class));
		dataSource.setMaxIdle(environment.getProperty("database.pool.maxIdle", Integer.class));
		dataSource.setMinIdle(environment.getProperty("database.pool.minIdle", Integer.class));
		dataSource.setInitialSize(environment.getProperty("database.pool.initialSize", Integer.class));
		dataSource.setMaxWait(environment.getProperty("database.pool.maxWait", Integer.class));
		dataSource.setTestOnBorrow(environment.getProperty("database.pool.testOnBorrow", Boolean.class));
		dataSource.setTestOnReturn(environment.getProperty("database.pool.testOnReturn", Boolean.class));
		dataSource.setTestWhileIdle(environment.getProperty("database.pool.testWhileIdle", Boolean.class));
		dataSource.setValidationQuery(environment.getProperty("database.pool.validationQuery"));
		dataSource.setValidationQueryTimeout(environment.getProperty("database.pool.validationQueryTimeout", Integer.class));
		dataSource.setTimeBetweenEvictionRunsMillis(environment.getProperty("database.pool.timeBetweenEvictionRunsMillis", Integer.class));
		dataSource.setMinEvictableIdleTimeMillis(environment.getProperty("database.pool.minEvictableIdleTimeMillis", Integer.class));
		dataSource.setRemoveAbandoned(environment.getProperty("database.pool.removeAbandoned", Boolean.class));
		dataSource.setRemoveAbandonedTimeout(environment.getProperty("database.pool.removeAbandonedTimeout", Integer.class));
		dataSource.setLogAbandoned(environment.getProperty("database.pool.logAbandoned", Boolean.class));
		dataSource.setValidationInterval(environment.getProperty("database.pool.validationInterval", Integer.class));
		dataSource.setFairQueue(environment.getProperty("database.pool.fairQueue", Boolean.class));
		dataSource.setAbandonWhenPercentageFull(environment.getProperty("database.pool.abandonWhenPercentageFull", Integer.class));
		dataSource.setMaxAge(environment.getProperty("database.pool.maxAge", Integer.class));
		dataSource.setLogValidationErrors(environment.getProperty("database.pool.logValidationErrors", Boolean.class));
		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setDatabase(environment.getProperty("database.vendor", Database.class));
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setPackagesToScan("no.difi.signature.testclient.domain");
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.afterPropertiesSet();
		return entityManagerFactory.getObject();
	}

	@Bean
	public PersistenceExceptionTranslator persistenceExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
		return jpaTransactionManager;
	}
}