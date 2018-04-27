	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class CEGConnection  {

		___nsuri: string = "http://specmate.com/model/20180412/model/requirements";
		public url: string;
		public className: string = "CEGConnection";
		public static className: string = "CEGConnection";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public negate: EBoolean;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];
		public source: Proxy;
		public target: Proxy;

		// Containment


	}

