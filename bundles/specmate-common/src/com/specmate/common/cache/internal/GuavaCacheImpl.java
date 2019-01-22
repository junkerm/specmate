package com.specmate.common.cache.internal;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.LoadingCache;
import com.specmate.common.cache.ICache;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;

/** A cache implementation based on the guava library */
public class GuavaCacheImpl<K, V> implements ICache<K, V> {

	private LoadingCache<K, V> guavaCache;

	public GuavaCacheImpl(LoadingCache<K, V> guavaCache) {
		this.guavaCache = guavaCache;
	}

	@Override
	public V get(K key) throws SpecmateException {
		try {
			return guavaCache.get(key);
		} catch (ExecutionException e) {
			throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM,
					"Could not load cache item with key " + key + ". Reason: " + e.getMessage(), e);
		}
	}

	@Override
	public void clean() {
		this.guavaCache.invalidateAll();
		this.guavaCache.cleanUp();
	}

}
