import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { SpecmateDataService } from '../../services/specmate-data.service';
import { Requirement } from '../../model/Requirement';
import { CEGModel } from '../../model/CEGModel';

import 'rxjs/add/operator/switchMap';

@Component({
    moduleId: module.id,
    selector: 'requirements-details',
    templateUrl: 'requirement-details.component.html',
    styleUrls: ['requirement-details.component.css']
})

export class RequirementsDetails implements OnInit {
    constructor(private dataService: SpecmateDataService, private route: ActivatedRoute) { }

    private requirement: Requirement;

    ngOnInit() {
        this.route.params
            .switchMap((params: Params) => this.dataService.getDetails(params['url']))
            .subscribe(requirement => this.requirement = requirement as Requirement);
    }

    delete(model: CEGModel): void {
        var contents = this.requirement['contents'];
        var index = contents.indexOf(model);
        contents.splice(index, 1);
    }
}