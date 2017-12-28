"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var FieldMetaItem = /** @class */ (function () {
    function FieldMetaItem() {
    }
    return FieldMetaItem;
}());
exports.FieldMetaItem = FieldMetaItem;
var MetaInfo = /** @class */ (function () {
    function MetaInfo() {
    }
    MetaInfo.INamed = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }
    ];
    MetaInfo.IDescribed = [
        {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.IID = [];
    MetaInfo.IContentElement = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.IContainer = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ISpecmateModelObject = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.Folder = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.IPositionable = [];
    MetaInfo.IExternal = [];
    MetaInfo.ISpecmatePositionableModelObject = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.IModelConnection = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.IModelNode = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ITracingElement = [];
    MetaInfo.Requirement = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.CEGModel = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.CEGNode = [
        {
            name: "type",
            shortDesc: 'Type',
            longDesc: 'The type of a node',
            required: true,
            type: 'singleSelection',
            values: '["AND", "OR"]',
            position: '3'
        }, {
            name: "variable",
            shortDesc: 'Variable',
            longDesc: 'The variable of a node',
            required: true,
            type: 'text',
            position: '1'
        }, {
            name: "condition",
            shortDesc: 'Condition',
            longDesc: 'The condition the variable has to fulfil',
            required: true,
            type: 'text',
            position: '2'
        }
    ];
    MetaInfo.CEGConnection = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }, {
            name: "negate",
            shortDesc: 'Negate',
            longDesc: 'Negation of this connection',
            type: 'checkbox',
            position: '1'
        }
    ];
    MetaInfo.TestSpecification = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.TestParameter = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.TestCase = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ParameterAssignment = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.TestProcedure = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }, {
            name: "isRegressionTest",
            shortDesc: 'Regression Test',
            type: 'checkbox',
            position: '3',
            longDesc: ''
        }
    ];
    MetaInfo.TestStep = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.Process = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ProcessNode = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ProcessStep = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ProcessDecision = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ProcessConnection = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }, {
            name: "condition",
            shortDesc: 'Condition',
            longDesc: 'The condition the variable has to fulfil',
            required: false,
            type: 'text',
            position: '2'
        }
    ];
    MetaInfo.ProcessStart = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    MetaInfo.ProcessEnd = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: '',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }
    ];
    return MetaInfo;
}());
exports.MetaInfo = MetaInfo;
//# sourceMappingURL=field-meta.js.map