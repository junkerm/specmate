import { NgModule } from '@angular/core';
import { PageNotFound } from './page-not-found.component';
import { Welcome } from './welcome.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        NgbModule
    ],
    exports: [],
    declarations: [
        Welcome,
        PageNotFound
    ],
    providers: [],
})
export class PagesModule { }
