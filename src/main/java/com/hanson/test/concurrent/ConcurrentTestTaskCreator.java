package com.hanson.test.concurrent;

import java.util.Map;

import javax.websocket.Session;

import com.hanson.test.concurrent.impl.MyConcurrentTestTaskHandler;
import com.hanson.test.domain.TestCaseDomain;
import com.hanson.test.domain.TestDomain;
import com.hanson.test.loader.AppTestResourcesLoader;
import com.hanson.test.request.service.IRequestService;
import com.hanson.test.request.service.impl.ConcurrentHttpResuqestServiceImpl;
import com.hanson.test.websocket.MyWebsocket;

/**
 * 
 * @author Administrator
 *
 */
public class ConcurrentTestTaskCreator {
	Map param;
	Map testCaseBeforeParam;
	TestCaseDomain testCase;
	TestDomain testConfigation;
	IRequestService testCaseBeforeRequest;
	ConcurrentHttpResuqestServiceImpl concurrentRequest;
	public ConcurrentTestTaskCreator(Map map,MyWebsocket socket
){
		int threadCount =Integer.valueOf((String) map.get("threadCount"));
		int requestCount = Integer.valueOf((String)map.get("requestCount"));
		param = (Map) map.get("param");
		String beanName= (String) param.get("beanName");
		testConfigation = (TestDomain) AppTestResourcesLoader.getBean(beanName);
		param.remove("beanName");
		testCase = testConfigation.getTestCases().get(0);
		
		testCaseBeforeParam = testCase.getParam();
		testCase.setParam(param);
		testCaseBeforeRequest = testCase.getRequestHandler();
		
		
		concurrentRequest = new ConcurrentHttpResuqestServiceImpl(threadCount, requestCount, testCaseBeforeRequest,
				new MyConcurrentTestTaskHandler(socket,testCase,testCaseBeforeParam,testCaseBeforeRequest),
				socket);
		testCase.setRequestHandler(concurrentRequest);
		testConfigation.doTest(testCase);
	}
	
	public void start(){
//		testConfigation.doTest(testCase);
		concurrentRequest.startup();
	}
	
	public void shutDown(){
		concurrentRequest.shutDown();
		concurrentRequest=null;
	}
}
