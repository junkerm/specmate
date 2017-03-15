	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGEffectNode  {
		
		public url: string;
		public className: string = "CEGEffectNode";
		public static className: string = "CEGEffectNode";

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

