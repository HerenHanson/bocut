package com.hanson.test.domain;

import java.util.Map;
import java.util.Set;

import com.hanson.test.config.AppTestConfig;
import com.hanson.test.handler.BeforeTestHandler;
import com.hanson.test.handler.RequestParamParsor;
import com.hanson.test.handler.ResultParser;
import com.hanson.test.handler.TestSuccessHandler;
import com.hanson.test.request.service.IRequestService;

/**
 * 定义测试用例
 * @author mac
 *
 */
public class TestCaseDomain {

	//测试用编号
	private String code;
	//测试用例标题
	private String title;
	//测试用例说明性文字
	private String desc;
	//请求参数
	private Map param; 
	//发起请求时的参数，不需要在xml中配置和
	private String requestParam;
	//是否需要人工校验
	private boolean judge;
	//缓存返回结果，用于在页面上显示
	private String result;
	
	private boolean outFlag  = true;
	private boolean success;
	
	private String method;
	//测试通过对应的返回值，
	//因为我们会设计一些测试用例来验证服务端对非法请求的处理情况
	//因此，测试用例是否通过并不一定总是成功的标志
	//比如，我们用错误的帐号登录系统时，希望后端返回失败而不是成功
	private String successCode;
	//需要在调用前的处理
	//比如在请求参数中添加token
	private BeforeTestHandler beforeTest;
	//需要使用特殊的请求处理
	//比如需要header，特殊的content-type
	private IRequestService requestHandler;
	//解析特殊的返回结果
	private ResultParser resultParser;
	//测试用例调用成功后的处理
	private TestSuccessHandler successHandler;
	//请求参数处理器，默认转为json格式的字符串
	private RequestParamParsor requestParsor;
	
	/**
	 * 
	 * @param requestUrl 拼接的完整请求路径
	 * @param flag 请求路径中是否存在参数
	 * @return
	 */
	public boolean test(String requestUrl, boolean flag,Set<String> varInPathKeys) {
//		if(judge){
//			return false;
//		}
		String url = requestUrl;
		if(flag){
			for(String key :varInPathKeys){
				String v = param.get(key)+"";
				url =	requestUrl.replaceAll("[{]"+key+"[}]", v);
			}
		}
		beforeTest.handler(param);
		String paramStr = null;
		if(null!=param&&param.size()>0){
			
			paramStr = requestParsor.parseRequestParam(param);
			requestParam = paramStr;
		}
		try{
			
			String result = requestHandler.doRequest(url, method, paramStr);
			setResult(result);
			Map map =  resultParser.parseResult(result);
			if(successCode.equals(map.get(AppTestConfig.appcodeKey))){
				successHandler.handlerResult(map);
				success=true;
				return true;
			}
			
		}catch(Exception e){
			setResult(e.getMessage());
			
		}
		
		return false;
	}

	public void setOutFlag(boolean outFlag) {
		this.outFlag = outFlag;
	}
	public boolean isSuccess() {
		return success;
	}
	public boolean isOutFlag() {
		return outFlag;
	}
	public boolean test(){
		return false;
	}

	public String getCode() {
		return code;
	}
	 

	public void setMethod(String method) {
		this.method = method;
	}
	public void setCode(String code) {
		this.code = code;
	}

public String getRequestParam() {
	return requestParam;
}
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Map<String, String> getParam() {
		return param;
	}


	public void setParam(Map<String, String> param) {
		this.param = param;
	}


	public boolean isJudge() {
		return judge;
	}


	public void setJudge(boolean judge) {
		this.judge = judge;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getSuccessCode() {
		return successCode;
	}


	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}


	public BeforeTestHandler getBeforeTest() {
		return beforeTest;
	}


	public void setBeforeTest(BeforeTestHandler beforeTest) {
		this.beforeTest = beforeTest;
	}


	public IRequestService getRequestHandler() {
		return requestHandler;
	}


	public void setRequestHandler(IRequestService requestHandler) {
		this.requestHandler = requestHandler;
	}


	public ResultParser getResultParser() {
		return resultParser;
	}


	public void setResultParser(ResultParser resultParser) {
		this.resultParser = resultParser;
	}


	public TestSuccessHandler getSuccessHandler() {
		return successHandler;
	}


	public void setSuccessHandler(TestSuccessHandler successHandler) {
		this.successHandler = successHandler;
	}


	public RequestParamParsor getRequestParsor() {
		return requestParsor;
	}


	public void setRequestParsor(RequestParamParsor requestParsor) {
		this.requestParsor = requestParsor;
	}
	
	

}
