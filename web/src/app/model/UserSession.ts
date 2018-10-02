	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class UserSession  {

		___nsuri: string = "http://specmate.com/20180925/model/user";
		public url: string;
		public className: string = "UserSession";
		public static className: string = "UserSession";

		// Attributes
		public id: EString;
		public allowedPathPattern: EString;
		public userName: EString;
		public lastActive: ELong;
		public SourceSystem: AccessRights;
		public TargetSystem: AccessRights;
		public libraryFolders: EString[];

		// References

		// Containment


	}

