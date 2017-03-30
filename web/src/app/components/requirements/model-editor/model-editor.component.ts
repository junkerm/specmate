import { CEGEditor } from './ceg-editor.component';
import { ViewChild, Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
import { ConfirmationModal } from "../../core/confirmation-modal.service";
import { Arrays } from "../../../util/Arrays";
import { AbstractForm, FieldType } from "../../../controls/AbstractForm";

import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/reduce';
import { FieldMetaItem, MetaInfo } from "../../../model/meta/field-meta";


@Component({
    moduleId: module.id,
    selector: 'model-editor',
    templateUrl: 'model-editor.component.html'
})
export class ModelEditor extends AbstractForm implements OnInit {


    @ViewChild(CEGEditor)
    private cegEditor: CEGEditor;

    private rows = Config.CEG_EDITOR_DESCRIPTION_ROWS;

    private model: CEGModel;
    private contents: IContainer[];

    protected get fieldMeta(): FieldMetaItem[] {
        return MetaInfo.CEGModel;
    }


    protected get formModel(): any { return this.model; }

    constructor(
        formBuilder: FormBuilder,
        dataService: SpecmateDataService,
        private router: Router,
        private route: ActivatedRoute,
        private modal: ConfirmationModal,
        private changeDetectorRef: ChangeDetectorRef) {
        super(formBuilder, dataService);
    }

    ngOnInit() {
        this.dataService.clearCommits();
        this.route.params
            .switchMap((params: Params) => this.dataService.readElement(Url.fromParams(params)))
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
            .then(() => this.dataService.commit('Delete'))
            .then(() => this.navigateToRequirement())
            .catch(() => { });
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
