	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class CEGNode  {

		___nsuri: string = "http://specmate.com/model/20180412/model/requirements";
		public url: string;
		public className: string = "CEGNode";
		public static className: string = "CEGNode";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public x: EDouble;
		public y: EDouble;
		public type: NodeType;
		public variable: EString;
		public condition: EString;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];
		public outgoingConnections: Proxy[];
		public incomingConnections: Proxy[];

		// Containment


	}

