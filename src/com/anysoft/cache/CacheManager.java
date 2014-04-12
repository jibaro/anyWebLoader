package com.anysoft.cache;

import java.util.Vector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.anysoft.util.Manager;


/**
 * 对象缓存管理器
 * 
 * @author duanyy
 * @since 1.0.6
 * @param <data> 缓存对象类
 * 
 * @version 1.0.7 [20140409 duanyy]
 * + 增加{@link com.anysoft.cache.CacheManager#_get(String)
 * + 增加{@link com.anysoft.cache.CacheManager#_add(String, Cachable)
 * 
 */
public class CacheManager<data extends Cachable> extends Manager<data> 
implements Provider<data>,ChangeAware<data> {

	/**
	 * a logger of log4j
	 */
	protected static Logger logger = LogManager.getLogger(CacheManager.class);
	/**
	 * 委托的Provider
	 */
	protected Provider<data> provider = null;
	
	/**
	 * 锁对象
	 */
	protected Object lock = new Object();
	
	/**
	 * Constructor
	 */
	public CacheManager(){
		
	}
	
	/**
	 * Constructor
	 * @param _provider 委托的Provider
	 */
	public CacheManager(Provider<data> _provider){
		provider = _provider;
		if (provider != null){
			provider.addChangeListener(this);
		}
	}
	
	@Override
	public data load(String id) {
		if (provider != null){
			return provider.load(id);
		}
		return null;
	}

	@Override
	public data get(String id) {
		synchronized (lock){
			data found = super.get(id);
			if (found == null){
				found = load(id);
				if (found != null){
					add(found);
				}
			}else{
				if (found.isExpired()){
					//对象已过期
					found = load(id);
					if (found != null){
						add(found);
					}					
				}
			}
			return found;
		}
	}	

	/**
	 * 获取指定ID的数据
	 * 
	 * <br>
	 * 提供一个快速接口给子类使用.
	 * @param id ID
	 * @return
	 * @since 1.0.7
	 */
	protected data _get(String id){
		return super.get(id);
	}
	
	/**
	 * 向容器中增加数据
	 * 
	 * <br>
	 * 提供一个快速接口给子类使用.
	 * @param id id
	 * @param obj 数据对象
	 * @return
	 * @since 1.0.7
	 */
	protected void _add(String id,data obj){
		synchronized (lock){
			super.add(id, obj);
		}
	}
	
	@Override
	public void add(String id, data obj) {
		synchronized (lock){
			super.add(id, obj);
		}
	}	

	@Override
	public void remove(String id) {
		synchronized (lock){
			super.remove(id);
		}
	}
	
	/**
	 * 向缓存中增加对象
	 * @param obj
	 */
	public void add(data obj){
		synchronized (lock){
			super.add(obj.getId(), obj);
		}
	}

	/**
	 * 监听器列表
	 */
	protected Vector<ChangeAware<data>> listeners = new Vector<ChangeAware<data>>();
	
	@Override
	public void addChangeListener(ChangeAware<data> listener) {
		listeners.add(listener);
	}

	@Override
	public void changed(String id, data obj) {
		synchronized (lock){
			logger.info("model is changed,id = " + id);
			add(id, obj);
		}
		
		for (ChangeAware<data> listener:listeners){
			if (listener != null){
				listener.changed(id, obj);
			}
		}
	}
}
