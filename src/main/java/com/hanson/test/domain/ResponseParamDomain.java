package com.hanson.test.domain;

import java.util.List;

public class ResponseParamDomain extends BaseParamDomain{

	private List<BaseParamDomain> children;

	public List<BaseParamDomain> getChildren() {
		return children;
	}

	public void setChildren(List<BaseParamDomain> children) {
		this.children = children;
	}
	
	
	
}
