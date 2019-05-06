import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { LogListModule } from '../../../views/side/modules/log-list/log-list.module';
import { SpecmateDataService } from './services/specmate-data.service';

@NgModule({
  imports: [
    // MODULE IMPORTS
    HttpClientModule,
    LogListModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES
    SpecmateDataService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class DataServiceModule { }
