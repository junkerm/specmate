package com.specmate.common.cache;

import com.specmate.common.exception.SpecmateException;

public interface ICacheLoader<K, V> {

	public V load(K key) throws SpecmateException;

}
