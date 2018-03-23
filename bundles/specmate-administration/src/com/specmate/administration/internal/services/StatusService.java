package com.specmate.administration.internal.services;

import org.osgi.service.component.annotations.Component;

import com.specmate.administration.api.ESpecmateStatus;
import com.specmate.administration.api.IStatusService;

@Component
public class StatusService implements IStatusService {

	private ESpecmateStatus currentStatus = ESpecmateStatus.NORMAL;

	@Override
	public ESpecmateStatus getCurrentStatus() {
		return currentStatus;
	}

	@Override
	public void setCurrentStatus(ESpecmateStatus status) {
		this.currentStatus = status;
	}
}
