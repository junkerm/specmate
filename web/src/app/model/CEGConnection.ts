	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class CEGConnection  {
		
		public url: string;
		public className: string = "CEGConnection";
		public static className: string = "CEGConnection";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public negate: EBoolean;

		// References
		
		public source: Proxy; 
		public target: Proxy; 


	}

