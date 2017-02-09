package com.specmate.emfrest.internal.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EMFJsonSerializerStopAt {
	String[] stopClass() default {};
	String[] stopFeature() default {};
	int stopDepth() default -1;
}
