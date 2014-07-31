package com.anysoft.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * ChangeAwareHub
 * 
 * @author duanyy
 *
 * @param <data>
 */
public class ChangeAwareHub<data extends Cacheable> implements ChangeAware<data> {

	@Override
	public void changed(String id, data obj) {
		for (ChangeAware<data> listener:listeners){
			if (listener != null){
				listener.changed(id, obj);
			}
		}
	}

	/**
	 * 清除所有注册的Listener
	 */
	public void clear(){
		listeners.clear();
	}
	
	/**
	 * 注册Listener
	 * 
	 * @param listener
	 */
	public void add(ChangeAware<data> listener){
		listeners.add(listener);
	}

	public void remove(ChangeAware<data> listener){
		listeners.remove(listener);
	}
	
	protected List<ChangeAware<data>> listeners = new ArrayList<ChangeAware<data>>();
}
