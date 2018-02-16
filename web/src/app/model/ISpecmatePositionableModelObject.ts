	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export interface ISpecmatePositionableModelObject {

		___nsuri: string;
		 url: string;
		 className: string;
		

		// Attributes
		 id: EString;
		 name: EString;
		 description: EString;
		 x: EDouble;
		 y: EDouble;

		// References
		
		 tracesTo: Proxy[];
		 tracesFrom: Proxy[];

		// Containment


	}

