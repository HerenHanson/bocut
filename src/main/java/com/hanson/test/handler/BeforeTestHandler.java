package com.hanson.test.handler;

import java.util.Map;

/**
 * 在测试开始调用接口前对参数的处理
 * 比如需要追加token
 * 或者对参数进行加密
 * 
 * 或者某些接口的执行需要前置条件
 * @author mac
 *
 */
public interface BeforeTestHandler {

	
	public void handler(Map param);
	
}
