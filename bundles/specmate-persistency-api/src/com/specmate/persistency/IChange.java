package com.specmate.persistency;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

public interface IChange<T> {
	public T doChange() throws SpecmateException, SpecmateValidationException;
}
