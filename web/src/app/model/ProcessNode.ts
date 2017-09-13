	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class ProcessNode  {

		___nsuri: string = "http://specmate.com/20170409/model/processes";
		public url: string;
		public className: string = "ProcessNode";
		public static className: string = "ProcessNode";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public x: EDouble;
		public y: EDouble;

		// References
		
		public outgoingConnections: Proxy[];
		public incomingConnections: Proxy[];


	}

