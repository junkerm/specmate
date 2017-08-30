import { Url } from '../../util/Url';
import { Component, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/data/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { NavigatorService } from "../../services/navigation/navigator.service";

@Component({
    moduleId: module.id,
    selector: 'project-explorer',
    templateUrl: 'project-explorer.component.html',
    styleUrls: ['project-explorer.component.css']
})
export class ProjectExplorer implements OnInit {

    baseUrl: string = '/';

    rootElements: IContainer[];

    public get currentElement(): IContainer {
        return this.navigator.currentElement;
    }

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) { }

    ngOnInit() {
        this.dataService.readContents(this.baseUrl).then((children: IContainer[]) => this.rootElements = children);
    }
}  