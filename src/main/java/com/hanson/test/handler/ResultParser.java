package com.hanson.test.handler;

import java.util.Map;

/**
 * 接口返回值解析器
 * 我们只实现了对json格式的返回数据的解析器
 * 可根据项目的实际情况实现自定义的解析器，比如解析xml
 * @author mac
 *
 */
public interface ResultParser {
	/**
	 * 测试用例通过map中appcodekey所指定的值来判断是通过测试
	 * @param data
	 * @return
	 */
	public <T extends Map> T parseResult(String data);

}
