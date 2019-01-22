package com.specmate.common.cache;

import com.specmate.common.exception.SpecmateException;

/**
 * Interface for a cache.
 *
 * @author junkerm
 *
 * @param <K>
 *            The type of the keys
 * @param <V>
 *            The type of the cached values
 */
public interface ICache<K, V> {
	/**
	 * Retrieves the value saved under the given key. If there is no value
	 * cached yet, loads the value
	 */
	public V get(K key) throws SpecmateException;

	/** Clean up the cache (i.e. deletes all entries) */
	public void clean();
}
