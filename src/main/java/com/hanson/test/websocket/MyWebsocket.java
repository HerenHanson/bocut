package com.hanson.test.websocket;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hanson.test.concurrent.ConcurrentTestTask;
import com.hanson.test.concurrent.ConcurrentTestTaskCreator;
import com.hanson.test.concurrent.impl.MyConcurrentTestTaskHandler;

@ServerEndpoint(value="/test/concurrent")
/**
 * tomcat部署注释component
 * @author Administrator
 *
 */
//@Component
public class MyWebsocket {
	 //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//    private static CopyOnWriteArraySet<MyWebsocket> webSocketSet = new CopyOnWriteArraySet<MyWebsocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
//        session.setMaxTextMessageBufferSize(2000);
//        webSocketSet.add(this);     //加入set中
        System.out.println("有新连接加入");
            sendMessage("welcome");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
//        webSocketSet.remove(this);  //从set中删除
    	System.out.println("有一连接关闭" );
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        try {
        	Map param  = JSON.parseObject(message);
        	String method = (String) param.get("method");
        	System.out.println(method);
        	Method m = this.getClass().getDeclaredMethod(method, Map.class);
        	m.invoke(this, param);
//			sendMessage("we have recevice your msg"+message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    

    private ConcurrentTestTaskCreator concurrentTask;
    public void stopConcurrent(Map map){
    	concurrentTask.shutDown();
    	concurrentTask = null;
    }
    
    public void startConcurrent(Map map){
    	if(null!=concurrentTask){
    		return ;
    	}
    	
    	
    	concurrentTask = new ConcurrentTestTaskCreator(map,this);
    	concurrentTask.start();
    }
    
    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public synchronized void sendMessage(String message) {
    	
    	
    		System.out.println(message);
    		try {
    			
    			this.session.getBasicRemote().sendText(message);
    		} catch (Exception e) {
    			e.printStackTrace();
    			try {
					
    				this.session.getAsyncRemote().sendText(message);
				} catch (Exception e2) {
					// TODO: handle exception
					e.printStackTrace();
				}
    			// TODO: handle exception
    		}
    }
}
