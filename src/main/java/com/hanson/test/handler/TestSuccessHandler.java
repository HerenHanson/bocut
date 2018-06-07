package com.hanson.test.handler;

import java.util.Map;

/**
 * 测试用例调用成功后，后续处理的情况
 * 比如将登陆后的token缓存起来
 * @author mac
 *
 */
public interface TestSuccessHandler {

	public boolean handlerResult(Map map);
}
