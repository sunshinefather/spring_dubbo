package org.ibase4j.core.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{
	
	public static Map<DynamicDataSourceGlobal,List<String>> METHODTYPE = new HashMap<DynamicDataSourceGlobal,List<String>>();
    
	@Override
	protected Object determineCurrentLookupKey() {
		DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();
		
		if(dynamicDataSourceGlobal == null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.READ) {
            return DynamicDataSourceGlobal.READ.name();
        }

        return DynamicDataSourceGlobal.WRITE.name();
	}

	// 设置方法名前缀对应的数据源
	public void setMethodType(Map<DynamicDataSourceGlobal, String> map) {
		for (DynamicDataSourceGlobal key : map.keySet()) {
			List<String> v = new ArrayList<String>();
			String[] types = map.get(key).split(",");
			for (String type : types) {
				if (StringUtils.isNotBlank(type)) {
					v.add(type);
				}
			}
			METHODTYPE.put(key, v);
		}
	}
}
