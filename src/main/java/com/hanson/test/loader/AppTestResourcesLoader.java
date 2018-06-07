package com.hanson.test.loader;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.hanson.test.config.AppTestConfig;
import com.hanson.test.handler.TestOnStartup;


/**
 * 
 * 加载测试用例的入口，并提供获得spring bean的方法
 *
 */

@Configuration
@AutoConfigureAfter(value=AppTestConfigLoader.class)
public class AppTestResourcesLoader  implements ApplicationContextAware{

	/**
	 * 将spring上下文声明为静态变量，以便其他类中可以通过类方法获得spring bean
	 */
	private static ApplicationContext context;
	@Autowired
	private TestOnStartup testOnStartup;
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		AppTestResourcesLoader.context = context;
		if(AppTestConfig.testOnStartup){
			testOnStartup.startTest(context);
		}
		
	}
	
	public static ApplicationContext getContext(){
		return context;
	}
	/**
	 * 获得spring bean 的静态方法
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		 return context.getBean(beanName);
	}

	public static <T> T getBean(String beanName,Class clazz){
		return (T)context.getBean(beanName, clazz);
	}
	
	
}
