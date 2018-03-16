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

    private hiddenFieldsProvider: HiddenFieldsProvider;
    private _selectedElement: IContainer;

    @ViewChildren(GenericForm)
    private form: QueryList<GenericForm>;

    constructor(private selectedElementService: SelectedElementService) {
        selectedElementService.selectionChanged.subscribe((element: IContainer) => {
            this.hiddenFieldsProvider = new HiddenFieldsProvider(element);
            this._selectedElement = element;
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
