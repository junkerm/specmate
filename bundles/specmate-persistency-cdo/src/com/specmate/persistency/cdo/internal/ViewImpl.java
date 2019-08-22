package com.specmate.persistency.cdo.internal;

import java.util.List;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.log.LogService;

import com.specmate.persistency.IView;

public class ViewImpl implements IView {

	private CDOView view;
	private String resourceName;
	protected CDOPersistencyService persistency;

	public ViewImpl(CDOPersistencyService persistency, CDOView view, String resourceName, LogService logService) {
		super();
		this.view = view;
		this.resourceName = resourceName;
		this.persistency = persistency;
	}

	@Override
	public Resource getResource() {
		return view.getResource(resourceName);
	}

	@Override
	public EObject getObjectById(Object originId) {
		CDOID id;
		if (originId instanceof CDOID) {
			id = (CDOID) originId;
		} else if (originId instanceof String) {
			id = CDOIDUtil.read((String) originId);
		} else {
			return null;
		}
		return view.getObject(id);
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public List<Object> query(String queryString, Object context) {
		CDOQuery cdoQuery = this.view.createQuery("ocl", queryString, context);
		return cdoQuery.getResult();
	}
	
	@Override
	public List<Object> querySQL(String queryString, Object context, long lastActive) {
		CDOQuery cdoSQLQuery = this.view.createQuery("sql", queryString);
		//CDOQuery cdoSQLQuery = this.view.createQuery("sql", queryString, context);
		cdoSQLQuery.setParameter("cdoObjectQuery", false);
		cdoSQLQuery.setParameter("time", lastActive);
		return cdoSQLQuery.getResult();
	}
	
	@Override 
	public List<Object> querySQLWithName(String queryString, Object context, String userName, long lastActive) {
		CDOQuery cdoSQLQuery = this.view.createQuery("sql", queryString);
		//CDOQuery cdoSQLQuery = this.view.createQuery("sql", queryString, context);
		cdoSQLQuery.setParameter("cdoObjectQuery", false);
		cdoSQLQuery.setParameter("name", userName);
		cdoSQLQuery.setParameter("time", lastActive);
		return cdoSQLQuery.getResult();
	}

	public void update(CDOView view) {
		this.view = view;
	}

	public void close() {
		view.close();
		persistency.closedView(this);
	}

}
