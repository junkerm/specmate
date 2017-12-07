import { NgModule } from '@angular/core';
import { OperationMonitor } from './components/operation-monitor.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    OperationMonitor
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    OperationMonitor
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class OperationMonitorModule { }
