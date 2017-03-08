import { Component, Input, OnInit, OnChanges } from '@angular/core';
import { Url } from '../../util/Url';

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
        this.setUrlParts();
    }

    ngOnChanges(): void {
        this.setUrlParts();
    }

    setUrlParts(): void {
        this.parts = Url.parts(this.url);
    }
}