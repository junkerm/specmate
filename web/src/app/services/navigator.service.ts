import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { IContainer } from "../model/IContainer";

@Injectable()
export class NavigatorService {
    constructor(private router: Router) { }

    public navigate(element: IContainer) : void {
        console.log('NAV TARGET ' + element.className + ' URL: ' + element.url);
        this.router.navigate([element.className, element.url]);
    }
}
