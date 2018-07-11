	import './support/gentypes';
	import { Proxy } from './support/proxy';

			import { IContainer } from './IContainer';

	export class Operation  {

		___nsuri: string = "http://specmate.com/20180622/model/batch";
		public url: string;
		public className: string = "Operation";
		public static className: string = "Operation";

		// Attributes
		public type: OperationType;

		// References
		public target: Proxy;
		

		// Containment
		
		public value: IContainer;


	}

