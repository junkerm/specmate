import { SpecmateDataService } from '../../../services/specmate-data.service';
import { Component, Input } from '@angular/core';
import { FormBuilder } from "@angular/forms";

import { Type } from '../../../util/Type';

import { CEGNode } from '../../../model/CEGNode'
import { CEGConnection } from '../../../model/CEGConnection'
import { CEGEffectNode } from "../../../model/CEGEffectNode";
import { CEGCauseNode } from "../../../model/CEGCauseNode";
import { AbstractForm, FieldType } from "../../../controls/AbstractForm";
import { MetaInfo, FieldMetaItem } from "../../../model/meta/field-meta";

@Component({
    moduleId: module.id,
    selector: 'ceg-node-details',
    templateUrl: 'ceg-node-details.component.html'
})
export class CEGNodeDetails extends AbstractForm {

    protected get fieldMeta(): FieldMetaItem[] {
        if(this.element) {
            return MetaInfo[this.element.className];
        }
        return [];
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