package com.anysoft.loadbalance.impl;

import java.util.List;
import java.util.Random;

import com.anysoft.loadbalance.Load;
import com.anysoft.loadbalance.LoadBalance;
import com.anysoft.loadbalance.LoadContext;
import com.anysoft.util.Properties;

public class Rand<load extends Load> implements LoadBalance<load> {

	public Rand(Properties props){
		
	}
	
	@Override
	public load select(String key, Properties props, List<load> loads,
			LoadContext<load> ctx) {
		int size = loads.size();
		if (size <= 0) return null;		
		int index = r.nextInt(size) % size;		
		return loads.get(index);
	}

	public static Random r = new Random();
}
