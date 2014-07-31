package com.anysoft.cache;

import java.util.Hashtable;
import java.util.Map;

import org.w3c.dom.Element;

import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;

/**
 * 基于内存的DataStore
 * @author duanyy
 *
 * @param <data>
 */
public class Memory<data extends Cacheable> extends AbstractDataStore<data> {

	@Override
	public void save(String id, data _data) throws BaseException {
		datas.put(id, _data);
		if (changeAware != null){
			changeAware.changed(id, _data);
		}
	}

	@Override
	public data load(String id) {
		return load(id,true);
	}

	@Override
	public data load(String id, boolean cacheAllowed) {
		return datas.get(id);
	}

		
	@Override
	public void close() throws Exception {
		datas.clear();
	}

	@Override
	public void create(Properties props) throws BaseException {
		
	}

	@Override
	public void refresh() throws BaseException {
		datas.clear();
	}
	
	/**
	 * 缓存数据
	 */
	protected Hashtable<String,data> datas = new Hashtable<String,data>();
	
	@Override
	public void report(Element xml) {
		if (xml != null){
			xml.setAttribute("dataCount", String.valueOf(datas.size()));
		}
	}

	@Override
	public void report(Map<String, Object> json) {
		if (json != null){
			json.put("dataCount", datas.size());
		}
	}

}
