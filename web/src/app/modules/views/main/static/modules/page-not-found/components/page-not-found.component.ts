import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    moduleId: module.id.toString(),
    selector: 'page-not-found',
    templateUrl: 'page-not-found.component.html'
})
export class PageNotFound {
    constructor(private router: Router) { }

    public get route(): string {
        return this.router.url;
    }
}
