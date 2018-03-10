package com.specmate.migration.test.support;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.common.SpecmateException;
import com.specmate.dummydata.api.IDataService;
import com.specmate.migration.test.baseline.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.baseline.testmodel.artefact.Diagram;
import com.specmate.migration.test.baseline.testmodel.base.BaseFactory;
import com.specmate.migration.test.baseline.testmodel.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

@Component
public class BaselineTestdataService implements IDataService {
	private IPersistencyService persistencyService;
	
	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Activate
	public void activate() throws SpecmateException, InterruptedException {
		ITransaction transaction = this.persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());
		
		if(root == null) {
			Folder f = BaseFactory.eINSTANCE.createFolder();
			f.setName("root");
			loadBaselineTestdata(f);
			
			transaction.getResource().getContents().add(f);
			
			try {
				transaction.commit();
			}
			catch(SpecmateException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	private void loadBaselineTestdata(Folder root) {
		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		d1.setName("d1");
		d1.setTested(false);
		
		root.getContents().add(d1);
	}
}
