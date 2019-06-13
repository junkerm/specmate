import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { SpecmateSharedModule } from '../../../../specmate/specmate.shared.module';
import { HistoryView } from './components/history-view.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    SpecmateSharedModule,
    NgbModule.forRoot()
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    HistoryView
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    HistoryView
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class HistoryViewModule { }
