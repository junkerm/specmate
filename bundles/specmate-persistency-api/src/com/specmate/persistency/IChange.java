package com.specmate.persistency;

import com.specmate.common.exception.SpecmateException;

public interface IChange<T> {
	public T doChange() throws SpecmateException;
}
