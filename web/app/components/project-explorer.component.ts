import { Component, OnInit } from '@angular/core';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { ISpecmateObject } from '../../model/ISpecmateObject';

@Component({
    moduleId:module.id,
    templateUrl:'project-explorer.component.html',
    selector:'project-explorer'
})
export class ProjectExplorer implements OnInit {
    constructor(private dataService: SpecmateDataService) {}

    rootObjects:ISpecmateObject[];

    ngOnInit(){
        this.rootObjects = this.dataService.getChildren('/');
    }
}  