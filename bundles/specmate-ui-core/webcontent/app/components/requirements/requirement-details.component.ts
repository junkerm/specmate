import { IContentElement } from '../../model/IContentElement';
import { CEGEffectNode } from '../../model/CEGEffectNode';
import { CEGCauseNode } from '../../model/CEGCauseNode';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/Type';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Config } from '../../config/config';
import { CEGModel } from '../../model/CEGModel';
import { IContainer } from '../../model/IContainer';
import { Requirement } from '../../model/Requirement';
import { TestSpecification } from '../../model/TestSpecification';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Id } from '../../util/Id';
import { Url } from '../../util/Url';
import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute,  Params} from '@angular/router';
import { ConfirmationModal } from "../core/forms/confirmation-modal.service";
import { EditorCommonControlService } from '../../services/editor-common-control.service'
import { NavigatorService } from "../../services/navigator.service";
import { SpecmateViewBase } from '../core/views/specmate-view-base';
import { TestSpecificationGenerator } from './test-specification-generator';


@Component({
    moduleId: module.id,
    selector: 'requirements-details',
    templateUrl: 'requirement-details.component.html',
    styleUrls: ['requirement-details.component.css']
})

export class RequirementsDetails extends TestSpecificationGenerator {

    cegModelType = CEGModel;

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        editorCommonControlService: EditorCommonControlService
    ) {
        super(dataService, modal, route, navigator, editorCommonControlService);
    }

    protected resolveRequirement(element: IContainer): Promise<Requirement> {
        return Promise.resolve(element as Requirement);
    }

    delete(element: IContentElement): void {
        this.modal.open("Do you really want to delete " + element.name + "?")
            .then(() => this.dataService.deleteElement(element.url, true, Id.uuid))
            .then(() => this.dataService.commit('Delete'))
            .then(() => this.dataService.readContents(this.requirement.url, true))
            .then((contents: IContainer[]) => this.requirementContents = contents)
            .then(() => this.readAllTestSpecifications())
            .catch(() => {});
    }

    createModel(): void {
        if (!this.requirementContents) {
            return;
        }
        let model: CEGModel = new CEGModel();
        model.id = Id.uuid;
        let modelUrl: string = Url.build([this.requirement.url, model.id]);
        model.url = modelUrl;
        model.name = Config.CEG_NEW_MODEL_NAME;
        model.description = Config.CEG_NEW_NODE_DESCRIPTION;

        this.dataService.createElement(model, true, Id.uuid)
            .then(() => this.dataService.readContents(model.url, true))
            .then((contents: IContainer[]) => this.requirementContents = contents)
            .then(() => this.dataService.commit('Create'))
            .then(() => this.navigator.navigate(model));
    }

    protected get isValid(): boolean {
        return true;
    }
}
