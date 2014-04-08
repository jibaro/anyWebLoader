package com.anysoft.cache;


/**
 * Change监听器
 * @author duanyy
 * @since 1.0.6
 */
public interface ChangeAware<data extends Cachable> {
	
	/**
	 * changed
	 * @param id 对象ID
	 * @param obj 对象
	 */
	public void changed(String id,data obj);
}
