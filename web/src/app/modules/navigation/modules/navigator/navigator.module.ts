import { NgModule } from '@angular/core';
import { NavigationTargetDirective } from './directives/navigation-target.directive';
import { NavigatorService } from './services/navigator.service';

@NgModule({
  imports: [
    // MODULE IMPORTS
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    NavigationTargetDirective
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    NavigationTargetDirective
  ],
  providers: [
    // SERVICES
    NavigatorService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class NavigatorModule { }