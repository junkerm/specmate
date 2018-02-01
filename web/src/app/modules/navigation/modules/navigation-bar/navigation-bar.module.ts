import { NgModule } from '@angular/core';
import { NavigationBar } from './components/navigation-bar.component';
import { CommonControlsModule } from '../../../actions/modules/common-controls/common-controls.module';
import { OperationMonitorModule } from '../../../notification/modules/operation-monitor/operation-monitor.module';
import { I18NModule } from '../../../common/modules/i18n/i18n.module';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    // MODULE IMPORTS
    CommonControlsModule,
    OperationMonitorModule,
    I18NModule,
    TranslateModule
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
