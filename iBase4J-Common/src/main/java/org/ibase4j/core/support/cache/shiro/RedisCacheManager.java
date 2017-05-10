/**
 * 
 */
package org.ibase4j.core.support.cache.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.ibase4j.core.Constants;

/**
 * 
 * @author ShenHuaJie
 * @version 2017年3月24日 下午8:50:14
 */
public class RedisCacheManager implements CacheManager {
    private final Logger logger = LogManager.getLogger();

    // fast lookup by name map
    @SuppressWarnings("rawtypes")
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    private String keyPrefix = Constants.CACHE_NAMESPACE + "shiro_redis_cache:";

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");

		Cache c = caches.get(name);

        if (c == null) {
            c = new RedisCache<K, V>(keyPrefix);
            caches.put(name, c);
        }
        return c;
    }
}