import { Component, ViewChildren, QueryList } from '@angular/core';
import { HiddenFieldsProvider } from '../../../../main/editors/modules/graphical-editor/providers/properties/hidden-fields-provider';
import { IContainer } from '../../../../../../model/IContainer';
import { GenericForm } from '../../../../../forms/modules/generic-form/components/generic-form.component';
import { SelectedElementService } from '../../selected-element/services/selected-element.service';
import { Type } from '../../../../../../util/type';
import { ProcessStep } from '../../../../../../model/ProcessStep';
import { MetaInfo, FieldMetaItem } from '../../../../../../model/meta/field-meta';

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
        selectedElementService.selectionChanged.subscribe((element: IContainer) => {
            this.hiddenFieldsProvider = new HiddenFieldsProvider(element);
            this._selectedElement = element;

            // try to select the most relvant field in the properties view
            try {
                let meta: FieldMetaItem[] = MetaInfo[element.className];
                if (meta) {
                    meta.sort((item1: FieldMetaItem, item2: FieldMetaItem) =>
                        Number.parseInt(item1.position) - Number.parseInt(item2.position));
                    setTimeout(() => {
                        let inputElement: HTMLElement = document.getElementById('properties-' + meta[0].name + '-textfield');
                        if (inputElement) {
                            (inputElement as HTMLInputElement).select();
                        }
                    }, 10);
                }
            } catch {
                // meta field is not provided. No need to do anything
            }
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
