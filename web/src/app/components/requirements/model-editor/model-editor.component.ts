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

    private get nodes(): CEGNode[] {
        return this.contents.filter((element: IContainer) => Type.is(element, CEGNode) || Type.is(element, CEGCauseNode) || Type.is(element, CEGEffectNode)) as CEGNode[];
    }

    private get connections(): CEGConnection[] {
        return this.contents.filter((element: IContainer) => Type.is(element, CEGConnection)) as CEGConnection[];
    }

    private get isValid(): boolean {
        if (!this.cegEditor || !this.form) {
            return true;
        }
        return this.cegEditor.isValid && this.form.isValid;
    }

    private save(): void {
        if (!this.isValid) {
            return;
        }
        // We need to update all nodes to save new positions.

        for (let i = 0; i < this.nodes.length; i++) {
            this.dataService.updateElement(this.nodes[i], true);
        }
        this.dataService.commit('Save');
    }

    private delete(): void {
        this.modal.open('Do you really want to delete all elements in ' + this.model.name + '?')
            .then(() => this.removeAllElements())
            .catch(() => { });
    }

    private removeAllElements(): void {

        for (let i = this.connections.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.connections[i].url, true);
        }
        for (let i = this.nodes.length - 1; i >= 0; i--) {
            this.dataService.deleteElement(this.nodes[i].url, true);
        }
    }

    private doDiscard(): Promise<void> {
        let first: Promise<void> = Promise.resolve();
        if (this.dataService.hasCommits) {
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
