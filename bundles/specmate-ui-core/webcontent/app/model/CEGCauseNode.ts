	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGCauseNode  {

		___nsuri: string = "http://specmate.com/20170209/model/requirements";
		public url: string;
		public className: string = "CEGCauseNode";
		public static className: string = "CEGCauseNode";

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
		
		public outgoingConnections: Proxy[];
		public incomingConnections: Proxy[];


	}

