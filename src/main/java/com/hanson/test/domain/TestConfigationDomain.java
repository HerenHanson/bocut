package com.hanson.test.domain;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hanson.test.config.AppTestConfig;
import com.hanson.test.handler.TestHandler;
import com.hanson.test.util.FormatUtils;

/**
 * 定义被测试对象
 * @author mac
 *
 */
public  class TestConfigationDomain {

	//接口路径，不带前缀，如 user/login
	private	String url;
	//接口编号,如1.1,用于排序
	private String no;
	//接口标题，如登录
	private String title;
	//描述,对调用该接口的一些说明
	private String desc;
	
	private String method="POST";
	//返回结果示例
	private String result;
	
	//变更标志
	//add or update
	private String modifyFlag="normal";
	
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		String th = FormatUtils.format(result);
		this.result = th;
	}
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	

	
	
}
