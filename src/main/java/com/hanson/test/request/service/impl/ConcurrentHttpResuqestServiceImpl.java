package com.hanson.test.request.service.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

import com.hanson.test.concurrent.ConcurrentTestTask;
import com.hanson.test.concurrent.ConcurrentTestTaskHandler;
import com.hanson.test.concurrent.ConcurrentTestTaskStatisBean;
import com.hanson.test.request.service.IRequestService;
import com.hanson.test.websocket.MyWebsocket;

public class ConcurrentHttpResuqestServiceImpl implements IRequestService {

	private IRequestService request;
	int threadCount;
	int requestCount;
	ExecutorService service;
	long startTime;	
	ConcurrentTestTaskHandler handler;
	CountDownLatch begin;
	CountDownLatch end;
	MyWebsocket socket;
	
	
	
	ConcurrentTestTaskStatisBean statis  = new ConcurrentTestTaskStatisBean();

	public ConcurrentHttpResuqestServiceImpl(
			int threadCount,
			int requestCount,
			IRequestService request,
			ConcurrentTestTaskHandler handler,
			MyWebsocket socket){
//		service = Executors.newFixedThreadPool(threadCount);
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(requestCount);
		service=	new ThreadPoolExecutor(threadCount, threadCount, 30, TimeUnit.SECONDS, workQueue);
		
		this.threadCount=threadCount;
		this.requestCount = requestCount;
		this.request = request;
		this.handler=handler;
		this.socket = socket;
	}
	@Override
	public String doRequest(String url, String method, String param) {
//		String re = request.doRequest(url, method, param);
//		return re;
		begin = new CountDownLatch(1); 
		end = new CountDownLatch(threadCount);
		for(int i=0;i<threadCount;i++){
			service.execute(new ConcurrentTestTask(i,requestCount, socket, request, 
					url, method, param,begin,end,statis));
		}
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
		}
		
	       
		
		
		
		return null;
	}

	public void startup(){
		startTime =  System.currentTimeMillis();
		handler.onTaskStart(startTime);
		 //放开所有线程
		begin.countDown(); 
/*		 try {  
	        	//主线程等待其它线程执行完成
	            end.await();  
	        } catch (InterruptedException e) {
	            e.printStackTrace();  
	        } finally {  
	        	System.out.println("all thread has finished");
	        }*/
		 service.shutdown();
		 Runnable run  = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
			        	//主线程等待其它线程执行完成
			           while(true){
			        	   if(end.getCount()==0){
			        		   report();
			        		   break;
			        	   }
			           }
				
			}
		};
		Thread thread = new Thread(run);
		thread.setDaemon(true);
		thread.start();
	}
	
	
	private void report(){
		long endTime = System.currentTimeMillis();
	    long costTime  = statis.totalTimes();
	    int count = statis.count();
	    
	    double avgTime = costTime/Double.valueOf(count);
	    double concurent = Double.valueOf(count)/(endTime-startTime)*1000;
	    
	    handler.onTaskFinished(endTime, costTime, avgTime,concurent,count,statis.errorCount());
	}
	
	public void shutDown(){
		statis.shutDownFlag=true;
		
		List<Runnable> activeThreads =  service.shutdownNow();
		System.out.println("活跃线程数"+activeThreads.size());
		report();
	}
	
	
}
