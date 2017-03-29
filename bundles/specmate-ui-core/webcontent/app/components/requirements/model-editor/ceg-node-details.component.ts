import { SpecmateDataService } from '../../../services/specmate-data.service';
import { Component, Input } from '@angular/core';
import { FormBuilder } from "@angular/forms";

import { Type } from '../../../util/Type';

import { CEGNode } from '../../../model/CEGNode'
import { CEGConnection } from '../../../model/CEGConnection'
import { CEGEffectNode } from "../../../model/CEGEffectNode";
import { CEGCauseNode } from "../../../model/CEGCauseNode";
import { AbstractForm, FieldType, FieldMetaItem } from "../../../controls/AbstractForm";

@Component({
    moduleId: module.id,
    selector: 'ceg-node-details',
    templateUrl: 'ceg-node-details.component.html'
})
export class CEGNodeDetails extends AbstractForm {

    private formMetaNode: FieldMetaItem[] = [
        {
            name: 'name',
            shortDesc: 'Name',
            longDesc: 'The name of the node',
            required: true,
            type: FieldType.TEXT
        },
        {
            name: 'type',
            shortDesc: 'Type',
            longDesc: 'The type of the node',
            required: true,
            type: FieldType.TEXT
        },
        {
            name: 'variable',
            shortDesc: 'Variable',
            longDesc: 'The variable of the node',
            required: true,
            type: FieldType.TEXT
        },
        {
            name: 'operator',
            shortDesc: 'Operator',
            longDesc: 'The operator of the node',
            required: true,
            type: FieldType.TEXT
        },
        {
            name: 'value',
            shortDesc: 'Value',
            longDesc: 'The value of the node',
            required: true,
            type: FieldType.TEXT
        },
        {
            name: 'description',
            shortDesc: 'Description',
            longDesc: 'The description of the node',
            type: FieldType.TEXT_LONG
        }
    ];

    private formMetaConnection: FieldMetaItem[] = [
        {
            name: 'name',
            shortDesc: 'Name',
            longDesc: 'The name of the connection',
            required: true,
            type: FieldType.TEXT
        },
        {
            name: 'negate',
            shortDesc: 'Negate',
            longDesc: 'The negation of the connection',
            type: FieldType.CHECKBOX
        },
        {
            name: 'description',
            shortDesc: 'Description',
            longDesc: 'The description of the connection',
            type: FieldType.TEXT_LONG
        },
    ];

    protected get fieldMeta(): FieldMetaItem[] {
        return this.isNode ? this.formMetaNode : this.formMetaConnection;
    }
    protected get formModel(): any {
        return this.element;
    }

    private _element: CEGNode;

    private get element(): CEGNode {
        return this._element;
    }

    @Input()
    private set element(element: CEGNode) {
        this._element = element;
        this.createForm();
        this.update();
    }

    constructor(formBuilder: FormBuilder, dataService: SpecmateDataService) {
        super(formBuilder, dataService);
        this.update();
    }

    private setUpChangeListener(): void {
        this.inputForm.valueChanges.subscribe(() => {
            this.updateFormModel();
            return '';
        });
    }

    private get isNode(): boolean {
        return Type.is(this.element, CEGNode) || Type.is(this.element, CEGCauseNode) || Type.is(this.element, CEGEffectNode);
    }

    private get isConnection(): boolean {
        return Type.is(this.element, CEGConnection);
    }

    public update(): void {
        this.updateForm();
        this.setUpChangeListener();
    }
}