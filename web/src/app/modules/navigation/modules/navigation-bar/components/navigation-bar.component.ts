import { Component } from '@angular/core';
import { VERSION } from '../../../../../../environments/version';
import { ENV } from '../../../../../../environments/environment';

@Component({
    selector: 'navigation-bar',
    moduleId: module.id.toString(),
    templateUrl: 'navigation-bar.component.html'
})
export class NavigationBar {
    constructor() { }

    public get version(): string {
        return VERSION.NUMBER + ' (' + ENV.TYPE + ')';
    }
}
