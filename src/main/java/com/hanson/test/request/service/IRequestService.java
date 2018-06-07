package com.hanson.test.request.service;


/**
 * 请求处理器
 * 绝大多数情况下，我们都是常规的http请求，
 * 但有些特殊的场景，比如上传图片等，可通过实现该接口进行扩展
 * @author mac
 *
 */
public interface IRequestService {

	public String doRequest(String url ,String method,String param) throws Exception;
	
}
