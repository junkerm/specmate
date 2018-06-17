package com.specmate.persistency;

import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;

public interface IChange<T> {
	public RestResult<T> doChange() throws SpecmateException, SpecmateValidationException;
}
