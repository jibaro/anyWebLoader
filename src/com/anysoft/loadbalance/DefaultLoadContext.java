package com.anysoft.loadbalance;

import java.util.Hashtable;

import com.anysoft.util.DefaultProperties;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;


/**
 * 缺省的LoadContext
 * @author duanyy
 *
 * @param <load>
 * 
 * @since 1.2.0
 */
public class DefaultLoadContext<load extends Load> extends DefaultProperties 
implements LoadContext<load> 
{	
	protected long cycle = 30 * 60 * 1000;
	
	public DefaultLoadContext(Properties props){
		super("Default",props);
		cycle = PropertiesConstants.getLong(props, "loadbalance.cycle", cycle);
	}
	@Override
	public LoadStat getLoadStat(String id) {
		LoadStat found = stats.get(id);
		if (found != null)
			return found;

		synchronized (this){
			if (found == null){
				found = new LoadStat(id,cycle);
				stats.put(id, found);				
			}
			return found;
		}
	}

	@Override
	public LoadBalance<load> createLoadBalace() {
		String className = this.GetValue("loadbalance","${loadbalance.module}");
		if (className == null || className.length() <= 0){
			className = "com.anysoft.loadbalance.impl.RoundRobin";
		}
		LoadBalanceFactory<load> factory = new LoadBalanceFactory<load>();		
		return factory.newInstance(className, this);
	}

	protected Hashtable<String,LoadStat> stats = new Hashtable<String,LoadStat>();
}
