package com.specmate.metrics;

import com.specmate.persistency.IView;

public interface IUserMetricsService {

	void loginCounter(IView sessionView, String userName);

}
