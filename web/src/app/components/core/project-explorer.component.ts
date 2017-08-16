import { Url } from '../../util/Url';
import { Component, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { IContainer } from '../../model/IContainer';

@Component({
    moduleId: module.id,
    selector: 'project-explorer',
    templateUrl: 'project-explorer.component.html',
    styleUrls: ['project-explorer.component.css']
})
export class ProjectExplorer implements OnInit {

    baseUrl: string = '/';

    rootElements: IContainer[];

    constructor(private dataService: SpecmateDataService) { }

    ngOnInit() {
        this.dataService.readContents(this.baseUrl).then((children: IContainer[]) => this.rootElements = children);
    }
}  