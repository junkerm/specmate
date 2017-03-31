	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGNode  {

		___nsuri: string = "http://specmate.com/20170209/model/requirements";
		public url: string;
		public className: string = "CEGNode";
		public static className: string = "CEGNode";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public type: NodeType;
		public x: EDouble;
		public y: EDouble;
		public variable: EString;
		public operator: EString;
		public value: EString;

		// References
		
		public outgoingConnections: Proxy; 
		public incomingConnection: Proxy; 


	}

