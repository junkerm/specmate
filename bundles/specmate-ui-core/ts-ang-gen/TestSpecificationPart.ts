	import {EInt,EString,EBoolean} from './gentypes';

	export class TestSpecificationPart  extends SpecmateObject {
		// Attributes
		private _id:EString;
		private _name:EString;
		private _description:EString;

		// References
		public annotations:Proxy[];
		public input:Proxy[];
		public expected:Proxy[];


		get id() : EString{
			return this._id;
		}

		set id(value : EString, source?:string){
			this._id=value;
			if(source){
				this.log(source);
			} 
		}
		get name() : EString{
			return this._name;
		}

		set name(value : EString, source?:string){
			this._name=value;
			if(source){
				this.log(source);
			} 
		}
		get description() : EString{
			return this._description;
		}

		set description(value : EString, source?:string){
			this._description=value;
			if(source){
				this.log(source);
			} 
		}


		_get(feature: string): any {
			switch (feature) {
				case "id":
					return this.id;
				case "name":
					return this.name;
				case "description":
					return this.description;
				case "annotations":
					return this.annotations;
				case "input":
					return this.input;
				case "expected":
					return this.expected;
				default: throw new Error("Feature not defined:"+feature);
			}
		}

		_set(feature: string, value: any, source?:string) {
			switch (feature) {
				case "id":
					this.id=<EString>value;
					break;
				case "name":
					this.name=<EString>value;
					break;
				case "description":
					this.description=<EString>value;
					break;
				default:
                    throw new Error("Feature not defined or not writable: " + feature);
			}
		}

		_getMetaData(): ModelMetaData {
			return {
	            getClassName(): string {
	                return "TestSpecificationPart";
	            },
	            getAttributes(): Array<string> {
	                return ["id","name","description"];
	            },
	            getNsUri(): string {
	                return "http://allianz.com/20151023_05/model/test";
	            },
	            getReferences(): Array<string> {
	                return [];
	            },
            };
        }

		_getId():string {
			return this.id;
		}
	}
}
