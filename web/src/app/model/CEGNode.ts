	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGNode  {
		
		public url: string;

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public type: NodeType;

		// References
		
		public outgoingConnections: Proxy; 
		public incomingConnection: Proxy; 

	}

