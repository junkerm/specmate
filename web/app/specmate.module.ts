import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { AppRoutingModule }     from './app-routing.module';
import { HttpModule} from '@angular/http';
// Imports for loading & configuring the in-memory web api
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService }  from './in-memory-data.service';

import { SpecmateComponent } from './specmate.component';
import { SpecmateNavigationBar } from './components/specmate-navigation-bar/specmate-navigation-bar.component';



@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService)
  ],
  declarations: [
   SpecmateComponent,
   SpecmateNavigationBar
  ],
  providers: [  ],
  bootstrap: [ SpecmateComponent ]
})

export class SpecmateModule { }