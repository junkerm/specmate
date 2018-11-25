package com.specmate.cdoserver;

import com.specmate.common.exception.SpecmateException;

public interface ICDOServer {

	void start() throws SpecmateException;

	void shutdown();

}
