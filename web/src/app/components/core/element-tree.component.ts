import { Component, Input, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { Folder } from '../../model/Folder';

@Component({
    moduleId: module.id,
    selector: 'element-tree',
    templateUrl: 'element-tree.component.html',
    styleUrls: ['element-tree.component.css']
})
export class ElementTree implements OnInit {
    constructor(private dataService: SpecmateDataService) { }

    @Input()
    baseUrl: string;

    element: IContainer = new Folder();
    elements: IContainer[];

    expanded: boolean = false;

    ngOnInit() {
        this.dataService.getDetails(this.baseUrl).then(element => { this.element = element; });
        this.dataService.getList(this.baseUrl).then(children => { this.elements = children; });
    }

    private toggle(): void {
        this.expanded = !this.expanded;
    }

    public get isRequirement(): boolean {
        this.logType("Requirement");
        return typeof this.element === "Requirement";
    } 
    
    public get isCEGModelNode(): boolean {
        this.logType("CEGModel");
        return typeof this.element === "CEGModel";
    }

    public get isFolderNode(): boolean {
        this.logType("Folder");
        return typeof this.element === "Folder";
    }

    private logType(type: string): void {
        console.log("TYPE " + type + ": " + typeof this.element === type);
    }
}