import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { ISpecmateObject } from '../../model/ISpecmateObject';

import 'rxjs/add/operator/switchMap';

@Component({
    moduleId: module.id,
    selector: 'requirements-details',
    templateUrl: 'requirement-details.component.html'
})
export class RequirementsDetails implements OnInit {
    constructor(private dataService: SpecmateDataService, private route: ActivatedRoute) { }

    private requirement: ISpecmateObject;

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.getContent(params['url']))
            .subscribe(requirement => this.requirement = requirement);
    }
}