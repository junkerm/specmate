import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Config } from "../../../config/config";
import { SpecmateDataService } from "../../../services/specmate-data.service";

import { IContainer } from "../../../model/IContainer";
import { Requirement } from "../../../model/Requirement";

import { CEGModel } from "../../../model/CEGModel";
import { CEGNode } from "../../../model/CEGNode";
import { CEGCauseNode } from "../../../model/CEGCauseNode";
import { CEGEffectNode } from "../../../model/CEGEffectNode";
import { CEGConnection } from "../../../model/CEGConnection";

import { ITool } from "./tools/ITool";
import { CauseNodeTool } from "./tools/cause-node-tool";
import { EffectNodeTool } from "./tools/effect-node-tool";
import { DeleteTool } from "./tools/delete-tool";

import { Type } from "../../../util/Type";
import { Id } from "../../../util/Id";
import { Url } from "../../../util/Url";
import { ConnectionTool } from "./tools/connection-tool";
import { MoveTool } from "./tools/move-tool";
import { NodeTool } from "./tools/node-tool";


@Component({
    moduleId: module.id,
    selector: 'ceg-editor',
    templateUrl: 'ceg-editor.component.html'
})
export class CEGEditor implements OnInit {

    private rows = Config.CEG_EDITOR_DESCRIPTION_ROWS;
    private editorHeight: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;

    private causeNodeType = CEGCauseNode;
    private nodeType = CEGNode;
    private effectNodeType = CEGEffectNode;
    private connectionType = CEGConnection;

    private model: CEGModel;
    private contents: IContainer[];

    private cegForm: FormGroup;

    private tools: ITool[];
    private activeTool: ITool;

    constructor(private formBuilder: FormBuilder, private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute, private location: Location, private changeDetector: ChangeDetectorRef) {
        this.createForm();
    }

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.getDetails(params['url']))
            .subscribe(model => {
                this.model = model;
                this.dataService.getList(this.model.url).then(
                    (contents: IContainer[]) => {
                        this.contents = contents;
                        this.setFormValues();
                        this.initTools();
                    }
                );
            });
    }

    private initTools(): void {
        this.tools = [
            new MoveTool(),
            //new CauseNodeTool(this.container, this.contents, this.dataService),
            //new EffectNodeTool(this.container, this.contents, this.dataService),
            new NodeTool(this.model, this.contents, this.dataService),
            new ConnectionTool(this.model, this.contents, this.dataService),
            new DeleteTool(this.contents, this.dataService)
        ];

        this.activate(this.tools[0]);
    }

    createForm(): void {
        this.cegForm = this.formBuilder.group({
            name: ['', Validators.required],
            description: ''
        });
        this.cegForm.valueChanges.subscribe(formModel => {
            this.updateModel();
        });
    }

    updateModel(): void {

        var name: string = this.cegForm.controls['name'].value;
        var description: string = this.cegForm.controls['description'].value;

        if (!description) {
            description = "";
        }
        if (name) {
            this.model.name = name;
            this.model.description = description;
        }
    }

    setFormValues(): void {
        this.cegForm.setValue({
            name: this.model.name,
            description: this.model.description
        });
    }

    delete(): void {
        this.dataService.removeDetails(this.model);
        this.router.navigate(['/requirements', { outlets: { 'main': [Url.parent(this.model.url)] } }]);
    }

    discard(): void {
        this.dataService.reGetDetails(this.model.url)
            .then((model: IContainer) => {
                this.model = model;
                this.dataService.reGetList(this.model.url).then((contents: IContainer[]) => {
                    this.contents = contents;
                    this.setFormValues();
                    this.router.navigate(['/requirements', { outlets: { 'main': [this.model.url, 'ceg'] } }]);
                });
            });
    }

    get ready(): boolean {
        return this.dataService.ready;
    }


    /**
     * =================================
     * EDITOR HANDLING
     * =================================
     */

    activate(tool: ITool): void {
        if (!tool) {
            return;
        }
        if (this.activeTool) {
            this.activeTool.deactivate();
        }
        this.activeTool = tool;
        this.activeTool.activate();
    }

    isActive(tool: ITool): boolean {
        return this.activeTool === tool;
    }

    get selectedNodes(): (CEGNode | CEGConnection)[] {
        if (this.activeTool) {
            return this.activeTool.selectedElements;
        }
        return [];
    }

    isSelected(element: CEGNode | CEGConnection) {
        return this.selectedNodes.indexOf(element) >= 0;
    }

    select(element: CEGNode | CEGConnection): void {
        if (this.activeTool) {
            this.activeTool.select(element);
        }
        this.navigateToSelectedElement();
    }

    click(evt: MouseEvent): void {
        if (this.activeTool) {
            this.activeTool.click(evt);
        }
        this.navigateToSelectedElement();
    }

    private navigateToSelectedElement(): void {
        if (this.selectedNodes && this.selectedNodes.length > 0) {
            var selectedNode: CEGNode | CEGConnection = this.selectedNodes[this.selectedNodes.length - 1];
            this.router.navigate([{ outlets: { 'ceg-node-details': [selectedNode.url, 'ceg-node-details'] } }], { relativeTo: this.route });
        }
    }
}