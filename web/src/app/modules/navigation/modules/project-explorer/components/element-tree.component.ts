import { Component, OnInit, Input } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../navigator/services/navigator.service';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { IContainer } from '../../../../../model/IContainer';
import { Url } from '../../../../../util/url';
import { Type } from '../../../../../util/type';
import { Requirement } from '../../../../../model/Requirement';
import { CEGModel } from '../../../../../model/CEGModel';
import { Folder } from '../../../../../model/Folder';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { Process } from '../../../../../model/Process';

@Component({
    moduleId: module.id.toString(),
    selector: 'element-tree',
    templateUrl: 'element-tree.component.html',
    styleUrls: ['element-tree.component.css']
})
export class ElementTree implements OnInit {

    @Input()
    public baseUrl: string;

    @Input()
    public parent: IContainer;

    private _currentElement: IContainer;

    public get currentElement(): IContainer {
        return this._currentElement;
    }

    @Input()
    public set currentElement(currentElement: IContainer) {
        this._currentElement = currentElement;
        if (this.isMustOpen) {
            this.initContents();
        }
    }

    @Input()
    public withExpand: boolean;

    private _element: IContainer;
    public get element(): IContainer {
        return this._element;
    }

    @Input()
    public set element(element: IContainer) {
        this._element = element;
        if (this.isMustOpen) {
            this.initContents();
        }
    }

    public contents: IContainer[];

    public _expanded = false;
    public get expanded(): boolean {
        if (!this._expanded && this.isMustOpen) {
            this._expanded = true;
        }
        return this._expanded;
    }
    public set expanded(expanded: boolean) {
        this._expanded = expanded;
    }

    public get canExpand(): boolean {
        return (this.isCEGModelNode || this.isProcessNode || this.isRequirementNode || this.isFolderNode)
            && this.withExpand;
    }

    private get isMustOpen(): boolean {
        if (this._currentElement && this.element) {
            return Url.isParent(this.element.url, this._currentElement.url);
        }
        return false;
    }

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService, private logger: LoggingService) { }

    ngOnInit() {
        this.dataService.readElement(this.baseUrl).then((element: IContainer) => {
            this.element = element;
        });
        if (this.expanded || this.isMustOpen) {
            this.initContents();
        }
    }

    private toggle(): void {
        this.expanded = !this._expanded;
        if (this.expanded && !this.contents) {
            this.initContents();
        }
    }

    private initContents(): void {
        this.dataService.readContents(this.baseUrl).then((contents: IContainer[]) => {
            this.contents = contents;
        });
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
        return this.isTestSpecificationNode && this.parent && (Type.is(this.parent, CEGModel) || Type.is(this.parent, Process));
    }

    public get isProcessNode(): boolean {
        return Type.is(this.element, Process);
    }

    public get isActive(): boolean {
        if (!this.element || !this.navigator.currentElement) {
            return false;
        }
        return this.element.url === this.navigator.currentElement.url;
    }

    public get showElement(): boolean {
        return this.isCEGModelNode || this.isProcessNode || this.isRequirementNode
            || this.isTestSpecificationNode || this.isFolderNode;
    }
}
