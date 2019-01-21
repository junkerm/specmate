package com.specmate.common.cache;

import com.specmate.common.exception.SpecmateException;

public interface ICache<K,V> {
	public V get(K key) throws SpecmateException;
}
