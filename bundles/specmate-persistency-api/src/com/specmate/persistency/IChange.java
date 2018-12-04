package com.specmate.persistency;

import com.specmate.common.SpecmateException;

public interface IChange<T> {
	public T doChange() throws SpecmateException;
}
