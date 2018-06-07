package com.hanson.test.concurrent;

public interface ConcurrentTestTaskHandler {

	
	void onTaskStart(long startTime);
	void onTaskFinished(long endTime,long costTime,Double avgTime,
			Double avgConcurrent,
			int testCount,int errorCount);
}
