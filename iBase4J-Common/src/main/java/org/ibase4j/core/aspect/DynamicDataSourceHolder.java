package org.ibase4j.core.aspect;
/**
 * 获取当前线程使用的数据源
 * @ClassName:  DynamicDataSourceHolder   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2017年5月5日 上午11:39:24
 */
public class DynamicDataSourceHolder {
	private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();
	
	public static void putDataSource(DynamicDataSourceGlobal datasource) {
		holder.set(datasource);
	}

	public static DynamicDataSourceGlobal getDataSource() {
		return holder.get();
	}

	public static void clear() {
		holder.remove();
	}
}