/// <reference path="../ts-src/model/init.ts" />
module model {
	class RequirementsFactory implements ISpecmateModelFactory {
		public createObject(eClass:string):SpecmateModelObject{
			switch(eClass){
				case 'TextRequirement':
					return new TextRequirement();
				case 'CauseEffectDiagram':
					return new CauseEffectDiagram();
				case 'Event':
					return new Event();
			}
		}
	} 

	globalModelFactory.registerFactory("http://allianz.com/20151023_05/model/requirements", new RequirementsFactory());
}
