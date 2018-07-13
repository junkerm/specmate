package com.specmate.cdoserver;

import com.specmate.common.SpecmateException;

public interface ICDOServer {

	void start() throws SpecmateException;

	void shutdown();

}
