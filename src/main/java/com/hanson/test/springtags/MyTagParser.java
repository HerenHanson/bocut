package com.hanson.test.springtags;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class MyTagParser extends AbstractSimpleBeanDefinitionParser{
	

	@Override
	protected void doParse(Element arg0, ParserContext arg1,
			BeanDefinitionBuilder arg2) {
		// TODO Auto-generated method stub
		super.doParse(arg0, arg1, arg2);
	}
	
	@Override
	protected Class<?> getBeanClass(Element element) {
		return super.getBeanClass(element);
	}
}
