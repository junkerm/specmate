	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class IModelConnection  {

		___nsuri: string = "http://specmate.com/20180925/model/base";
		public url: string;
		public className: string = "IModelConnection";
		public static className: string = "IModelConnection";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];
		public source: Proxy;
		public target: Proxy;

		// Containment


	}

