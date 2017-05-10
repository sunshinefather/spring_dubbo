package org.ibase4j.core.aspect;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 切换数据源(不同方法调用不同数据源)
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:17:52
 */
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {
	private final Logger logger = LogManager.getLogger();

	@Pointcut("execution(* org.ibase4j.service..*.*(..))")
	public void aspect() {
	}
	
	@Before("aspect()")
	public void before(JoinPoint point) {
		String className = point.getTarget().getClass().getName();
		String method = point.getSignature().getName();
		Class<?> classObj = point.getTarget().getClass();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        
        logger.info(className + "." + method + "(" + StringUtils.join(point.getArgs(), ",") + ")");
        
        try {
            Method m = classObj.getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSource(data.value());
            }else{
            	L:for (DynamicDataSourceGlobal key : DynamicDataSource.METHODTYPE.keySet()) {
    				for (String type : DynamicDataSource.METHODTYPE.get(key)) {
    					if (method.startsWith(type)) {
    						logger.info(key);
    						DynamicDataSourceHolder.putDataSource(key);
    						break L;
    					}
    				}
    			}
            }
            
        } catch (Exception e) {
			logger.error(e);
			DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.WRITE);
        }
	}
	
	@After("aspect()")
	public void after(JoinPoint point) {
		DynamicDataSourceHolder.clear();
	}
}
