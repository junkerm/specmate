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
            type: 'text'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText'
        }];
    MetaInfo.CEGModel = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText'
        }];
    MetaInfo.CEGNode = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText'
        }, {
            name: "type",
            shortDesc: 'Type',
            longDesc: 'The type of a node',
            required: true,
            type: 'singleSelection',
            values: '["AND", "OR"]'
        }, {
            name: "value",
            shortDesc: 'Value',
            longDesc: 'The value of a node',
            required: true,
            type: 'text'
        }, {
            name: "operator",
            shortDesc: 'Operator',
            longDesc: 'The value of a node',
            required: true,
            type: 'singleSelection',
            values: '["=", "<", "<=", ">=", ">"]'
        }, {
            name: "variable",
            shortDesc: 'Variable',
            longDesc: 'The variable of a node',
            required: true,
            type: 'text'
        }];
    MetaInfo.CEGConnection = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText'
        }, {
            name: "negate",
            shortDesc: 'Negate',
            longDesc: 'Negation of this connection',
            type: 'checkbox'
        }];
    MetaInfo.CEGCauseNode = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText'
        }, {
            name: "type",
            shortDesc: 'Type',
            longDesc: 'The type of a node',
            required: true,
            type: 'singleSelection',
            values: '["AND", "OR"]'
        }, {
            name: "value",
            shortDesc: 'Value',
            longDesc: 'The value of a node',
            required: true,
            type: 'text'
        }, {
            name: "operator",
            shortDesc: 'Operator',
            longDesc: 'The value of a node',
            required: true,
            type: 'singleSelection',
            values: '["=", "<", "<=", ">=", ">"]'
        }, {
            name: "variable",
            shortDesc: 'Variable',
            longDesc: 'The variable of a node',
            required: true,
            type: 'text'
        }];
    MetaInfo.CEGEffectNode = [
        {
            name: "name",
            shortDesc: 'Name',
            longDesc: 'The name of an element',
            required: true,
            type: 'text'
        }, {
            name: "description",
            shortDesc: 'Description',
            longDesc: 'The element\'s description',
            required: false,
            type: 'longText'
        }, {
            name: "type",
            shortDesc: 'Type',
            longDesc: 'The type of a node',
            required: true,
            type: 'singleSelection',
            values: '["AND", "OR"]'
        }, {
            name: "value",
            shortDesc: 'Value',
            longDesc: 'The value of a node',
            required: true,
            type: 'text'
        }, {
            name: "operator",
            shortDesc: 'Operator',
            longDesc: 'The value of a node',
            required: true,
            type: 'singleSelection',
            values: '["=", "<", "<=", ">=", ">"]'
        }, {
            name: "variable",
            shortDesc: 'Variable',
            longDesc: 'The variable of a node',
            required: true,
            type: 'text'
        }];
    return MetaInfo;
}());
exports.MetaInfo = MetaInfo;
//# sourceMappingURL=field-meta.js.map