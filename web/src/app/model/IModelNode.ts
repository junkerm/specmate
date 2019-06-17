	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class IModelNode  {

		___nsuri: string = "http://specmate.com/20190125/model/base";
		public url: string;
		public className: string = "IModelNode";
		public static className: string = "IModelNode";
		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public x: EDouble;
		public y: EDouble;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];
		public outgoingConnections: Proxy[];
		public incomingConnections: Proxy[];

		// Containment


	}

