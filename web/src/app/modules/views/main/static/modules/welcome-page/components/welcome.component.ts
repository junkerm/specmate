import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs/Observable';

@Component({
    moduleId: module.id.toString(),
    selector: 'welcome',
    templateUrl: 'welcome.component.html'
})
export class Welcome {

    constructor(private translate: TranslateService) { }

    public get str(): Observable<string> {
        return this.translate.get('welcome.text');
    }
}
