package com.specmate.common;

/**
 * Utility class that contains static method for assertions and preconditions
 * @author junkerm
 *
 */
public class AssertUtil {

	/** 
	 * Asserts that precondition <code>b</code> is true and throws an exception if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 * */
	public static void preTrue(boolean b){
		preTrue(b, "Precondition violated");
	}
	
	/** 
	 * Asserts that precondition <code>b</code> is true  and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void preTrue(boolean b, String msg){
		if(!b){
			throw new AssertionError(msg);
		}
	}

	/** 
	 * Asserts that precondition <code>b</code> is false  and throws an exceptio if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void preFalse(boolean b){
		preTrue(!b, "Precondition violated");
	}
	

	/** 
	 * Asserts that precondition <code>b</code> is false  and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void preFalse(boolean b, String msg){
		preTrue(!b,msg);
	}


	/** 
	 * Asserts that condition <code>b</code> is true and throws an exception if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 * */
	public static void assertTrue(boolean b){
		preTrue(b, "Assertion violated");
	}
	
	/** 
	 * Asserts that condition <code>b</code> is true  and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertTrue(boolean b, String msg){
		if(!b){
			throw new AssertionError(msg);
		}
	}
	
	/** 
	 * Asserts that condition <code>b</code> is false  and throws an exceptio if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertFalse(boolean b){
		assertTrue(!b);
	}
	
	/** 
	 * Asserts that condition <code>b</code> is false  and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */	
	public static void assertFalse(boolean b, String msg){
		assertTrue(!b,msg);
	}
	
	
	/** 
	 * Asserts that <code>object</code> is not <code>null</code> and throws an exception if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertNotNull(Object object) {
		assertTrue(object!=null);
	}

	/**
	 * Asserts that <code>object</code> is not <code>null</code> and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertNotNull(Object object, String msg) {
		assertTrue(object!=null,msg);
	}

	/** 
	 * Asserts that <code>object</code> is <code>null</code> and throws an exception if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertNull(Object transformed) {
		assertTrue(transformed==null);
	}

	/**
	 * Asserts that <code>object</code> is <code>null</code> and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertNull(Object transformed, String msg) {
		assertTrue(transformed==null,msg);
	}
	
	
	/**
	 * Asserts that <code>o1.equals(o2)</code> and throws an exception if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertEquals(Object o1, Object o2) {
		assertTrue(o1.equals(o2));
	}

	/**
	 * Asserts that <code>o1.equals(o2)</code> and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static void assertEquals(Object o1, Object o2, String msg) {
		assertTrue(o1.equals(o2),msg);
	}
	
	/**
	 * Asserts that <code>o</code> is an instance of <code>clazz</code> and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static <T> void assertInstanceOf(Object o, Class<T> clazz, String msg){
		assertTrue(clazz.isAssignableFrom(o.getClass()),msg);
	}
	
	/**
	 * Asserts that <code>o</code> is an instance of <code>clazz</code> and throws an exception with message <code>msg</code> if not.
	 * @throws  java.lang.AssertionError if <code>b</code> is false. 
	 */
	public static <T> void assertInstanceOf(Object o, Class<T> clazz){
		assertTrue(clazz.isAssignableFrom(o.getClass()));
	}
	
}
