
export class FieldMetaItem {
	public name: string;
    public shortDesc: string;
    public longDesc: string;
    public type: string;
    public required?: boolean;
    public values?: string;
}

export class MetaInfo {
	public static Requirement: FieldMetaItem[] = [
		{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text'		
		},		{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText'		
		}	];
	public static CEGModel: FieldMetaItem[] = [
		{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text'		
		},		{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText'		
		}	];
	public static CEGNode: FieldMetaItem[] = [
		{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text'		
		},		{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText'		
		},		{
			name: "type",
			shortDesc: 'Type',
			longDesc: 'The type of a node',
			required: true,
			type: 'singleSelection',
			values: '["AND", "OR"]'		
		},		{
			name: "value",
			shortDesc: 'Value',
			longDesc: 'The value of a node',
			required: true,
			type: 'text'		
		},		{
			name: "operator",
			shortDesc: 'Operator',
			longDesc: 'The value of a node',
			required: true,
			type: 'singleSelection',
			values: '["=", "<", "<=", ">=", ">"]'		
		},		{
			name: "variable",
			shortDesc: 'Variable',
			longDesc: 'The variable of a node',
			required: true,
			type: 'text'		
		}	];
	public static CEGConnection: FieldMetaItem[] = [
		{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text'		
		},		{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText'		
		},		{
			name: "negate",
			shortDesc: 'Negate',
			longDesc: 'Negation of this connection',
			type: 'checkbox'		
		}	];
	public static CEGCauseNode: FieldMetaItem[] = [
		{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text'		
		},		{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText'		
		},		{
			name: "type",
			shortDesc: 'Type',
			longDesc: 'The type of a node',
			required: true,
			type: 'singleSelection',
			values: '["AND", "OR"]'		
		},		{
			name: "value",
			shortDesc: 'Value',
			longDesc: 'The value of a node',
			required: true,
			type: 'text'		
		},		{
			name: "operator",
			shortDesc: 'Operator',
			longDesc: 'The value of a node',
			required: true,
			type: 'singleSelection',
			values: '["=", "<", "<=", ">=", ">"]'		
		},		{
			name: "variable",
			shortDesc: 'Variable',
			longDesc: 'The variable of a node',
			required: true,
			type: 'text'		
		}	];
	public static CEGEffectNode: FieldMetaItem[] = [
		{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text'		
		},		{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText'		
		},		{
			name: "type",
			shortDesc: 'Type',
			longDesc: 'The type of a node',
			required: true,
			type: 'singleSelection',
			values: '["AND", "OR"]'		
		},		{
			name: "value",
			shortDesc: 'Value',
			longDesc: 'The value of a node',
			required: true,
			type: 'text'		
		},		{
			name: "operator",
			shortDesc: 'Operator',
			longDesc: 'The value of a node',
			required: true,
			type: 'singleSelection',
			values: '["=", "<", "<=", ">=", ">"]'		
		},		{
			name: "variable",
			shortDesc: 'Variable',
			longDesc: 'The variable of a node',
			required: true,
			type: 'text'		
		}	];
}
