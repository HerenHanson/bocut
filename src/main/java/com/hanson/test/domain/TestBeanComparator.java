package com.hanson.test.domain;

import java.util.List;

import com.hanson.test.util.FormatUtils;

public class TestBeanComparator implements Comparable<TestBeanComparator> {

	
	String beanName;
	TestDomain testConfig;
	String no;
	
	int successCount=0;
	int errorCount=0;
	int totalCount;
	int judgeCount=0;
	
	public void statisTestResult(){
		List<TestCaseDomain> testCases = testConfig.getTestCases();
		totalCount = testCases.size();
		for(TestCaseDomain testCase : testCases){
			
			if(testCase.isSuccess()){
				successCount++;
			}else{
				errorCount++;
			}
			if(testCase.isJudge()){
				judgeCount++;
			}
		}
	}
	
	@Override
	public int compareTo(TestBeanComparator o) {
		return no.compareTo(o.getNo());
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public TestDomain getTestConfig() {
		return testConfig;
	}

	public String getNo() {
		return no;
	}
	public void setTestConfig(TestDomain testConfig) {
		
		this.testConfig = testConfig;
		this.no = testConfig.getNo();
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getJudgeCount() {
		return judgeCount;
	}

	public void setJudgeCount(int judgeCount) {
		this.judgeCount = judgeCount;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
