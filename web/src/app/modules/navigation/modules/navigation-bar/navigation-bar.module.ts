import { NgModule } from '@angular/core';
import { NavigationBar } from './components/navigation-bar.component';
import { CommonControlsModule } from '../../../actions/modules/common-controls/common-controls.module';
import { OperationMonitorModule } from '../../../notification/modules/operation-monitor/operation-monitor.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    CommonControlsModule,
    OperationMonitorModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    NavigationBar
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    NavigationBar
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class NavigationBarModule { }