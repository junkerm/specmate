	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class Process  {

		___nsuri: string = "http://specmate.com/20181108/model/processes";
		public url: string;
		public className: string = "Process";
		public static className: string = "Process";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];

		// Containment


	}

