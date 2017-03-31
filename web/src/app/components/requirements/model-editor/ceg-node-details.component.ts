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

    @Input()
    private element: CEGNode;

    constructor() { }

    private get isNode(): boolean {
        return Type.is(this.element, CEGNode) || Type.is(this.element, CEGCauseNode) || Type.is(this.element, CEGEffectNode);
    }

    private get isConnection(): boolean {
        return Type.is(this.element, CEGConnection);
    }
}