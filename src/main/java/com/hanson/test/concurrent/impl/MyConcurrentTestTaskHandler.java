package com.hanson.test.concurrent.impl;

import java.io.IOException;
import java.util.Map;

import javax.websocket.Session;

import com.hanson.test.concurrent.ConcurrentTestTaskHandler;
import com.hanson.test.domain.TestCaseDomain;
import com.hanson.test.domain.TestDomain;
import com.hanson.test.loader.AppTestResourcesLoader;
import com.hanson.test.request.service.IRequestService;
import com.hanson.test.request.service.impl.ConcurrentHttpResuqestServiceImpl;
import com.hanson.test.websocket.MyWebsocket;

public class MyConcurrentTestTaskHandler implements ConcurrentTestTaskHandler {

	MyWebsocket socket;
	TestCaseDomain testCase;
	Map testCaseBeforeParam;
	IRequestService testCaseBeforeRequest;
//	void sendMessage(String msg){
//		try {
//			session.getAsyncRemote().sendText(msg);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
	
	public MyConcurrentTestTaskHandler(MyWebsocket socket,
			TestCaseDomain testCase, Map testCaseBeforeParam,
			IRequestService testCaseBeforeRequest) {
		this.socket =  socket;
		this.testCase=testCase;
		this.testCaseBeforeParam = testCaseBeforeParam;
		this.testCaseBeforeRequest = testCaseBeforeRequest;
	
	}


	@Override
	public void onTaskStart(long startTime) {
		socket.sendMessage("压力测试开始时间--->"+startTime);
	}

	@Override
	public void onTaskFinished(long endTime, long costTime, 
			Double avgTime,Double avgConcurrent,
			int testCount,int errorCount) {
		
		
		//还原
		testCase.setParam(testCaseBeforeParam);
		testCase.setRequestHandler(testCaseBeforeRequest);
		StringBuilder sb  = new StringBuilder();
		sb.append("压力测试结束时间--->").append(endTime).append(",\n总耗时--->").
			append(costTime).append("ms,\n平均耗时--->").append(avgTime).
			append("ms,\n平均每秒吞吐量--->").append(avgConcurrent).
			append("个,\n总共处理--->").append(testCount).append("个,失败--->").append(errorCount).append("个");
		socket.sendMessage(sb.toString());
		
	}

}
