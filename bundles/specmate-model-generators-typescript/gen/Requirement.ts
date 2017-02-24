	import { EInt,EString,EBoolean } from '../support/gentypes';
	import { ISpecmateObject } from '../support/ISpecmateObject';

	export class Requirement  implements ISpecmateObject {
		// Attributes
		public id:EString;
		public name:EString;
		public description:EString;
		public extId:EString;
		public extId2:EString;
		public numberOfTests:EInt;
		public tac:EString;
		public implementingUnit:EString;
		public implementingBOTeam:EString;
		public implementingITTeam:EString;
		public plannedRelease:EString;
		public status:EString;


	}

