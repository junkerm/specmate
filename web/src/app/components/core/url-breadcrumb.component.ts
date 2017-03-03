import { Component, Input, OnInit, OnChanges } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'url-breadcrumb',
    templateUrl: 'url-breadcrumb.component.html'
})
export class UrlBreadcrumb implements OnInit {

    @Input()
    url: string;

    parts: string[]

    constructor() { }

    ngOnInit(): void {
        if(this.url) {
            this.parts = this.url.split('/');
        }
        else {
            this.parts = null;
        }
    }

    ngOnChanges(): void {
        this.parts = this.url.split('/');
    }
}