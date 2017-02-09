/// <reference path="../ts-src/model/init.ts" />
module model {
	class BlueprintFactory implements ISpecmateModelFactory {
		public createObject(eClass:string):SpecmateModelObject{
			switch(eClass){
				case 'Blueprint':
					return new Blueprint();
				case 'Action':
					return new Action();
				case 'Decision':
					return new Decision();
				case 'Condition':
					return new Condition();
				case 'Variant':
					return new Variant();
			}
		}
	} 

	globalModelFactory.registerFactory("http://allianz.com/20151023_05/model/blueprint", new BlueprintFactory());
}
