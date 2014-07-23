package com.anysoft.pool;

import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;

/**
 * 缓冲池接口
 * 
 * @author duanyy
 * @since 1.1.0
 * 
 * @param <pooled> 缓冲池对象
 * 
 * @version 1.2.2 [20140722 duanyy]
 * - 可缓冲的对象改为AutoCloseable
 */
public interface Pool<pooled extends AutoCloseable> {
	
	/**
	 * 创建缓冲池
	 * 
	 * @param props 环境变量
	 */
	public void create(Properties props);
	
	/**
	 * 关闭缓冲池
	 */
	public void close();
	
	/**
	 * 从缓冲池中借出缓冲对象
	 * @param priority 优先级
	 * @return
	 * @throws BaseException
	 */
	public pooled borrowObject(int priority,int timeout) throws BaseException;
	
	/**
	 * 归还缓冲对象
	 * @param obj 缓冲对象
	 */
	public void returnObject(pooled obj);
}
