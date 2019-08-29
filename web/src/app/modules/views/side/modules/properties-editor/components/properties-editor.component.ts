import { Component } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { ProcessStep } from '../../../../../../model/ProcessStep';
import { Type } from '../../../../../../util/type';
import { HiddenFieldsProvider } from '../../../../main/editors/modules/graphical-editor/providers/properties/hidden-fields-provider';

@Component({
    moduleId: module.id.toString(),
    selector: 'properties-editor',
    templateUrl: 'properties-editor.component.html',
    styleUrls: ['properties-editor.component.css']
})
export class PropertiesEditor {

    public isCollapsed = false;

    private hiddenFieldsProvider: HiddenFieldsProvider;
    private _selectedElement: IContainer;

    constructor() { }

    public get selectedElement(): IContainer {
        return this._selectedElement;
    }

    public get hiddenFields(): string[] {
        if (!this.hiddenFieldsProvider) {
            return undefined;
        }
        return this.hiddenFieldsProvider.hiddenFields;
    }

     public get showTracing(): boolean {
        return Type.is(this.selectedElement, ProcessStep);
    }
}
