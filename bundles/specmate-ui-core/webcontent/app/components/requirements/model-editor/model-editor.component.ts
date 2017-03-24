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


@Component({
    moduleId: module.id,
    selector: 'model-editor',
    templateUrl: 'model-editor.component.html'
})
export class ModelEditor implements OnInit {

    private rows = Config.CEG_EDITOR_DESCRIPTION_ROWS;
    private editorHeight: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;

    private model: CEGModel;
    private contents: IContainer[];

    private inputForm: FormGroup;

    private tools: ITool[];
    private activeTool: ITool;

    constructor(
        private formBuilder: FormBuilder,
        private dataService: SpecmateDataService,
        private router: Router,
        private route: ActivatedRoute,
        private modal: ConfirmationModal) {
        this.createForm();
    }

    ngOnInit() {
        this.dataService.clearCommits();
        this.route.params
            .switchMap((params: Params) => {
                return this.dataService.readElement(params['url']);
            })
            .subscribe((model: IContainer) => {
                this.model = model;
                this.dataService.readContents(this.model.url).then(
                    (contents: IContainer[]) => {
                        this.contents = contents;
                        this.setFormValues();
                    }
                );
            });
    }

    createForm(): void {
        this.inputForm = this.formBuilder.group({
            name: ['', Validators.required],
            description: ''
        });
    }

    updateModel(): void {
        let name: string = this.inputForm.controls['name'].value;
        let description: string = this.inputForm.controls['description'].value;

        if (!description) {
            description = '';
        }
        if (name) {
            this.model.name = name;
            this.model.description = description;
        }
    }

    setFormValues(): void {
        this.inputForm.setValue({
            name: this.model.name,
            description: this.model.description
        });
    }

    save(): void {
        this.updateModel();
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

    delete(): void {
        this.modal.open("Do you really want to delete?")
            .then(() => this.dataService.clearCommits())
            .then(() => this.dataService.deleteElement(this.model.url))
            .then(() => {
                return this.dataService.commit('Delete');
            })
            .then(() => {
                this.router.navigate(['/requirements', { outlets: { 'main': [Url.parent(this.model.url)] } }]);
            }).catch(() => { });
    }

    discard(): void {
        this.doDiscard().catch(() => { });
    }

    private doDiscard(): Promise<void> {
        return this.modal.open("Unsaved changes are discarded! Continue?")
            .then(() => this.dataService.clearCommits())
            .then(() => this.dataService.readElement(this.model.url))
            .then((model: IContainer) => {
                this.model = model;
                this.setFormValues();
            })
            .then(() => {
                return this.dataService.readContents(this.model.url);
            })
            .then((contents: IContainer[]) => {
                this.contents = contents;
            });
    }

    close(): void {
        this.doDiscard().then(() => this.router.navigate(['/requirements', { outlets: { 'main': [Url.parent(this.model.url)] } }])).catch(() => { });
    }
}
