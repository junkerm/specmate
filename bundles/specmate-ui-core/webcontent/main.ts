import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { SpecmateModule } from './app/specmate.module';

//enableProdMode();
platformBrowserDynamic().bootstrapModule(SpecmateModule);
