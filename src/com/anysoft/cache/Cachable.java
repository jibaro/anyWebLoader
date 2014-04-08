package com.anysoft.cache;

import com.anysoft.util.JsonSerializer;
import com.anysoft.util.XmlSerializer;


/**
 * 可缓存
 * @author duanyy
 * @since 1.0.6
 */
public interface Cachable extends XmlSerializer,JsonSerializer{
	
	/**
	 * 获取缓存对象的ID
	 * 
	 * @return ID
	 */
	public String getId();
	
	/**
	 * 是否已经过期
	 * @return 
	 */
	public boolean isExpired();	
}
