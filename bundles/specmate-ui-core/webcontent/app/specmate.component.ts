import { Url } from './util/Url';
import { Params, ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';

/**
 * This is the Specmate main component
 */
@Component({
    selector:'specmate',
    moduleId:module.id,
    templateUrl:'specmate.component.html',
    styleUrls:['specmate.component.css']
})

export class SpecmateComponent {
    public title: string = "Specmate";

    public url: string;

    constructor(private route: ActivatedRoute) {
        this.route.params.switchMap((params: Params) => this.url = Url.fromParams(params));
    }

}