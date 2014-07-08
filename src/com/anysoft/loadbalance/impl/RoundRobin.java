package com.anysoft.loadbalance.impl;

import java.util.List;

import com.anysoft.loadbalance.Load;
import com.anysoft.loadbalance.LoadBalance;
import com.anysoft.loadbalance.LoadContext;
import com.anysoft.util.Properties;


/**
 * 轮训调度
 * 
 * @author duanyy
 *
 * @param <load>
 */
public class RoundRobin<load extends Load> implements LoadBalance<load> {
	
	public RoundRobin(Properties props){
		
	}
	
	@Override
	public load select(String key,Properties props, List<load> loads,
			LoadContext<load> ctx) {
		int size = loads.size();
		if (size <= 0) return null;

		load found = loads.get(currentSelect % size);
		
		synchronized (this){
			currentSelect ++;
			if (currentSelect >= size){
				currentSelect = 0;
			}
		}
		return found;
	}

	protected int currentSelect = 0;
}
