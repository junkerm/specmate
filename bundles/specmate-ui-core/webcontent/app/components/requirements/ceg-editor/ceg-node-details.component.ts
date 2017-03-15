import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Type } from '../../../util/Type';

import { SpecmateDataService } from '../../../services/specmate-data.service';
import { CEGNode } from '../../../model/CEGNode'
import { CEGConnection } from '../../../model/CEGConnection'

@Component({
    moduleId: module.id,
    selector: 'ceg-node-details',
    templateUrl: 'ceg-node-details.component.html'
})
export class CEGNodeDetails implements OnInit {

    element: CEGNode

    constructor(private dataService: SpecmateDataService, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.dataService.getElement(params['url']))
            .subscribe(node => this.element = node as CEGNode);
    }

    get isNode(): boolean {
        return Type.is(this.element, CEGNode);
    }

    get isConnection(): boolean {
        return Type.is(this.element, CEGConnection);
    }
}