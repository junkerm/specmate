import { Component, Input, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { ISpecmateObject } from '../../model/ISpecmateObject';

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

    element: ISpecmateObject = {};
    elements: ISpecmateObject[];

    expanded: boolean = false;

    ngOnInit() {
        this.dataService.getContent(this.baseUrl).then(element => { this.element = element; });
        this.dataService.getChildren(this.baseUrl).then(children => { this.elements = children; });
    }

    private toggle(): void {
        this.expanded = !this.expanded;
    }
}