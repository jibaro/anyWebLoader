package com.anysoft.loadbalance.impl;

import java.util.List;

import com.anysoft.loadbalance.Load;
import com.anysoft.loadbalance.LoadBalance;
import com.anysoft.loadbalance.LoadContext;
import com.anysoft.util.Properties;

public class Priority<load extends Load> implements LoadBalance<load> {

	public Priority(Properties props){
		
	}	
	
	@Override
	public load select(String key,Properties props, List<load> loads,
			LoadContext<load> ctx) {
		int size = loads.size();
		if (size <= 0) return null;
			
		int highestIndex = 0;
		int highestPriority = 0;
		
		for (int i = 0 ; i < size; i ++){
			int _p = loads.get(i).getPriority();
			if (_p > highestPriority){
				highestIndex = i;
				highestPriority = _p;
			}
		}
		
		return loads.get(highestIndex);
	}

}
