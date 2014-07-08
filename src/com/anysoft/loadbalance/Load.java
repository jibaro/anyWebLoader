package com.anysoft.loadbalance;

import com.anysoft.util.JsonSerializer;
import com.anysoft.util.XmlSerializer;


/**
 * 负载接口
 * 
 * @author duanyy
 * 
 * @since 1.2.0
 * 
 */
public interface Load extends XmlSerializer,JsonSerializer{
	/**
	 * 获取负载的标示ID
	 * @return
	 */
	public String getId();
	
	/**
	 * 获取本负载的权重
	 * @return
	 */
	public int getWeight();
	
	/**
	 * 获取本负载的优先级
	 * @return
	 */
	public int getPriority();
}
