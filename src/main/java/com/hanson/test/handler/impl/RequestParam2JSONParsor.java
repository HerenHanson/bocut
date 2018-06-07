package com.hanson.test.handler.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hanson.test.handler.RequestParamParsor;

//@Component("defaultRequestParamParsor")
public class RequestParam2JSONParsor implements RequestParamParsor {

	@Override
	public String parseRequestParam(Map param) {
		return  JSON.toJSONString(param);
	}

}
