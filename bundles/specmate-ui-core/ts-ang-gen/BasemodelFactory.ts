/// <reference path="../ts-src/model/init.ts" />
module model {
	class BasemodelFactory implements ISpecmateModelFactory {
		public createObject(eClass:string):SpecmateModelObject{
			switch(eClass){
				case 'UIAnnotation':
					return new UIAnnotation();
				case 'BaseModelNode':
					return new BaseModelNode();
				case 'VariableValuation':
					return new VariableValuation();
				case 'Variable':
					return new Variable();
			}
		}
	} 

	globalModelFactory.registerFactory("http://specmate.com/20151203_01/model/basemodel", new BasemodelFactory());
}
