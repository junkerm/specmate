/// <reference path="../ts-src/model/init.ts" />
module model {
	class TestproceduresFactory implements ISpecmateModelFactory {
		public createObject(eClass:string):SpecmateModelObject{
			switch(eClass){
				case 'TestProcedure':
					return new TestProcedure();
				case 'TestProcedureStep':
					return new TestProcedureStep();
				case 'TestProcedureAction':
					return new TestProcedureAction();
				case 'SliceProcedure':
					return new SliceProcedure();
				case 'TestProcedureCheck':
					return new TestProcedureCheck();
			}
		}
	} 

	globalModelFactory.registerFactory("http://allianz.com/20151023_05/model/testprocedures", new TestproceduresFactory());
}
