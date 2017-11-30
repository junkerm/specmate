import { Requirement } from '../../../model/Requirement';
import { ConfirmationModal } from '../../../services/notification/confirmation-modal.service';
import { NavigatorService } from '../../../services/navigation/navigator.service';
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Config } from '../../../config/config';
import { SpecmateDataService } from '../../../services/data/specmate-data.service';

import { IContainer } from '../../../model/IContainer';

import { CEGModel } from '../../../model/CEGModel';

import { Url } from '../../../util/Url';
import { Type } from '../../../util/Type';
import { Arrays } from "../../../util/Arrays";

import { FieldMetaItem, MetaInfo } from "../../../model/meta/field-meta";
import { GenericForm } from "../../forms/generic-form.component";
import { EditorCommonControlService } from '../../../services/common-controls/editor-common-control.service';

import { SpecmateViewBase } from '../../core/views/specmate-view-base';
import { TestSpecification } from "../../../model/TestSpecification";
import { GraphicalEditor } from "../../core/graphical/graphical-editor.component";

@Component({
    moduleId: module.id,
    selector: 'ceg-model-details-editor',
    templateUrl: 'ceg-model-details.component.html'
})
export class CEGModelDetails extends SpecmateViewBase {

    @ViewChild(GraphicalEditor)
    private cegEditor: GraphicalEditor;

    private model: CEGModel;
    private contents: IContainer[];

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        editorCommonControlService: EditorCommonControlService
    ) {
        super(dataService, navigator, route, modal, editorCommonControlService);
    }

    protected resolveRequirement(element: IContainer): Promise<Requirement> {
        return this.dataService.readElement(Url.parent(element.url)).then((element: IContainer) => element as Requirement);
    }

    protected onElementResolved(element: IContainer): void {
        this.model = element;
        this.dataService.readContents(this.model.url).then((contents: IContainer[]) => this.contents = contents);
    }

    protected get isValid(): boolean {
        if (!this.cegEditor) {
            return true;
        }
        return this.cegEditor.isValid;
    }
}
