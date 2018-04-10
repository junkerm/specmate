package com.specmate.administration.api;

public interface IStatusService {

	ESpecmateStatus getCurrentStatus();

	void setCurrentStatus(ESpecmateStatus maintenance);

}
