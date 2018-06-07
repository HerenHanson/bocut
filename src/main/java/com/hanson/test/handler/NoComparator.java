package com.hanson.test.handler;

import java.util.Comparator;

import com.hanson.test.domain.TestBeanComparator;

public class NoComparator implements Comparator<TestBeanComparator> {

	@Override
	public int compare(TestBeanComparator o1, TestBeanComparator o2) {
		String _no1 = o1.getNo();
		String _no2 = o2.getNo();
		String[] nos1 = _no1.split("\\.");
		String[] nos2 = _no2.split("\\.");
		
		for(int i=0 ; i< (nos1.length<nos2.length?nos1.length:nos2.length) ; i++){
			int t1 = Integer.parseInt(nos1[i]);
			int t2 = Integer.parseInt(nos2[i]);
			
			if(t1>t2){
				return 1;
			}else if(t1<t2){
				return -1;
			}
		}
		return 0;
	}

}
