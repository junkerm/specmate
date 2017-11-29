import { Component, ViewChildren, QueryList } from '@angular/core';
import { SelectedElementService } from '../../../services/editor/selected-element.service';
import { IContainer } from '../../../model/IContainer';
import { HiddenFieldsProvider } from '../graphical/providers/hidden-fields-provider';
import { GenericForm } from '../../forms/generic-form.component';

@Component({
    moduleId: module.id,
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
        if(!this.hiddenFieldsProvider) {
            return undefined;
        }
        return this.hiddenFieldsProvider.hiddenFields;
    }
}
