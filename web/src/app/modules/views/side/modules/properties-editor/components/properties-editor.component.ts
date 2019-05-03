import { Component, QueryList, ViewChildren } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { FieldMetaItem, MetaInfo } from '../../../../../../model/meta/field-meta';
import { ProcessStep } from '../../../../../../model/ProcessStep';
import { Type } from '../../../../../../util/type';
import { GenericForm } from '../../../../../forms/modules/generic-form/components/generic-form.component';
import { HiddenFieldsProvider } from '../../../../main/editors/modules/graphical-editor/providers/properties/hidden-fields-provider';
import { SelectedElementService } from '../../selected-element/services/selected-element.service';

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

            // try to select the most relevant field in the properties view
            try {
                let meta: FieldMetaItem[] = MetaInfo[this._selectedElement.className];
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
