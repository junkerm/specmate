	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export interface ISpecmateModelObject {

		___nsuri: string;
		 url: string;
		 className: string;
		

		// Attributes
		 id: EString;
		 name: EString;
		 description: EString;

		// References
		
		 tracesTo: Proxy[];
		 tracesFrom: Proxy[];

		// Containment


	}

