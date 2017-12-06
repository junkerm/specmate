"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
var core_1 = require("@angular/core");
var specmate_module_1 = require("./app/modules/specmate/specmate.module");
if (window.location.href.indexOf('localhost') >= 0 || window.location.href.indexOf('127.0.0.1') >= 0) {
    core_1.enableProdMode();
}
platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(specmate_module_1.SpecmateModule);
//# sourceMappingURL=main.js.map