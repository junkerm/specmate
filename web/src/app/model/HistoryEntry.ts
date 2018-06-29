	import './support/gentypes';
	import { Proxy } from './support/proxy';

			import { Change } from './Change';

	export class HistoryEntry  {

		___nsuri: string = "http://specmate.com/20180622/model/history";
		public url: string;
		public className: string = "HistoryEntry";
		public static className: string = "HistoryEntry";

		// Attributes
		public date: EDate;
		public user: EString;
		public comment: EString;

		// References
		

		// Containment
		public changes: Change[];


	}

