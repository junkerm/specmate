package com.specmate.common.cache.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.specmate.common.cache.ICache;
import com.specmate.common.cache.ICacheLoader;
import com.specmate.common.cache.ICacheService;
import com.specmate.common.exception.SpecmateException;

/**
 * A cache implementation on the base of guava caches
 *
 * @author junkerm
 *
 */
@Component(service = ICacheService.class)
public class GuavaCacheServiceImpl implements ICacheService {

	@SuppressWarnings("rawtypes")
	Map<String, ICache> caches;

	@SuppressWarnings("rawtypes")
	@Activate
	public void activate() {
		caches = new HashMap<String, ICache>();
	}

	@Deactivate
	public void deactivate() {
		for (String cacheName : caches.keySet()) {
			removeCache(cacheName);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> ICache<K, V> createCache(String name, int capacity, int evictTime, ICacheLoader<K, V> loader) {
		if (caches.containsKey(name)) {
			return caches.get(name);
		}
		LoadingCache<K, V> guavaCache = CacheBuilder.newBuilder().maximumSize(capacity)
				.expireAfterWrite(evictTime, TimeUnit.SECONDS).build(new CacheLoader<K, V>() {
					@Override
					public V load(K key) throws SpecmateException {
						return loader.load(key);
					}
				});
		ICache<K, V> cacheImpl = new GuavaCacheImpl<K, V>(guavaCache);
		caches.put(name, cacheImpl);
		return cacheImpl;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void removeCache(String name) {
		GuavaCacheImpl cache = (GuavaCacheImpl) caches.get(name);
		if (cache != null) {
			cache.clean();
			caches.remove(name);
		}
	}

}
