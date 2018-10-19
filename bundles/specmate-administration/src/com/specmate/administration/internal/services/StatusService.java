package com.specmate.administration.internal.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.administration.api.ESpecmateStatus;
import com.specmate.administration.api.IStatusService;
import com.specmate.common.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.administration.AdministrationFactory;
import com.specmate.model.administration.Status;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = { IRestService.class, IStatusService.class })
public class StatusService extends RestServiceBase implements IStatusService {
	private ESpecmateStatus currentStatus = ESpecmateStatus.NORMAL;

	private Map<String, Status> statusMap = new HashMap<>();

	public StatusService() {
		Status normalStatus = AdministrationFactory.eINSTANCE.createStatus();
		normalStatus.setValue(ESpecmateStatus.NORMAL.getName());
		Status maintenanceStatus = AdministrationFactory.eINSTANCE.createStatus();
		maintenanceStatus.setValue(ESpecmateStatus.MAINTENANCE.getName());
		this.statusMap.put(ESpecmateStatus.NORMAL.getName(), normalStatus);
		this.statusMap.put(ESpecmateStatus.MAINTENANCE.getName(), maintenanceStatus);
	}

	@Override
	public String getServiceName() {
		return "status";
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof Resource);
	}

	@Override
	public boolean canPost(Object target, Object object) {
		// return (target instanceof Resource && object instanceof Status);
		return false;
	}

	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		if (target instanceof Resource) {
			return new RestResult<>(Response.Status.OK, statusMap.get(getCurrentStatus().getName()));
		}
		return new RestResult<>(Response.Status.BAD_REQUEST);
	}

	@Override
	public RestResult<?> post(Object target, Object object, String token) throws SpecmateException {
		if (target instanceof Resource) {
			Status status = (Status) object;
			switch (status.getValue()) {
			case ESpecmateStatus.MAINTENANCE_NAME:
				setCurrentStatus(ESpecmateStatus.MAINTENANCE);
				return new RestResult<>(Response.Status.OK, status);
			case ESpecmateStatus.NORMAL_NAME:
				setCurrentStatus(ESpecmateStatus.NORMAL);
				return new RestResult<>(Response.Status.OK, status);
			}
		}
		return new RestResult<>(Response.Status.BAD_REQUEST);
	}

	@Override
	public ESpecmateStatus getCurrentStatus() {
		return currentStatus;
	}

	@Override
	public void setCurrentStatus(ESpecmateStatus status) {
		this.currentStatus = status;
	}
}
