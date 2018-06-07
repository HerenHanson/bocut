package com.hanson;

import javax.servlet.annotation.WebFilter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CharacterEncodingFilter;


/**
 * 在tomcat中运行的入口类
 * 
 * @SpringBootApplication的作用等同于以下3个注解
 * 	@Configuration  
	@EnableAutoConfiguration 
	@ComponentScan
 *
 */
@SpringBootApplication
//@ImportResource("test-config.xml")
public class Application4Tomcat extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(Application4Tomcat.class);
	}
	@Bean
	public CharacterEncodingFilter initializeCharacterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
	
}
