	import { EInt,EString,EBoolean } from '../support/gentypes';
	import { Proxy } from '../support/Proxy';
	import { SpecmateObject } from '../support/SpecmateObject';
	import { IObjectMetaData } from '../support/IObjectMetaData';
	import { DEFAULT_SOURCE } from '../support/ISourcedValue';

	export class Variable  extends SpecmateObject {
		// Attributes
		private _id:EString;
		private _name:EString;
		private _description:EString;

		// References
		public annotations:Proxy[];


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
	                return "Variable";
	            },
	            get attributes(): string[] {
	                return ["id","name","description"];
	            },
	            get nsUri(): string {
	                return "http://specmate.com/20151203_01/model/basemodel";
	            },
	            get references(): string[] {
	                return [];
	            }
            };
        }

		_getId():string {
			return this.id;
		}
	}

