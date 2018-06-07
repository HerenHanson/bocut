package com.hanson.test.handler.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hanson.test.handler.ResultParser;

//@Component("defaultResultParsor")
public class JSONResultParsor implements ResultParser {

	@Override
	public <T extends Map> T parseResult(String data) {
		return (T) JSON.parseObject(data);
	}

}
