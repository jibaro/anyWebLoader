package com.anysoft.loadbalance;

/**
 * 上下文
 * @author duanyy
 *
 * @since 1.2.0
 */
public interface LoadContext<load extends Load> {
	
	/**
	 * 获取指定Load的统计信息
	 * @param id
	 * @return
	 */
	public LoadStat getLoadStat(String id);
	
	/**
	 * 创建LoadBalance实例
	 * 
	 */
	public LoadBalance<load> createLoadBalace(); 
}
