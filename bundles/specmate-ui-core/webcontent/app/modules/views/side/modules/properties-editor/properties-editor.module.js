"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var navigator_module_1 = require("../../../../navigation/modules/navigator/navigator.module");
var core_1 = require("@angular/core");
var properties_editor_component_1 = require("./components/properties-editor.component");
var generic_form_module_1 = require("../../../../forms/modules/generic-form/generic-form.module");
var platform_browser_1 = require("@angular/platform-browser");
var tracing_links_component_1 = require("./components/tracing-links.component");
var tracing_link_component_1 = require("./components/tracing-link.component");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var forms_1 = require("@angular/forms");
var icons_module_1 = require("../../../../common/modules/icons/icons.module");
var PropertiesEditorModule = /** @class */ (function () {
    function PropertiesEditorModule() {
    }
    PropertiesEditorModule = __decorate([
        core_1.NgModule({
            imports: [
                // MODULE IMPORTS
                platform_browser_1.BrowserModule,
                generic_form_module_1.GenericFormModule,
                navigator_module_1.NavigatorModule,
                ng_bootstrap_1.NgbModule.forRoot(),
                forms_1.FormsModule,
                forms_1.ReactiveFormsModule,
                icons_module_1.IconsModule
            ],
            declarations: [
                // COMPONENTS IN THIS MODULE
                properties_editor_component_1.PropertiesEditor,
                tracing_links_component_1.TracingLinks,
                tracing_link_component_1.TracingLink
            ],
            exports: [
                // THE COMPONENTS VISIBLE TO THE OUTSIDE
                properties_editor_component_1.PropertiesEditor
            ],
            providers: [],
            bootstrap: []
        })
    ], PropertiesEditorModule);
    return PropertiesEditorModule;
}());
exports.PropertiesEditorModule = PropertiesEditorModule;
//# sourceMappingURL=properties-editor.module.js.map