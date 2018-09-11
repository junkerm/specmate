import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { ExtendedHistoryView } from './components/extended-history-view.component';
import { HistoryView } from './components/history-view.component';
import { SimpleHistoryView } from './components/simple-history-view.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    NgbModule.forRoot(),
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    HistoryView,
    SimpleHistoryView,
    ExtendedHistoryView
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    SimpleHistoryView,
    ExtendedHistoryView
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class HistoryViewModule { }
