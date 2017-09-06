
export class FieldMetaItem {
	public name: string;
    public shortDesc: string;
    public longDesc: string;
    public type: string;
    public required?: boolean;
    public values?: string;
	public rows?: string;
	public position?: string;
}

export class MetaInfo {
	public static INamed: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		}	];
	public static IDescribed: FieldMetaItem[] = [
			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static IID: FieldMetaItem[] = [
	];
	public static IContentElement: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static IContainer: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static ISpecmateModelObject: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static Folder: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static IPositionable: FieldMetaItem[] = [
	];
	public static Requirement: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static CEGModel: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static CEGNode: FieldMetaItem[] = [
			{
			name: "type",
			shortDesc: 'Type',
			longDesc: 'The type of a node',
			required: true,
			type: 'singleSelection',
			values: '["AND", "OR"]',
			position: '3'		
		},			{
			name: "variable",
			shortDesc: 'Variable',
			longDesc: 'The variable of a node',
			required: true,
			type: 'text',
			position: '1'		
		},			{
			name: "condition",
			shortDesc: 'Condition',
			longDesc: 'The condition the variable has to fulfil',
			required: true,
			type: 'text',
			position: '2'		
		}	];
	public static CEGConnection: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		},			{
			name: "negate",
			shortDesc: 'Negate',
			longDesc: 'Negation of this connection',
			type: 'checkbox',
			position: '1'		
		}	];
	public static CEGCauseNode: FieldMetaItem[] = [
			{
			name: "type",
			shortDesc: 'Type',
			longDesc: 'The type of a node',
			required: true,
			type: 'singleSelection',
			values: '["AND", "OR"]',
			position: '3'		
		},			{
			name: "variable",
			shortDesc: 'Variable',
			longDesc: 'The variable of a node',
			required: true,
			type: 'text',
			position: '1'		
		},			{
			name: "condition",
			shortDesc: 'Condition',
			longDesc: 'The condition the variable has to fulfil',
			required: true,
			type: 'text',
			position: '2'		
		}	];
	public static CEGEffectNode: FieldMetaItem[] = [
			{
			name: "type",
			shortDesc: 'Type',
			longDesc: 'The type of a node',
			required: true,
			type: 'singleSelection',
			values: '["AND", "OR"]',
			position: '3'		
		},			{
			name: "variable",
			shortDesc: 'Variable',
			longDesc: 'The variable of a node',
			required: true,
			type: 'text',
			position: '1'		
		},			{
			name: "condition",
			shortDesc: 'Condition',
			longDesc: 'The condition the variable has to fulfil',
			required: true,
			type: 'text',
			position: '2'		
		}	];
	public static TestSpecification: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static TestParameter: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static TestCase: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static ParameterAssignment: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static TestProcedure: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		},			{
			name: "isRegressionTest",
			shortDesc: 'Regression Test',
			type: 'checkbox',
			position: '3',
			longDesc: ''		
		}	];
	public static TestStep: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: '',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: '',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
}

