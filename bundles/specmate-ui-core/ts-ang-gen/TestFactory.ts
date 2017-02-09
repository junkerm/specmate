/// <reference path="../ts-src/model/init.ts" />
module model {
	class TestFactory implements ISpecmateModelFactory {
		public createObject(eClass:string):SpecmateModelObject{
			switch(eClass){
				case 'TestSpecification':
					return new TestSpecification();
				case 'BlueprintSlice':
					return new BlueprintSlice();
				case 'Modification':
					return new Modification();
				case 'TestSpecificationColumn':
					return new TestSpecificationColumn();
				case 'TestSpecificationRow':
					return new TestSpecificationRow();
			}
		}
	} 

	globalModelFactory.registerFactory("http://allianz.com/20151023_05/model/test", new TestFactory());
}
