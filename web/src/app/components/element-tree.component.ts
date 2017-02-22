import { Component, Input, OnInit } from '@angular/core';
import { SpecmateDataService } from '../services/specmate-data.service';
import { ISpecmateObject } from '../model/ISpecmateObject';

@Component({
    moduleId:module.id,
    templateUrl:'element-tree.component.html',
    selector:'element-tree'
})
export class ElementTree implements OnInit {
    constructor(private dataService: SpecmateDataService) {}

    @Input() 
    baseUrl: string;

    elements:ISpecmateObject[];

    ngOnInit(){
        this.elements = this.dataService.getChildren(this.baseUrl);
    }
}  