package com.hanson.test.concurrent;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.websocket.Session;

import org.apache.commons.codec.language.bm.BeiderMorseEncoder;

import com.hanson.test.request.service.IRequestService;
import com.hanson.test.websocket.MyWebsocket;


/**
 * 压力测试
 * @author Administrator
 *
 */
public class ConcurrentTestTask extends Thread{

	int index;
	int requestCount;
	MyWebsocket socket;
	IRequestService request;
	String url ;
	String method;
	String param;
	CountDownLatch begin;
	CountDownLatch end;
	ConcurrentTestTaskStatisBean statis;
	public ConcurrentTestTask(int index,
						int requestCount,
						MyWebsocket socket,
						IRequestService request,
						String url,
						String method,
						String param,
						CountDownLatch begin,
						CountDownLatch end,
						ConcurrentTestTaskStatisBean statis){
		this.index = index;
		this.requestCount = requestCount;
		this.socket = socket;
		this.request = request;
		this.url = url;
		this.method = method;
		this.param = param;
		this.begin = begin;
		this.end = end;
		this.statis = statis;
	}
	@Override
	public void run() {
		try {
			begin.await();
			while(requestCount>0&&!statis.shutDownFlag){
				requestCount--;
				statis.incrible();
				long start = System.currentTimeMillis();
				StringBuilder sb = new StringBuilder();
				sb.append("处理请求").append(",请求参数--->\n").
				append(param).append("\n");
				try {
					
					String re = request.doRequest(url, method, param);
					sb.append("\t返回结果---->").append(re);
				} catch (Exception e) {
					sb.append("\t请求失败--->").append(e.getMessage());
					statis.incribleError();
				}
					sb.append("\n\t本次请求花费时间--->");
				long endTime = System.currentTimeMillis();
				Long cost = endTime - start;
				statis.add(cost, index);
				sb.append(cost).append("ms");
				sendMessage(sb.toString());
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			end.countDown();
		}
		
	}
	
	
	void sendMessage(String msg){
		socket.sendMessage(msg);
	}
	
	
	

	/*int threadCount;
	int requestCount;
	ExecutorService service;
	long startTime;
	ConcurrentTestTaskHandler handler;
	public  ConcurrentTestTask(int threadCount,int requestCount,ConcurrentTestTaskHandler handler){
		this.threadCount=threadCount;
		this.requestCount = requestCount;
		this.handler = handler;
		service = Executors.newFixedThreadPool(threadCount);
		
	}
	
	public void start(){
		CountDownLatch begin = new CountDownLatch(1); 
		CountDownLatch end = new CountDownLatch(requestCount);
		for(int i=0;i<requestCount;i++){
			service.execute(handler.addTask(i));
		}
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
		}
		startTime =  System.currentTimeMillis();
		handler.onTaskStart(startTime);
	        //放开所有线程
		begin.countDown(); 
		 try {  
	        	//主线程等待其它线程执行完成
	            end.await();  
	        } catch (InterruptedException e) {
	            e.printStackTrace();  
	        } finally {  
	        }
		 shutDown();
//	    service.shutdown(); 
//	    long endTime = System.currentTimeMillis();
//        long costTime  = endTime-startTime;
//        double avgTime = Double.valueOf(threadCount)/costTime;
//        handler.onTaskFinished(endTime, costTime, avgTime);
	}
	
	public void shutDown(){
		service.shutdown();
		long endTime = System.currentTimeMillis();
	    long costTime  = endTime-startTime;
	    double avgTime = Double.valueOf(threadCount)/costTime;
	       
	    handler.onTaskFinished(endTime, costTime, avgTime);
	}*/
	
}
