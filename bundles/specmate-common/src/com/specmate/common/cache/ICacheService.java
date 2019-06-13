package com.specmate.common.cache;

public interface ICacheService {
	/**
	 * Creates a new cache. In case a cache with the given name has already been
	 * created, returns the already created cache. Note: capacity and evict time are
	 * not updated.
	 */
	public <K,V> ICache<K,V> createCache(String name, int capacity, int evictTime, ICacheLoader<K,V> loader);

	void removeCache(String name);
}
