import { GenericForm } from '../../core/forms/generic-form.component';
import { ViewChild, Component, Input } from '@angular/core';

import { Type } from '../../../util/Type';

import { CEGNode } from '../../../model/CEGNode'
import { CEGConnection } from '../../../model/CEGConnection'
import { CEGEffectNode } from "../../../model/CEGEffectNode";
import { CEGCauseNode } from "../../../model/CEGCauseNode";

@Component({
    moduleId: module.id,
    selector: 'ceg-node-details',
    templateUrl: 'ceg-node-details.component.html'
})
export class CEGNodeDetails {

    @Input()
    public element: CEGNode;

    @ViewChild(GenericForm)
    private form: GenericForm;

    constructor() { }

    private get isNode(): boolean {
        return Type.is(this.element, CEGNode) || Type.is(this.element, CEGCauseNode) || Type.is(this.element, CEGEffectNode);
    }

    private get isConnection(): boolean {
        return Type.is(this.element, CEGConnection);
    }

    public get isValid(): boolean {
        if (!this.form) {
            return true;
        }
        return this.form.isValid;
    }
}