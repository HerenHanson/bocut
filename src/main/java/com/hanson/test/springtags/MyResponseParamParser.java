package com.hanson.test.springtags;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.hanson.test.domain.BaseParamDomain;
import com.hanson.test.domain.ResponseParamDomain;
import com.hanson.test.util.ValidateUtils;

public class MyResponseParamParser extends AbstractSimpleBeanDefinitionParser{
	

	@Override
	protected void doParse(Element element, ParserContext context,
			BeanDefinitionBuilder builder) {
		super.doParse(element, context, builder);
		List<Element> property =  DomUtils.getChildElementsByTagName(element, "resParam");
		if(!ValidateUtils.isEmpty(property)){
//			List<BeanDefinition> childrenArray = new ArrayList<BeanDefinition>();
		
//			Element list =  DomUtils.getChildElementByTagName(property,"list");
//			List<Element> children =  DomUtils.getChildElements(list);
			ManagedArray childrenArray  = new ManagedArray("com.hanson.test.domain.ResponseParamDomain", property.size());
			childrenArray.setMergeEnabled(true);
			for(Element el : property){
				BeanDefinition bean = context.getDelegate()  
	                .parseCustomElement(  
	                        el,builder.getRawBeanDefinition());
				childrenArray.add(bean);
			}
			builder.addPropertyValue("children", childrenArray);
		}
	}
	
	@Override
	protected Class<?> getBeanClass(Element element) {
		return ResponseParamDomain.class;
	}
}
