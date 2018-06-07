package com.hanson.test.handler.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.hanson.test.handler.TestSuccessHandler;
//@Component("defaultTestSuccessHandler")
public class DefaultTestSuccessHandler implements TestSuccessHandler {

	@Override
	public boolean handlerResult(Map map) {
		return false;
	}

}
