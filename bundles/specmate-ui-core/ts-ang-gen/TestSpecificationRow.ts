	import { EInt,EString,EBoolean } from '../support/gentypes';
	import { Proxy } from '../support/Proxy';
	import { SpecmateObject } from '../support/SpecmateObject';
	import { IObjectMetaData } from '../support/IObjectMetaData';
	import { DEFAULT_SOURCE } from '../support/ISourcedValue';

	export class TestSpecificationRow  extends SpecmateObject {
		// Attributes

		// References
		public values:Proxy[];




		_get(feature: string): any {
			switch (feature) {
				case "values":
					return this.values;
				default: throw new Error("Feature not defined:"+feature);
			}
		}

		_set(feature: string, value: any, source?:string) {
			switch (feature) {
				default:
                    throw new Error("Feature not defined or not writable: " + feature);
			}
		}

		_getMetaData(): IObjectMetaData {
			return <IObjectMetaData>{
	            get className(): string {
	                return "TestSpecificationRow";
	            },
	            get attributes(): string[] {
	                return [];
	            },
	            get nsUri(): string {
	                return "http://allianz.com/20151023_05/model/test";
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

