	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGNode  {
		
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

		// References
		
		public outgoingConnections: Proxy; 
		public incomingConnection: Proxy; 


	}

