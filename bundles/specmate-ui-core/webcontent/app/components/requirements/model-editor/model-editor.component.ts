import { CEGEditor } from './ceg-editor.component';
import { Component, OnInit, ViewChild } from '@angular/core';
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

import { ITool } from './tools/i-tool';
import { DeleteTool } from './tools/delete-tool';

import { Url } from '../../../util/Url';
import { ConnectionTool } from './tools/connection-tool';
import { MoveTool } from './tools/move-tool';
import { NodeTool } from './tools/node-tool';
import { Type } from '../../../util/Type';
import { Arrays } from "../../../util/Arrays";

import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/reduce';
import { FieldMetaItem, MetaInfo } from "../../../model/meta/field-meta";
import { GenericForm } from "../../core/forms/generic-form.component";
import { ConfirmationModal } from "../../core/forms/confirmation-modal.service";


@Component({
    moduleId: module.id,
    selector: 'model-editor',
    templateUrl: 'model-editor.component.html'
})
export class ModelEditor implements OnInit {


    @ViewChild(CEGEditor)
    private cegEditor: CEGEditor;

    @ViewChild(GenericForm)
    private form: GenericForm;

    private model: CEGModel;
    private contents: IContainer[];

    constructor(
        private dataService: SpecmateDataService,
        private router: Router,
        private route: ActivatedRoute,
        private modal: ConfirmationModal) { }

    ngOnInit() {
        this.dataService.clearCommits();
        this.route.params
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
            .subscribe((model: IContainer) => {
                this.model = model;
                this.dataService.readContents(this.model.url).then(
                    (contents: IContainer[]) => {
                        this.contents = contents;
                    }
                );
            });
    }

    private get isValid(): boolean {
        if(!this.cegEditor || !this.form) {
            return true;
        }
        return this.cegEditor.isValid && this.form.isValid;
    }

    private save(): void {
        if(!this.isValid) {
            return;
        }
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
            .then(() => this.dataService.deleteElement(this.model.url, true))
            .then(() => this.dataService.commit('Delete'))
            .then(() => this.navigateToRequirement())
            .catch(() => { });
    }

    private doDiscard(): Promise<void> {
        let first: Promise<void> = Promise.resolve();
        if(this.dataService.hasCommits) {
            first = this.modal.open(this.dataService.countCommits + ' unsaved changes are discarded! Continue?');
        }
        return first
            .then(() => this.dataService.clearCommits())
            .then(() => this.dataService.readElement(this.model.url))
            .then((model: IContainer) => this.model = model)
            .then(() => this.dataService.readContents(this.model.url))
            .then((contents: IContainer[]) => this.contents = contents)
            .then(() => this.cegEditor.reset());
    }

    private close(): void {
        this.doDiscard().then(() => this.navigateToRequirement()).catch(() => { });
    }

    private navigateToRequirement(): void {
        this.router.navigate(['/requirements', { outlets: { 'main': [Url.parent(this.model.url)] } }]);
    }
}
