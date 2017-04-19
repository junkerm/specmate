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
    MetaInfo.TestSpecification = [
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
            rows: '8',
            position: '100'
        }];
    MetaInfo.TestParameter = [
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
            rows: '8',
            position: '100'
        }];
    MetaInfo.TestCase = [
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
            rows: '8',
            position: '100'
        }];
    MetaInfo.ParameterAssignment = [
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
            rows: '8',
            position: '100'
        }];
    return MetaInfo;
}());
exports.MetaInfo = MetaInfo;
//# sourceMappingURL=field-meta.js.map