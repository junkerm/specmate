import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { SpecmateDataService } from '../../../services/specmate-data.service';
import { CEGNode } from '../../../model/CEGNode'

@Component({
    moduleId: module.id,
    selector: 'ceg-node-details',
    templateUrl: 'ceg-node-details.component.html'
})
export class CEGNodeDetails implements OnInit {

    node: CEGNode

    constructor(private dataService: SpecmateDataService, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.dataService.getDetails(params['url']))
            .subscribe(node => this.node = node as CEGNode);
    }
}