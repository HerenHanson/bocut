package com.hanson.test.domain;
/**
 * 请求参数，返回参数公共部分抽象类
 * 
 * @author mac
 *
 */
public  class BaseParamDomain {

	//参数的键名
	private String key;
	//参数数据类型
	private String type;
	//是否允许空值
	private boolean allowEmpty=true;
	//参数名称
	private String name;
	//参数说明
	private String desc;
	//参数默认值／或示例值
	private String value;
	//废弃标志
	private boolean deprecated;
	//是否新增
	private boolean addtion;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAllowEmpty() {
		return allowEmpty;
	}
	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isDeprecated() {
		return deprecated;
	}
	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}
	public boolean isAddtion() {
		return addtion;
	}
	public void setAddtion(boolean addtion) {
		this.addtion = addtion;
	}
	
	
	
	//参数的正则表达式
	//因为测试用例中可能需要设置非法的参数
	//故而未实现对该属性的处理
//	private String reg;
	
	
	
	
}
