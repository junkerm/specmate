	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class CEGModel  {

		___nsuri: string = "http://specmate.com/20180529/model/requirements";
		public url: string;
		public className: string = "CEGModel";
		public static className: string = "CEGModel";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];

		// Containment


	}

