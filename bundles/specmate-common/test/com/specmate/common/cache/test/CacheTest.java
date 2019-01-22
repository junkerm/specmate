package com.specmate.common.cache.test;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.common.cache.ICache;
import com.specmate.common.cache.ICacheLoader;
import com.specmate.common.cache.internal.GuavaCacheServiceImpl;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;

public class CacheTest {

	ICacheLoader<String, String> identityLoader = new ICacheLoader<String, String>() {
		@Override
		public String load(String key) throws SpecmateException {
			return key;
		}
	};

	ICacheLoader<String, String> countingLoader = new ICacheLoader<String, String>() {
		private int i = 1;

		@Override
		public String load(String key) throws SpecmateException {
			return Integer.toString(i++);
		}
	};

	ICacheLoader<String, String> failingLoader = new ICacheLoader<String, String>() {
		private boolean first = true;

		@Override
		public String load(String key) throws SpecmateException {
			if (first) {
				first=false;
				return key;
			} else {
				throw new SpecmateInternalException(ErrorCode.INTERNAL_PROBLEM, "");
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateCache() {
		GuavaCacheServiceImpl cacheService = new GuavaCacheServiceImpl();
		cacheService.activate();

		// Good Case Smoke test
		cacheService.createCache("cacheName", 1000, 100, identityLoader);

		// Corner Cases some test
		cacheService.createCache("cacheName", 0, 100, identityLoader);
		cacheService.createCache("cacheName", 1000, 0, identityLoader);
		cacheService.createCache("cacheName", 1000, 100, null);
	}

	@Test
	public void testCache() throws Exception {
		GuavaCacheServiceImpl cacheService = new GuavaCacheServiceImpl();
		cacheService.activate();

		int evictTimeInSeconds = 2;
		int capacity = 1000;
		String key1 = "key1";
		String key2 = "key2";

		ICache<String, String> cache = cacheService.createCache("cacheName", capacity, evictTimeInSeconds,
				countingLoader);

		// Initial value is set and is "1"
		String val = cache.get(key1);
		Assert.assertEquals("1", val);

		// Cache access still results in value "1"
		val = cache.get(key1);
		Assert.assertEquals("1", val);

		// Adding second key does not effect first key
		String val2 = cache.get(key2);
		val = cache.get(key1);
		Assert.assertEquals("1", val);
		Assert.assertEquals("2", val2);

		// Wait for evict time to pass and verify if cache entry has been loaded freshly
		// (i.e. incremented)
		Thread.sleep(evictTimeInSeconds * 1000);
		val = cache.get(key1);
		Assert.assertEquals("3", val);
		
		// retrieving cache with same name does not change anything
		ICache<String, String> cache2 = cacheService.createCache("cacheName", capacity, evictTimeInSeconds,
				countingLoader);
		val = cache2.get(key1);
		Assert.assertEquals("3", val);
		
		// retrieving cache with different name has an impact
		ICache<String, String> cache3 = cacheService.createCache("cacheName2", capacity, evictTimeInSeconds,
				countingLoader);
		val = cache3.get(key1);
		Assert.assertEquals("4", val);
	}

	@Test(expected = SpecmateException.class)
	public void testFail() throws Exception {
		GuavaCacheServiceImpl cacheService = new GuavaCacheServiceImpl();
		cacheService.activate();

		int evictTimeInSeconds = 2;
		int capacity = 1000;
		String key1 = "key1";

		// Good Case
		ICache<String, String> cache = cacheService.createCache("cacheName", capacity, evictTimeInSeconds,
				failingLoader);

		// Initial value is set and is "1"
		String val = cache.get(key1);
		Assert.assertEquals(key1, val);
		
		// Will not result in an error as value is cached
		val = cache.get(key1);
		Assert.assertEquals(key1, val);
		
		Thread.sleep(evictTimeInSeconds * 1000);
		// Will result in error
		val = cache.get(key1);

	}
}
