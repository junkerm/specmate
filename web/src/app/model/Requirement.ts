	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class Requirement  {

		___nsuri: string = "http://specmate.com/20181108/model/requirements";
		public url: string;
		public className: string = "Requirement";
		public static className: string = "Requirement";

		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public extId: EString;
		public extId2: EString;
		public source: EString;
		public live: EBoolean;
		public numberOfTests: EInt;
		public tac: EString;
		public implementingUnit: EString;
		public implementingBOTeam: EString;
		public implementingITTeam: EString;
		public plannedRelease: EString;
		public status: EString;
		public isRegressionRequirement: EBoolean;
		public platform: EString;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];

		// Containment


	}

