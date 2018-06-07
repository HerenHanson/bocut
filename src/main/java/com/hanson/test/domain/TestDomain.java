package com.hanson.test.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hanson.test.config.AppTestConfig;
import com.hanson.test.handler.TestHandler;

public class TestDomain extends TestConfigationDomain {

	static Pattern varInPathPattern = Pattern.compile("[{](\\w+)[}]");
	
	
	protected boolean varInPathFlag;
	
	public boolean doTest(TestCaseDomain testCase){
		String url  = getUrl();
		//拼接完整的请求路径
		String requestUrl= AppTestConfig.urlPrefix+url;
		Set<String> varInPath = new HashSet<String>();
		if(varInPathFlag){
 			//参数在路径中的请求
			Matcher match = varInPathPattern.matcher(url);
			
			while(match.find()){
				String key = match.group(1);
				varInPath.add(key);
			}
		}
		return  testCase.test(requestUrl, varInPathFlag, varInPath);
	}
	public boolean doTest() {
		try {
			
		String url  = getUrl();
			//拼接完整的请求路径
			String requestUrl= AppTestConfig.urlPrefix+url;
			
			Set<String> varInPath = new HashSet<String>();
 			//参数在路径中的请求
			Matcher match = varInPathPattern.matcher(url);
			
			while(match.find()){
				String key = match.group(1);
				varInPath.add(key);
			}
			if(varInPath.size()>0){
				varInPathFlag = true;
			}
			int count =0,total = testCases.size();
			for(TestCaseDomain caseDomain : testCases){
				caseDomain.setMethod(getMethod());
				if(caseDomain.test(requestUrl,varInPathFlag,varInPath))count++;
			}
			return count == total;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	//该接口下的所有测试用例
	private List<TestCaseDomain> testCases;
	private List<BaseParamDomain> request;
	private List<BaseParamDomain> response;
	
	public List<TestCaseDomain> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<TestCaseDomain> testCases) {
		this.testCases = testCases;
	}
	public List<BaseParamDomain> getRequest() {
		return request;
	}
	public void setRequest(List<BaseParamDomain> request) {
		this.request = request;
	}
	public List<BaseParamDomain> getResponse() {
		return response;
	}
	public void setResponse(List<BaseParamDomain> response) {
		this.response = response;
	}

	
	
	
}
