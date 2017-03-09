import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Config } from '../../../config/config';
import { SpecmateDataService } from '../../../services/specmate-data.service';
import { CEGModel } from '../../../model/CEGModel';
import { CEGNode } from '../../../model/CEGNode';
import { CEGConnection } from '../../../model/CEGConnection';
import { Requirement } from '../../../model/Requirement';
import { IContainer } from '../../../model/IContainer';
import { Type } from '../../../util/Type';
import { Url } from '../../../util/Url';
import { Id } from '../../../util/Id';

enum Tools {
    NODE, CONNECTION, DELETE, MOVE
}

@Component({
    moduleId: module.id,
    selector: 'ceg-editor',
    templateUrl: 'ceg-editor.component.html'
})
export class CEGEditor implements OnInit {

    constructor(private formBuilder: FormBuilder, private dataService: SpecmateDataService, private router: Router, private route: ActivatedRoute, private location: Location, private changeDetectorRef: ChangeDetectorRef) {
        this.createForm();
    }

    private model: CEGModel;
    private name: string;
    private cegForm: FormGroup;
    private container: IContainer;
    private rows = Config.CEG_EDITOR_DESCRIPTION_ROWS;
    private isNew: boolean;

    private editorHeight: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;

    private nodeType = CEGNode;
    private connectionType = CEGConnection;

    private activeTool: Tools = Tools.MOVE;
    private selectedConnectionNodes: CEGNode[] = [];
    private selectedNode: CEGNode | CEGConnection;

    activateNodeAdder(): void {
        this.activeTool = Tools.NODE;
    }

    activateConnectionAdder(): void {
        this.selectedConnectionNodes = [];
        this.selectedNode = undefined;
        this.activeTool = Tools.CONNECTION;
    }

    activateDeleter(): void {
        this.activeTool = Tools.DELETE;
    }

    activateMover(): void {
        this.activeTool = Tools.MOVE;
    }

    get isActiveNodeAdder(): boolean {
        return this.isActive(Tools.NODE);
    }

    get isActiveConnectionAdder(): boolean {
        return this.isActive(Tools.CONNECTION);
    }

    get isActiveDeleter(): boolean {
        return this.isActive(Tools.DELETE);
    }

    get isActiveMover(): boolean {
        return this.isActive(Tools.MOVE);
    }

    isActive(tool: Tools) {
        return this.activeTool === tool;
    }

    isSelected(node: CEGNode) {
        return node === this.selectedNode || this.selectedConnectionNodes.indexOf(node) >= 0;
    }

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.getDetails(params['url']))
            .subscribe(container => {
                this.container = container;
                if (Type.is(container, CEGModel)) {
                    this.model = container as CEGModel;
                    this.isNew = false;
                    this.setFormValues();
                }
                else if (Type.is(container, Requirement)) {
                    this.model = new CEGModel();
                    this.model.name = Config.CEG_NEW_NAME;
                    this.model.description = Config.CEG_NEW_DESCRIPTION;
                    this.model['contents'] = [];
                    this.isNew = true;
                    this.setFormValues();
                    this.updateModel(this.cegForm);
                }
            });
    }

    createForm(): void {
        this.cegForm = this.formBuilder.group({
            name: ['', Validators.required],
            description: ''
        });
        this.cegForm.valueChanges.subscribe(formModel => {
            this.updateModel(formModel);
        });
    }

    updateModel(formModel: any): void {
        this.model.name = formModel.name as string;
        this.model.description = formModel.description as string;
        if (this.isNew && this.model.name) {
            this.model.id = Id.fromName(this.model.name);
            this.model.url = Url.build([this.container.url, this.model.id]);
        }
    }

    setFormValues(): void {
        this.cegForm.setValue({
            name: this.model.name,
            description: this.model.description
        });
    }

    save(): void {
        if (this.isNew) {
            this.container['contents'].push(this.model);
        }
        console.log("Persist now!");
    }

    discard(): void {
        //TODO: Really discard new data and go back. Implement a reset button? Reactive Forms in Angular should help.
        console.log("We do not have reset the values of the model! TODO!");
        this.location.back();
    }

    addNode(x: number, y: number): void {
        var node: CEGNode = new CEGNode();
        node.name = Config.CEG_NEW_NAME;
        node.description = Config.CEG_NEW_DESCRIPTION;
        node.x = x;
        node.y = y;
        this.model['contents'].push(node);
        this.changeDetectorRef.detectChanges();
        this.dataService.addElement(node);
        //this.select(node);
    }

    addConnection(n1: CEGNode, n2: CEGNode): void {
        var connection: CEGConnection = new CEGConnection();
        connection.name = "New Connection";
        connection.id = "newconn";
        connection.description = "";
        connection.source = { url: n1.url };
        connection.target = { url: n2.url };
        this.model['contents'].push(connection);
        this.dataService.addElement(connection);
    }

    addNodeToSelectedConnectionNodes(node: CEGNode): void {
        if(this.selectedConnectionNodes.length == 2) {
            this.selectedConnectionNodes = [];
        }
        this.selectedConnectionNodes.push(node);
    }

    delete(element: CEGNode | CEGConnection): void {
        if (Type.is(element, CEGNode)) {
            var connections: CEGConnection[] = this.getConnections(element as CEGNode);
            for(var i = 0; i < connections.length; i++) {
                var connectionIndex: number = this.model['contents'].indexOf(connections[i]);
                if(connectionIndex >= 0) {
                    this.model['contents'].splice(connectionIndex, 1);
                }
            }
        }
        var nodeIndex = this.model['contents'].indexOf(element);
        if (nodeIndex >= 0) {
            this.model['contents'].splice(nodeIndex, 1);
        }
        console.log("DELETION IS NOT PERSISTED YET");
    }

    getConnections(node: CEGNode): CEGConnection[] {
        var connections: CEGConnection[] = [];
        for (var i = 0; i < this.model['contents'].length; i++) {
            var currentElement: CEGNode | CEGConnection = this.model['contents'][i];
            if (Type.is(currentElement, CEGConnection)) {
                var currentConnection: CEGConnection = currentElement as CEGConnection;
                if (currentConnection.source.url === node.url || currentConnection.target.url === node.url) {
                    connections.push(currentConnection);
                }
            }
        }
        return connections;
    }

    select(element: CEGNode | CEGConnection): void {
        this.selectedNode = element;
        this.router.navigate([{ outlets: { 'ceg-node-details': [element.url, 'ceg-node-details'] } }], { relativeTo: this.route });
        if (this.isActiveConnectionAdder && Type.is(element, CEGNode)) {
            this.addNodeToSelectedConnectionNodes(element as CEGNode);
        } else if (this.isActiveDeleter) {
            this.delete(element);
        }
    }

    click(evt: MouseEvent): void {
        if (this.isActiveNodeAdder) {
            this.addNode(evt.offsetX, evt.offsetY);
        } else if (this.isActiveConnectionAdder) {
            if (this.selectedConnectionNodes.length == 2) {
                this.addConnection(this.selectedConnectionNodes[0], this.selectedConnectionNodes[1]);
                this.selectedConnectionNodes = [];
            }
        }
    }
}