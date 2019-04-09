import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { LogEntry } from './components/log-entry.component';
import { LogList } from './components/log-list.component';
import { LoggingService } from './services/logging.service';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    LogList,
    LogEntry
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    LogList
  ],
  providers: [
    // SERVICES
    LoggingService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class LogListModule { }
