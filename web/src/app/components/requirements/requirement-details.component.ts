import {Process} from '../../model/Process';
import { IContentElement } from '../../model/IContentElement';
import { CEGNode } from '../../model/CEGNode';
import { Type } from '../../util/Type';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Config } from '../../config/config';
import { CEGModel } from '../../model/CEGModel';
import { IContainer } from '../../model/IContainer';
import { Requirement } from '../../model/Requirement';
import { TestSpecification } from '../../model/TestSpecification';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { Id } from '../../util/Id';
import { Url } from '../../util/Url';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ConfirmationModal } from "../../services/notification/confirmation-modal.service";
import { EditorCommonControlService } from '../../services/common-controls/editor-common-control.service'
import { NavigatorService } from "../../services/navigation/navigator.service";
import { SpecmateViewBase } from '../core/views/specmate-view-base';
import { TestSpecificationGenerator } from '../core/common/test-specification-generator';


@Component({
    moduleId: module.id,
    selector: 'requirements-details',
    templateUrl: 'requirement-details.component.html',
    styleUrls: ['requirement-details.component.css']
})

export class RequirementsDetails extends TestSpecificationGenerator {

    public cegModelType = CEGModel;
    public processModelType = Process;

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

    public createModel(): void {
        this.createElement(new CEGModel(), Config.CEG_NEW_MODEL_NAME, Config.CEG_NEW_MODEL_DESCRIPTION);
    }
    
    public createProcess(): void {
        this.createElement(new Process(), Config.PROCESS_NEW_PROCESS_NAME, Config.PROCESS_NEW_PROCESS_DESCRIPTION);
    }

    public get cegModels(): CEGModel[] {
        return this.requirementContents.filter((element: IContainer) => Type.is(element, this.cegModelType));
    }

    public get processModels(): Process[] {
        return this.requirementContents.filter((element: IContainer) => Type.is(element, this.processModelType));
    }

    private createElement(element: IContainer, name: string, description: string): void {
        if (!this.requirementContents) {
            return;
        }
        element.id = Id.uuid;
        element.url = Url.build([this.requirement.url, element.id]);
        element.name = name;
        element.description = description;

        this.dataService.createElement(element, true, Id.uuid)
            .then(() => this.dataService.readContents(element.url, true))
            .then((contents: IContainer[]) => this.requirementContents = contents)
            .then(() => this.dataService.commit('Create'))
            .then(() => this.navigator.navigate(element));
    }

    protected get isValid(): boolean {
        return true;
    }
}
