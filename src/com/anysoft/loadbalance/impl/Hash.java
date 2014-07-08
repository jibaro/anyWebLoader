package com.anysoft.loadbalance.impl;

import java.util.List;

import com.anysoft.loadbalance.Load;
import com.anysoft.loadbalance.LoadBalance;
import com.anysoft.loadbalance.LoadContext;
import com.anysoft.util.Properties;

public class Hash<load extends Load> implements LoadBalance<load> {

	public Hash(Properties props){
		
	}	
	
	@Override
	public load select(String key,Properties props, List<load> loads,
			LoadContext<load> ctx) {
		int size = loads.size();
		if (size <= 0) return null;
		
		if (key == null || key.length() <= 0)
			return loads.get(0);
		
		int hashcode = key.hashCode();
		int index = (hashcode<0?-hashcode:hashcode)%size;
		return loads.get(index);
	}

}
