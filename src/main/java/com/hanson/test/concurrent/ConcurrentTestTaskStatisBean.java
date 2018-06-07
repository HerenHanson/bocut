package com.hanson.test.concurrent;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentTestTaskStatisBean {
	public volatile boolean shutDownFlag;
	AtomicInteger counter = new AtomicInteger();
	AtomicInteger errorCounter = new AtomicInteger();
	ConcurrentHashMap<Long, Set<Integer>> map = new ConcurrentHashMap<Long, Set<Integer>>();
	public int incrible(){
		return counter.incrementAndGet();
	}
	
	public int incribleError(){
		return errorCounter.incrementAndGet();
	}
	
	public int count(){
		return counter.get();
	}
	public int errorCount(){
		return errorCounter.get();
	}
	
	public long totalTimes(){
		long time=0;
		for(Entry<Long, Set<Integer>> e: map.entrySet()){
			time+=(e.getKey()*e.getValue().size());
		}
		return time;
	}
	
	public void add(Long time,int index){
		Set<Integer> set = map.get(time);
		if(null==set){
			synchronized (time) {
				set = map.get(time);
				if(null==set){
					set = new HashSet<Integer>();
					map.put(time, set);
				}
			}
		}
		set.add(index);
	}
	
}
