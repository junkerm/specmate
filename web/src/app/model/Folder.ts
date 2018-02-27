	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class Folder  {

		___nsuri: string = "http://specmate.com/20180126/model/base";
		public url: string;
		public className: string = "Folder";
		public static className: string = "Folder";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];

		// Containment


	}

