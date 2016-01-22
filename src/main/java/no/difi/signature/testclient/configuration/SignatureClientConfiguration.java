package no.difi.signature.testclient.configuration;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.concurrent.Executor;


@Configuration
@ComponentScan( { "no.difi.signature.testclient.web", "no.difi.signature.testclient.service", "no.difi.signature.testclient.validation" } )
@PropertySources( {
	@PropertySource(value = "classpath:configuration.properties"), // Defaults
	@PropertySource(value = "file:/etc/opt/signatureclient/configuration.properties", ignoreResourceNotFound = true) // Optional overrides
})
@EnableWebMvc
//@EnableJpaRepositories(basePackages = "no.difi.signature.testclient.repository")
//@EnableTransactionManagement
//@EnableScheduling
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

}