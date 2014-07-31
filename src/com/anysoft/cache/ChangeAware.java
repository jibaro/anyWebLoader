package com.anysoft.cache;


/**
 * Change监听器
 * @author duanyy
 * @since 1.0.6
 * @version 1.3.0 [20140727 duanyy]
 * - Cachable修正类名为Cacheable 
 */
public interface ChangeAware<data extends Cacheable> {
	
	/**
	 * changed
	 * @param id 对象ID
	 * @param obj 对象
	 */
	public void changed(String id,data obj);
}
