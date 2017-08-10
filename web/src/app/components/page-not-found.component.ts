import {Url} from '../util/Url';
import { Component, OnInit } from '@angular/core';
import { Params, ActivatedRoute } from '@angular/router';

@Component({
    moduleId: module.id,
    selector: 'page-not-found',
    templateUrl: 'page-not-found.component.html'
})
export class PageNotFound implements OnInit {
    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.url = Url.fromParams(params));
    }

    public url: string;

    constructor(private route: ActivatedRoute) {
        this.route.params
            .switchMap((params: Params) => this.url = Url.fromParams(params));
    }

    ngDoCheck() {
        this.route.params
            .switchMap((params: Params) => this.url = Url.fromParams(params));
    }
}