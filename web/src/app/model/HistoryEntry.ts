	import './support/gentypes';
	import { Proxy } from './support/proxy';

			import { Change } from './Change';

	export class HistoryEntry  {

		___nsuri: string = "http://specmate.com/20181108/model/history";
		public url: string;
		public className: string = "HistoryEntry";
		public static className: string = "HistoryEntry";

		// Attributes
		public timestamp: ELong;
		public user: EString;
		public deletedObjects: EString[];
		public comment: EString;

		// References
		

		// Containment
		public changes: Change[];


	}

