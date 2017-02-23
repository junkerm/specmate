import { Component, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { ISpecmateObject } from '../../model/ISpecmateObject';

@Component({
    moduleId:module.id,
    templateUrl:'project-explorer.component.html',
    selector:'project-explorer'
})
export class ProjectExplorer implements OnInit {

    baseUrl: string = '/';

    constructor(private dataService: SpecmateDataService) {}

    rootElements:ISpecmateObject[];

    ngOnInit(){
        this.dataService.getChildren(this.baseUrl).then(children => this.rootElements = children);
    }
}  