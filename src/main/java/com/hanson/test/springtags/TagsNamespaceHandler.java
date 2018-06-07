package com.hanson.test.springtags;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class TagsNamespaceHandler  extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("reqParam", new MyRequestParamParser());
		registerBeanDefinitionParser("resParam", new MyResponseParamParser());
	}

}
