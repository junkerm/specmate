package com.specmate.migration.test;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

import com.specmate.common.SpecmateException;
import com.specmate.migration.testmodel.baseline.artefact.ArtefactFactory;
import com.specmate.migration.testmodel.baseline.artefact.Diagram;
import com.specmate.migration.testmodel.baseline.base.BaseFactory;
import com.specmate.migration.testmodel.baseline.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

@Component(immediate = true)
public class BaselineTestdataService {
	private IPersistencyService persistencyService;
	private BundleContext context;
	
	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Activate
	public void activate() throws SpecmateException, InterruptedException {
		//context = FrameworkUtil.getBundle(BaselineTestdataService.class).getBundleContext();
		//getPersistencyService();
		ITransaction transaction = this.persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject root = SpecmateEcoreUtil.getEObjectWithName("root", resource.getContents());
		
		System.out.println("activated");
		
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
	
	private void getPersistencyService() throws InterruptedException, SpecmateException {
		ServiceTracker<IPersistencyService, IPersistencyService> persistencyTracker = new ServiceTracker<>(context,
				IPersistencyService.class.getName(), null);
		persistencyTracker.open();
		persistencyService = persistencyTracker.waitForService(100000);
		//Assert.assertNotNull(persistency);
		
	}
	
	private void loadBaselineTestdata(Folder root) {
		Diagram d1 = ArtefactFactory.eINSTANCE.createDiagram();
		d1.setName("d1");
		d1.setTested(false);
		
		root.getContents().add(d1);
	}
}
