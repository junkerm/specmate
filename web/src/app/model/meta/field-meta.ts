
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
	public static TestSpecification: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static TestParameter: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static TestCase: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
	public static ParameterAssignment: FieldMetaItem[] = [
			{
			name: "name",
			shortDesc: 'Name',
			longDesc: 'The name of an element',
			required: true,
			type: 'text',
			position: '0'		
		},			{
			name: "description",
			shortDesc: 'Description',
			longDesc: 'The element\'s description',
			required: false,
			type: 'longText',
			rows: '8',
			position: '100'		
		}	];
}
