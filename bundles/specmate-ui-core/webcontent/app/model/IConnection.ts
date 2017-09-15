	import './support/gentypes';
	import { Proxy } from './support/proxy';

	export class IConnection  {

		___nsuri: string = "http://specmate.com/20170209/model/base";
		public url: string;
		public className: string = "IConnection";
		public static className: string = "IConnection";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;

		// References
		
		public source: Proxy;
		public target: Proxy;


	}

