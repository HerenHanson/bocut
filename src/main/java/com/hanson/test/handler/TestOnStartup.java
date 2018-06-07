package com.hanson.test.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hanson.test.domain.TestDomain;

/**
 * 启动时执行测试的控制类
 * 这里将该类声明为spring管理的bean，是为了处理文档输出等情况下，
 * 可以通过织入切面的方式，不至于侵入到测试类中，以消除类间的耦合性。
 *
 */
@Component
public class TestOnStartup {

	private Log log = LogFactory.getLog(TestOnStartup.class);
	
	public  void startTest(ApplicationContext context){
		System.out.println("current thread id-->"+Thread.currentThread().getId());
		log.warn("--------集成测试开始--------");
		String beanNames[] = context.getBeanNamesForType(TestDomain.class);
		int count=0,total = beanNames.length;
		long start = System.currentTimeMillis();
		for(String bName : beanNames){
			TestDomain handler = context.getBean(bName, TestDomain.class);
			if(handler.doTest()){
				count++;
			}
		}
		long end = System.currentTimeMillis();
		log.warn("--------集成测试结束--------");
		log.warn("---->共测试接口"+total+"个,测试通过"+count+"个<------");
		log.warn("--------总计耗时---->>>>"+(end-start)/1000+"S<<<<<------");

	}
}
