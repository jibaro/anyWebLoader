package com.anysoft.loadbalance;

import com.anysoft.util.BaseException;
import com.anysoft.util.Factory;

/**
 * 工厂类
 * @author duanyy
 *
 * @param <load>
 * 
 * @since 1.2.0
 */
public class LoadBalanceFactory<load extends Load> extends Factory<LoadBalance<load>> {
	public String getClassName(String _module) throws BaseException{
		if (_module.indexOf('.') < 0){
			return "com.anysoft.loadbalance.impl." + _module;
		}
		return _module;
	}
}
