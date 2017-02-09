	import { EInt,EString,EBoolean } from '../support/gentypes';
	import { Proxy } from '../support/Proxy';
	import { SpecmateObject } from '../support/SpecmateObject';
	import { IObjectMetaData } from '../support/IObjectMetaData';
	import { DEFAULT_SOURCE } from '../support/ISourcedValue';

	export class BlueprintSlice  extends SpecmateObject {
		// Attributes
		private _id:EString;
		private _name:EString;
		private _description:EString;

		// References
		public annotations:Proxy[];
		public variants:Proxy[];
		public optionalActions:Proxy[];
        public from:Proxy;
        public to:Proxy;
		public modifications:Proxy[];


		get id() : EString{
			return this._id;
		}

		set id(value : EString){
			this._id=value;
			this.appendToLog(DEFAULT_SOURCE);
		}
		get name() : EString{
			return this._name;
		}

		set name(value : EString){
			this._name=value;
			this.appendToLog(DEFAULT_SOURCE);
		}
		get description() : EString{
			return this._description;
		}

		set description(value : EString){
			this._description=value;
			this.appendToLog(DEFAULT_SOURCE);
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
				case "variants":
					return this.variants;
				case "optionalActions":
					return this.optionalActions;
				case "from":
					return this.from;
				case "to":
					return this.to;
				case "modifications":
					return this.modifications;
				default: throw new Error("Feature not defined:"+feature);
			}
		}

		_set(feature: string, value: any, source?:string) {
			switch (feature) {
				case "id":
					this.id=<EString>value;
					if(source){
						this.appendToLog(source);
					}
					break;
				case "name":
					this.name=<EString>value;
					if(source){
						this.appendToLog(source);
					}
					break;
				case "description":
					this.description=<EString>value;
					if(source){
						this.appendToLog(source);
					}
					break;
				default:
                    throw new Error("Feature not defined or not writable: " + feature);
			}
		}

		_getMetaData(): IObjectMetaData {
			return <IObjectMetaData>{
	            get className(): string {
	                return "BlueprintSlice";
	            },
	            get attributes(): string[] {
	                return ["id","name","description"];
	            },
	            get nsUri(): string {
	                return "http://allianz.com/20151023_05/model/test";
	            },
	            get references(): string[] {
	                return ["variants","optionalActions","from","to"];
	            }
            };
        }

		_getId():string {
			return this.id;
		}
	}

