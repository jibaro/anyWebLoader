package com.anysoft.cache;


/**
 * 缓存对象的提供者
 * @author duanyy
 * @since 1.0.6
 */
public interface Provider<data extends Cachable> {
	
	/**
	 * 装入缓存对象
	 * @param id 对象id
	 * @return
	 */
	public data load(String id);
	
	/**
	 * 设置Change监听器
	 * @param listener
	 */
	public void addChangeListener(ChangeAware<data> listener);
}
