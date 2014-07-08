package com.anysoft.loadbalance;

import java.util.List;

import com.anysoft.util.Properties;

/**
 * LoadBalance接口
 * 
 * @author duanyy
 *
 * @param <load>
 * 
 * @since 1.2.0
 */
public interface LoadBalance<load extends Load> {
	
	/**
	 * 选择负载
	 * @param props 环境
	 * @param loads　负载　
	 * @param stats　负载统计
	 * @return
	 */
	public load select(String key,Properties props,List<load> loads,LoadContext<load> ctx);

}
