import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { SpecmateModule } from './app/modules/specmate/specmate.module';

if (window.location.href.indexOf('localhost') < 0 && window.location.href.indexOf('127.0.0.1') < 0) {
    enableProdMode();
}

platformBrowserDynamic().bootstrapModule(SpecmateModule);
