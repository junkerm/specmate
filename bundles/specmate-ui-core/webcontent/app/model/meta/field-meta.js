"use strict";
var FieldMetaItem = (function () {
    function FieldMetaItem() {
    }
    return FieldMetaItem;
}());
exports.FieldMetaItem = FieldMetaItem;
var MetaInfo = (function () {
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
        }];
    MetaInfo.IDescribed = [
        {
            name: "description",
            shortDesc: 'Description',
            longDesc: '',
            required: false,
            type: 'longText',
            rows: '8',
            position: '100'
        }];
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
        }];
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
        }];
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
        }];
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
        }];
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
        }];
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
        }];
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
        }];
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
        }];
    MetaInfo.CEGCauseNode = [
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
        }];
    MetaInfo.CEGEffectNode = [
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
        }];
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
        }];
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
        }];
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
        }];
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
        }];
    return MetaInfo;
}());
exports.MetaInfo = MetaInfo;
//# sourceMappingURL=field-meta.js.map