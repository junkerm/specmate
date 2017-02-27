	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGConection  {
		
		public url: string;

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public negate: EBoolean;

		// References
		
		public source: Proxy; 
		public target: Proxy; 

	}

