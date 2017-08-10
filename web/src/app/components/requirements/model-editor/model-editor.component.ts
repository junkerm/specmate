import { CEGEditor } from './ceg-editor.component';
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Config } from '../../../config/config';
import { SpecmateDataService } from '../../../services/specmate-data.service';

import { IContainer } from '../../../model/IContainer';

import { CEGModel } from '../../../model/CEGModel';

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
import { EditorCommonControlService } from '../../../services/editor-common-control.service';


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
        private route: ActivatedRoute,
        private editorCommonControlService: EditorCommonControlService,
        private changeDetectorRef: ChangeDetectorRef) { }

    ngOnInit() {
        this.editorCommonControlService.showCommonControls = true;
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

    ngDoCheck(args: any) {
        this.editorCommonControlService.isCurrentEditorValid = this.isValid;
        this.changeDetectorRef.detectChanges();
    }

    private get isValid(): boolean {
        if (!this.cegEditor || !this.form) {
            return true;
        }
        return this.cegEditor.isValid && this.form.isValid;
    }
}
