	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGCauseNode  {
		
		public url: string;
		public className: string = "CEGCauseNode";
		public static className: string = "CEGCauseNode";

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

