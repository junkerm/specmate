import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Config } from '../../../config/config';
import { SpecmateDataService } from '../../../services/specmate-data.service';

import { IContainer } from '../../../model/IContainer';

import { CEGModel } from '../../../model/CEGModel';
import { CEGNode } from '../../../model/CEGNode';
import { CEGCauseNode } from '../../../model/CEGCauseNode';
import { CEGEffectNode } from '../../../model/CEGEffectNode';
import { CEGConnection } from '../../../model/CEGConnection';

import { ITool } from './tools/ITool';
import { DeleteTool } from './tools/delete-tool';

import { Url } from '../../../util/Url';
import { ConnectionTool } from './tools/connection-tool';
import { MoveTool } from './tools/move-tool';
import { NodeTool } from './tools/node-tool';
import { Type } from '../../../util/Type';
import { ConfirmationModal } from "../../core/confirmation-modal.service";
import { Arrays } from "../../../util/Arrays";
import { AbstractForm } from "../../../controls/AbstractForm";

import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/reduce';


@Component({
    moduleId: module.id,
    selector: 'model-editor',
    templateUrl: 'model-editor.component.html'
})
export class ModelEditor extends AbstractForm implements OnInit {

    private rows = Config.CEG_EDITOR_DESCRIPTION_ROWS;
    private editorHeight: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;

    private model: CEGModel;
    private contents: IContainer[];

    private tools: ITool[];
    private activeTool: ITool;

    protected get formModel(): any { return this.model; }
    protected get formFields(): string[] { return ['name', 'description']; }
    protected get requiredFields(): string[] { return ['name']; }

    constructor(
        formBuilder: FormBuilder,
        private dataService: SpecmateDataService,
        private router: Router,
        private route: ActivatedRoute,
        private modal: ConfirmationModal) {
        super(formBuilder);
    }

    ngOnInit() {
        this.dataService.clearCommits();
        this.route.params
            .switchMap((params: Params) => {
                return this.dataService.readElement(Url.fromParams(params));
            })
            .subscribe((model: IContainer) => {
                this.model = model;
                this.dataService.readContents(this.model.url).then(
                    (contents: IContainer[]) => {
                        this.contents = contents;
                        this.updateForm();
                    }
                );
            });
    }

    private save(): void {
        this.updateFormModel();
        this.dataService.updateElement(this.model, true);

        // We need to update all nodes to save new positions.
        for (let i = 0; i < this.contents.length; i++) {
            let currentElement: IContainer = this.contents[i];
            if (Type.is(currentElement, CEGNode) || Type.is(currentElement, CEGCauseNode) || Type.is(currentElement, CEGEffectNode)) {
                this.dataService.updateElement(this.contents[i], true);
            }
        }
        this.dataService.commit('Save');
    }

    private delete(): void {
        this.modal.open('Do you really want to delete ' + this.model.name + '?')
            .then(() => this.dataService.clearCommits())
            .then(() => this.dataService.deleteElement(this.model.url))
            .then(() => {
                return this.dataService.commit('Delete');
            })
            .then(() => this.navigateToRequirement()).catch(() => { });
    }

    private discard(): void {
        this.doDiscard().catch(() => { });
    }

    private doDiscard(): Promise<void> {
        return this.modal.open('Unsaved changes are discarded! Continue?')
            .then(() => this.dataService.clearCommits())
            .then(() => this.dataService.readElement(this.model.url))
            .then((model: IContainer) => {
                this.model = model;
                this.updateForm();
            })
            .then(() => {
                return this.dataService.readContents(this.model.url);
            })
            .then((contents: IContainer[]) => {
                this.contents = contents;
            });
    }

    private close(): void {
        this.doDiscard().then(() => this.navigateToRequirement()).catch(() => { });
    }

    private navigateToRequirement(): void {
        this.router.navigate(['/requirements', { outlets: { 'main': [Url.parent(this.model.url)] } }]);
    }
}
