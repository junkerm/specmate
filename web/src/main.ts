import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { SpecmateModule } from './app/modules/specmate/specmate.module';
import { ENV } from './environments/environment';

if (ENV.TYPE === 'prod') {
    enableProdMode();
}

platformBrowserDynamic().bootstrapModule(SpecmateModule);
