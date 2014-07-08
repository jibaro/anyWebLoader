package com.anysoft.loadbalance;


/**
 * 负载统计
 * @author duanyy
 * @since 1.2.0
 */
public class LoadStat {
	
	public LoadStat(String _id,long _cycle){
		id = _id;
		cycle = _cycle;
	}
	
	public LoadStat(String _id){
		this(_id,30 * 60 * 1000);
	}
	
	/**
	 * 统计ID
	 */
	protected String id;
	
	/**
	 * 一分钟之内的使用次数
	 */
	public long timesInOnMin = 0;
	
	/**
	 * 一分钟之内的使用次数
	 */
	public long avgDurationInOnMin = 0;
	
	/**
	 * 上次不可用时间
	 */
	public long errorTime = 0;
	
	/**
	 * 时间戳
	 */
	protected long timestamp = 0;
	
	/**
	 * 数据统计周期，30分钟
	 */
	protected long cycle = 30 * 60 * 1000;
	
	/**
	 * 报告统计数据
	 * @param duration　时长
	 * @param isValid　是否可用
	 * 
	 */
	public void report(long duration,boolean isValid){
		synchronized (this){
			long now = System.currentTimeMillis();
			now = (now / cycle) * cycle;
			if (now != timestamp){
				timesInOnMin = 1;
				timestamp = now;
				avgDurationInOnMin = duration;
			}else{					
				avgDurationInOnMin = 
						(avgDurationInOnMin * timesInOnMin + duration)/(timesInOnMin + 1);
				timesInOnMin += 1;
			}
			if (!isValid){
				errorTime = now;
			}
		}
	}	
}
