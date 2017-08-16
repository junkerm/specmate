import {TestSpecification} from '../../model/TestSpecification';
import { Component, Input, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { Folder } from '../../model/Folder';
import { Requirement } from '../../model/Requirement';
import { CEGModel } from '../../model/CEGModel';
import { Type } from '../../util/Type';

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

    @Input()
    parentUrl: string;

    @Input()
    activeElementUrl: string;

    element: IContainer;
    elements: IContainer[];

    expanded: boolean = false;

    ngOnInit() {
        this.dataService.readElement(this.baseUrl).then((element: IContainer) => {
            this.element = element;
        });
        this.dataService.readContents(this.baseUrl).then((contents: IContainer[]) => {
            this.elements = contents;
        });
    }

    private toggle(): void {
        this.expanded = !this.expanded;
    }

    public get isRequirementNode(): boolean {
        return Type.is(this.element, Requirement);
    }

    public get isCEGModelNode(): boolean {
        return Type.is(this.element, CEGModel);
    }

    public get isFolderNode(): boolean {
        return Type.is(this.element, Folder);
    }

    public get isTestSpecificationNode(): boolean {
        return Type.is(this.element, TestSpecification);
    }

    public get isActive(): boolean {
        return this.element.url === this.activeElementUrl;
    }
}