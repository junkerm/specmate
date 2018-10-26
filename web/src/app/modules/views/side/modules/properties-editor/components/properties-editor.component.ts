import { Component, ViewChildren, QueryList } from '@angular/core';
import { HiddenFieldsProvider } from '../../../../main/editors/modules/graphical-editor/providers/properties/hidden-fields-provider';
import { IContainer } from '../../../../../../model/IContainer';
import { GenericForm } from '../../../../../forms/modules/generic-form/components/generic-form.component';
import { SelectedElementService } from '../../selected-element/services/selected-element.service';
import { Type } from '../../../../../../util/type';
import { ProcessStep } from '../../../../../../model/ProcessStep';

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

    @ViewChildren(GenericForm)
    private form: QueryList<GenericForm>;

    constructor(private selectedElementService: SelectedElementService) {
        selectedElementService.selectionChanged.subscribe((elements: IContainer[]) => {
            this.hiddenFieldsProvider = new HiddenFieldsProvider(elements[0]);
            this._selectedElement = elements[0];
        });
    }

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
