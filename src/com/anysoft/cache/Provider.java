package com.anysoft.cache;


/**
 * 缓存对象的提供者
 * @author duanyy
 * @since 1.0.6
 * 
 * @version 1.3.0 [20140727 duanyy]
 * - Cachable修正类名为Cacheable
 *  
 */
public interface Provider<data extends Cacheable> {
	
	/**
	 * 装入缓存对象
	 * @param id 对象id
	 * @return
	 * @deprecated
	 */
	public data load(String id);
	
	/**
	 * 装入对象
	 * @param id 对象ID
	 * @param cacheAllowed 允许装入缓存的对象
	 * @return
	 */
	public data load(String id,boolean cacheAllowed);
	
	/**
	 * 设置Change监听器
	 * @param listener
	 */
	public void addChangeListener(ChangeAware<data> listener);
	
	/**
	 * 清除指定的Change监听器
	 * @param listener
	 */
	public void removeChangeListener(ChangeAware<data> listener);
}
