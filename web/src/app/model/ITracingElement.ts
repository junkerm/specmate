	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class ITracingElement  {

		___nsuri: string = "http://specmate.com/20181210/model/base";
		public url: string;
		public className: string = "ITracingElement";
		public static className: string = "ITracingElement";

		// Attributes

		// References
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];

		// Containment


	}

