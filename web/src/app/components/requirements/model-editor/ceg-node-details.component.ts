import { Component, Input, ViewChild } from '@angular/core';
import { FormBuilder } from "@angular/forms";

import { Type } from '../../../util/Type';
import { SpecmateDataService } from '../../../services/specmate-data.service';

import { CEGNode } from '../../../model/CEGNode'
import { CEGConnection } from '../../../model/CEGConnection'
import { CEGEffectNode } from "../../../model/CEGEffectNode";
import { CEGCauseNode } from "../../../model/CEGCauseNode";
import { MetaInfo, FieldMetaItem } from "../../../model/meta/field-meta";
import { AbstractForm } from "../../core/forms/abstract-form.component";

@Component({
    moduleId: module.id,
    selector: 'ceg-node-details',
    templateUrl: 'ceg-node-details.component.html'
})
export class CEGNodeDetails {

    @ViewChild(AbstractForm)
    private form: AbstractForm;

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
        this.form.createForm();
        this.update();
    }

    constructor() {
        this.update();
    }

    private setUpChangeListener(): void {
        this.form.inputForm.valueChanges.subscribe(() => {
            this.form.updateFormModel();
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
        this.form.updateForm();
        this.setUpChangeListener();
    }
}