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
    MetaInfo.Requirement = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText',
            position: '100'
        }];
    MetaInfo.CEGModel = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText',
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
            position: '1'
        }, {
            name: "variable",
            shortDesc: 'Variable',
            longDesc: 'The variable of a node',
            required: true,
            type: 'text',
            position: '2'
        }, {
            name: "operator",
            shortDesc: 'Operator',
            longDesc: 'The value of a node',
            required: true,
            type: 'singleSelection',
            values: '["=", "<", "<=", ">=", ">"]',
            position: '3'
        }, {
            name: "value",
            shortDesc: 'Value',
            longDesc: 'The value of a node',
            required: true,
            type: 'text',
            position: '4'
        }];
    MetaInfo.CEGConnection = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text',
            position: '0'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText',
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
            position: '1'
        }, {
            name: "variable",
            shortDesc: 'Variable',
            longDesc: 'The variable of a node',
            required: true,
            type: 'text',
            position: '2'
        }, {
            name: "operator",
            shortDesc: 'Operator',
            longDesc: 'The value of a node',
            required: true,
            type: 'singleSelection',
            values: '["=", "<", "<=", ">=", ">"]',
            position: '3'
        }, {
            name: "value",
            shortDesc: 'Value',
            longDesc: 'The value of a node',
            required: true,
            type: 'text',
            position: '4'
        }];
    MetaInfo.CEGEffectNode = [
        {
            name: "type",
            shortDesc: 'Type',
            longDesc: 'The type of a node',
            required: true,
            type: 'singleSelection',
            values: '["AND", "OR"]',
            position: '1'
        }, {
            name: "variable",
            shortDesc: 'Variable',
            longDesc: 'The variable of a node',
            required: true,
            type: 'text',
            position: '2'
        }, {
            name: "operator",
            shortDesc: 'Operator',
            longDesc: 'The value of a node',
            required: true,
            type: 'singleSelection',
            values: '["=", "<", "<=", ">=", ">"]',
            position: '3'
        }, {
            name: "value",
            shortDesc: 'Value',
            longDesc: 'The value of a node',
            required: true,
            type: 'text',
            position: '4'
        }];
    return MetaInfo;
}());
exports.MetaInfo = MetaInfo;
//# sourceMappingURL=field-meta.js.map