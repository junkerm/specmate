import {TestSpecification} from '../../model/TestSpecification';
import { Component, Input, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { Folder } from '../../model/Folder';
import { Requirement } from '../../model/Requirement';
import { CEGModel } from '../../model/CEGModel';
import { Type } from '../../util/Type';
import { NavigatorService } from "../../services/navigator.service";
import { Strings } from "../../util/Strings";
import { Url } from "../../util/Url";

@Component({
    moduleId: module.id,
    selector: 'element-tree',
    templateUrl: 'element-tree.component.html',
    styleUrls: ['element-tree.component.css']
})
export class ElementTree implements OnInit {
    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) { }

    @Input()
    public baseUrl: string;

    @Input()
    public parent: IContainer;

    @Input()
    public currentElement: IContainer;

    public element: IContainer;
    public contents: IContainer[];

    public _expanded: boolean = false;
    public get expanded(): boolean {
        if(!this._expanded && this.isMustOpen) {
            this._expanded = true;
        }
        return this._expanded;
    }
    public set expanded(expanded: boolean) {
        this._expanded = expanded;
    }

    private get isMustOpen(): boolean {
        if(this.currentElement && this.element) {
            return Url.isParent(this.element.url, this.currentElement.url);
        }
        return false;
    }

    ngOnInit() {
        this.dataService.readElement(this.baseUrl).then((element: IContainer) => {
            this.element = element;
        });
        this.dataService.readContents(this.baseUrl).then((contents: IContainer[]) => {
            this.contents = contents;
        });
    }

    private toggle(): void {
        this.expanded = !this._expanded;
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

    public get isGeneratedTestSpecificationNode(): boolean {
        return this.isTestSpecificationNode && this.parent && Type.is(this.parent, CEGModel);
    }

    public get isActive(): boolean {
        if(!this.element || !this.navigator.currentElement) {
            return false;
        }
        return this.element.url === this.navigator.currentElement.url;
    }
}