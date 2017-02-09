package com.specmate.model.emfjson.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.json.JSONException;
import org.json.JSONObject;

import com.specmate.model.ModelPackage;
import com.specmate.model.emfjson.EMFJsonDeserializer;
import com.specmate.model.emfjson.EMFJsonSerializer;
import com.specmate.model.emfjson.ISerializerStopPredicate;
import com.specmate.model.emfjson.SerializerStopPredicateBase;
import com.specmate.model.scenario.MainFlow;
import com.specmate.model.scenario.Scenario;
import com.specmate.model.scenario.ScenarioPackage;
import com.specmate.model.scenario.Step;
import com.specmate.model.util.ModelFactory;

public class SerializerTest extends TestCase {

	private Scenario scenario;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		// init packages
		@SuppressWarnings("unused")
		EPackage pack= ScenarioPackage.eINSTANCE;
		pack = ModelPackage.eINSTANCE;		
		
		scenario = ModelFactory.createScenario();
		scenario.setName("My Scenario");
		scenario.setDescription("Scenario Description");
		scenario.setSid("1");
		MainFlow flow = ModelFactory.createFlow();
		flow.setName("My Flow");
		flow.setDescription("Description Flow");
		flow.setSid("2");
		Step step = ModelFactory.createUserStep();
		step.setName("Mystep");
		step.setDescription("My Description");
		step.setSid("3");
		flow.getSteps().add(step);
		Step step2 = ModelFactory.createUserStep();
		step2.setName("Mystep 2");
		step2.setDescription("My Description 2");
		step2.setSid("3");
		flow.getSteps().add(step2);
		scenario.setBasicFlow(flow);

	}

	public void testSerializer() throws JSONException {
		String serialized = new EMFJsonSerializer().serialize(scenario)
				.toString();
		System.out.println(serialized);
		Assert.assertEquals(
				serialized,
				"{\"exceptionalFlows\":[],\"id\":\"1\",\"___eclass\":\"Scenario\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"My Scenario\",\"comment\":\"Scenario Comment\",\"mainFlow\":{\"id\":\"2\",\"___eclass\":\"Flow\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"My Flow\",\"steps\":[{\"id\":\"3\",\"___eclass\":\"Step\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"Mystep\",\"comment\":\"My Comment\"},{\"id\":\"3\",\"___eclass\":\"Step\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"Mystep 2\",\"comment\":\"My Comment 2\"}],\"comment\":\"Comment Flow\"}}");
	}

	public void testSerializerDepth() throws JSONException {
		ISerializerStopPredicate stopPredicate = new SerializerStopPredicateBase() {
			@Override
			public boolean stopAtDepth(int depth) {
				return depth >= 1;
			}
		};
		String serialized = new EMFJsonSerializer(stopPredicate).serialize(
				scenario).toString();
		System.out.println(serialized);
		Assert.assertEquals(
				"{\"exceptionalFlows\":[],\"id\":\"1\",\"___eclass\":\"Scenario\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"My Scenario\",\"comment\":\"Scenario Comment\",\"mainFlow\":{}}",
				serialized);

	}
	
	public void testDeserializer() throws JSONException{
		EMFJsonDeserializer deserializer = new EMFJsonDeserializer(null);
		EObject deserializeEObject = deserializer.deserializeEObject(new JSONObject("{\"exceptionalFlows\":[],\"id\":\"1\",\"___eclass\":\"Scenario\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"My Scenario\",\"comment\":\"Scenario Comment\",\"mainFlow\":{\"id\":\"2\",\"___eclass\":\"Flow\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"My Flow\",\"steps\":[{\"id\":\"3\",\"___eclass\":\"Step\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"Mystep\",\"comment\":\"My Comment\"},{\"id\":\"3\",\"___eclass\":\"Step\",\"___nsuri\":\"http://specmate.com/20140523/model/scenario\",\"name\":\"Mystep 2\",\"comment\":\"My Comment 2\"}],\"comment\":\"Comment Flow\"}}"));
		Assert.assertTrue(EcoreUtil.equals(scenario, deserializeEObject));
	}

}
