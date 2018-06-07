package com.hanson.test.aop;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.hanson.test.domain.BaseParamDomain;
import com.hanson.test.domain.TestCaseDomain;
import com.hanson.test.domain.TestDomain;
import com.hanson.test.handler.BeforeTestHandler;
import com.hanson.test.handler.RequestParamParsor;
import com.hanson.test.handler.ResultParser;
import com.hanson.test.handler.TestSuccessHandler;
import com.hanson.test.request.service.IRequestService;

/**
 * 测试用例相关默认服务设置
 * @author Administrator
 *
 */
@Component
@Aspect
public class TestCaseHandlerInitor {

	@Resource(name="defaultRequestParamParsor")
	private RequestParamParsor defaultRequestParamParsor;
	@Resource(name="defaultResultParsor")
	private ResultParser defaultResultParsor;
	@Resource(name="defaultRequestService")
	private IRequestService defaultRequest;
	@Resource(name="defaultBeforeTestHandler")
	private BeforeTestHandler defaultBeforeTestHandler;
	@Resource(name="defaultTestSuccessHandler")	
	private TestSuccessHandler defaultTestSuccessHandler;
	
	private static ThreadLocal<List<BaseParamDomain>> localParamDomain = new ThreadLocal<List<BaseParamDomain>>();
	
	@Before(value="execution(* com.hanson.test.handler.TestHandler.doTest(..))")
	public void cacheRequestParamFomatter(JoinPoint jp){
		TestDomain testCase = (TestDomain) jp.getTarget();
		List<BaseParamDomain> list  = testCase.getRequest();
		localParamDomain.set(list);
		System.out.println("current thread id-->"+Thread.currentThread().getId());
	}
	
	@Before(value="execution(* com.hanson.test.domain.TestCaseDomain.test(..))")
	public void setHandler(JoinPoint jp){
		TestCaseDomain testCase = (TestCaseDomain) jp.getTarget();
		//以下代码设置默认处理器
		if(null == testCase.getRequestHandler()){
			testCase.setRequestHandler(defaultRequest);
		}
		if(null== testCase.getRequestParsor()){
			testCase.setRequestParsor(defaultRequestParamParsor);
		}
		if(null== testCase.getResultParser()){
			testCase.setResultParser(defaultResultParsor);
		}
		if(null==testCase.getBeforeTest()){
			testCase.setBeforeTest(defaultBeforeTestHandler);
		}
		if(null==testCase.getSuccessHandler()){
			testCase.setSuccessHandler(defaultTestSuccessHandler);
		}
		
//		Map<String,String> param= testCase.getParam();
		
//		System.out.println("aop---"+testCase.getCode());
//		System.out.println("current thread id-->"+Thread.currentThread().getId());
	}
	
	/**
	 * 格式化参数
	 * @param param
	 */
	private void formatParam(Map param){
		List<BaseParamDomain> list  = localParamDomain.get();
		for(BaseParamDomain domain:list){
			String type = domain.getType();
			if("string".equalsIgnoreCase(type)){
				continue;
			}
			String key = domain.getKey();
			Object v = param.get(key);
			if(null==v){
				continue;
			}
			if(v instanceof String){
				String tempV = v.toString();
				if("int".equalsIgnoreCase(type)){
					param.put(key, Integer.valueOf(tempV));
				}else if("long".equals(type)){
					param.put(key, Long.valueOf(tempV));
				}else if("float".equalsIgnoreCase(type)){
					param.put(key, Float.valueOf(tempV));
				}else if("double".equalsIgnoreCase(type)){
					param.put(key, Double.valueOf(tempV));
				}else if("short".equalsIgnoreCase(type)){
					param.put(key, Short.valueOf(tempV));
				}else if("byte".equalsIgnoreCase(type)){
					param.put(key, Byte.valueOf(tempV));
				}else if("boolean".equalsIgnoreCase(type)){
					param.put(key, Boolean.valueOf(tempV));
				}else if("char".equalsIgnoreCase(type)){
					param.put(key, tempV.charAt(0));
				}
			}
		}
	}
	
}
