	import { EInt,EString,EBoolean } from '../support/gentypes';
	import { Proxy } from '../support/Proxy';
	import { SpecmateObject } from '../support/SpecmateObject';
	import { IObjectMetaData } from '../support/IObjectMetaData';
	import { DEFAULT_SOURCE } from '../support/ISourcedValue';

	export class UIAnnotation  extends SpecmateObject {
		// Attributes
		private _x:EInt;
		private _y:EInt;
		private _id:EString;

		// References


		get x() : EInt{
			return this._x;
		}

		set x(value : EInt){
			this._x=+value;
			this.appendToLog(DEFAULT_SOURCE);
		}
		get y() : EInt{
			return this._y;
		}

		set y(value : EInt){
			this._y=+value;
			this.appendToLog(DEFAULT_SOURCE);
		}
		get id() : EString{
			return this._id;
		}

		set id(value : EString){
			this._id=value;
			this.appendToLog(DEFAULT_SOURCE);
		}


		_get(feature: string): any {
			switch (feature) {
				case "x":
					return this.x;
				case "y":
					return this.y;
				case "id":
					return this.id;
				default: throw new Error("Feature not defined:"+feature);
			}
		}

		_set(feature: string, value: any, source?:string) {
			switch (feature) {
				case "x":
					this.x=<EInt>value;
					if(source){
						this.appendToLog(source);
					}
					break;
				case "y":
					this.y=<EInt>value;
					if(source){
						this.appendToLog(source);
					}
					break;
				case "id":
					this.id=<EString>value;
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
	                return "UIAnnotation";
	            },
	            get attributes(): string[] {
	                return ["x","y","id"];
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

