(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? factory(exports, require('@angular/core'), require('angular2-modal')) :
        typeof define === 'function' && define.amd ? define(['exports', '@angular/core', 'angular2-modal'], factory) :
            (factory((global.angular2Modal = global.angular2Modal || {}, global.angular2Modal.plugins = global.angular2Modal.plugins || {}, global.angular2Modal.plugins.jsNative = global.angular2Modal.plugins.jsNative || {}), global.ng.core, global.angular2Modal));
}(this, (function (exports, _angular_core, angular2Modal) {
    'use strict';
    var __extends$2 = (undefined && undefined.__extends) || function (d, b) {
        for (var p in b)
            if (b.hasOwnProperty(p))
                d[p] = b[p];
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
    var DEFAULT_SETTERS = [
        'promptDefault'
    ];
    var JSNativeModalContext = (function (_super) {
        __extends$2(JSNativeModalContext, _super);
        function JSNativeModalContext() {
            return _super !== null && _super.apply(this, arguments) || this;
        }
        JSNativeModalContext.prototype.normalize = function () {
            if (!this.message)
                this.message = '';
            if (this.dialogType === undefined)
                this.dialogType = angular2Modal.DROP_IN_TYPE.alert;
        };
        return JSNativeModalContext;
    }(angular2Modal.ModalOpenContext));
    var JSNativeModalContextBuilder = (function (_super) {
        __extends$2(JSNativeModalContextBuilder, _super);
        function JSNativeModalContextBuilder(defaultValues, initialSetters, baseType) {
            if (defaultValues === void 0) {
                defaultValues = undefined;
            }
            if (initialSetters === void 0) {
                initialSetters = undefined;
            }
            if (baseType === void 0) {
                baseType = undefined;
            }
            return _super.call(this, defaultValues || {}, angular2Modal.arrayUnion(DEFAULT_SETTERS, initialSetters || []), baseType || JSNativeModalContext) || this;
        }
        return JSNativeModalContextBuilder;
    }(angular2Modal.ModalOpenContextBuilder));
    var __decorate$1 = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function")
            r = Reflect.decorate(decorators, target, key, desc);
        else
            for (var i = decorators.length - 1; i >= 0; i--)
                if (d = decorators[i])
                    r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    exports.JSNativeModalRenderer = (function () {
        function JSNativeModalRenderer() {
        }
        JSNativeModalRenderer.prototype.render = function (dialog, vcRef) {
            var result;
            switch (dialog.context.dialogType) {
                case angular2Modal.DROP_IN_TYPE.alert:
                    window.alert(dialog.context.message);
                    result = true;
                    break;
                case angular2Modal.DROP_IN_TYPE.prompt:
                    result = window.prompt(dialog.context.message, dialog.context.promptDefault);
                    break;
                case angular2Modal.DROP_IN_TYPE.confirm:
                    result = window.confirm(dialog.context.message);
                    break;
            }
            dialog.destroy = function () {
            };
            if (result === false) {
                dialog.dismiss();
            }
            else {
                dialog.close(result);
            }
            // we need to return ComponentRef<ModalOverlay> but a native dialog does'nt have that
            // so we resolve an empty promise, the user of this renderer should expect that.
            return {};
        };
        return JSNativeModalRenderer;
    }());
    exports.JSNativeModalRenderer = __decorate$1([
        _angular_core.Injectable()
    ], exports.JSNativeModalRenderer);
    var __extends$1 = (undefined && undefined.__extends) || function (d, b) {
        for (var p in b)
            if (b.hasOwnProperty(p))
                d[p] = b[p];
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
    var JSNativePresetBuilder = (function (_super) {
        __extends$1(JSNativePresetBuilder, _super);
        function JSNativePresetBuilder(modal, dialogType) {
            return _super.call(this, { modal: modal, dialogType: dialogType }) || this;
        }
        /**
         * Hook to alter config and return bindings.
         * @param config
         */
        JSNativePresetBuilder.prototype.$$beforeOpen = function (config) {
            return [];
        };
        /**
         * Open a modal window based on the configuration of this config instance.
         * @param viewContainer If set opens the modal inside the supplied viewContainer
         * @returns Promise<DialogRef>
         */
        JSNativePresetBuilder.prototype.open = function (viewContainer) {
            var context = this.toJSON();
            if (!(context.modal instanceof exports.Modal)) {
                return Promise.reject(new Error('Configuration Error: modal service not set.'));
            }
            var overlayConfig = {
                context: context,
                renderer: new exports.JSNativeModalRenderer(),
                viewContainer: viewContainer,
                bindings: typeof this.$$beforeOpen === 'function' && this.$$beforeOpen(context)
            };
            return context.modal.open(context.component, overlayConfig);
        };
        return JSNativePresetBuilder;
    }(JSNativeModalContextBuilder));
    var __extends = (undefined && undefined.__extends) || function (d, b) {
        for (var p in b)
            if (b.hasOwnProperty(p))
                d[p] = b[p];
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
    var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function")
            r = Reflect.decorate(decorators, target, key, desc);
        else
            for (var i = decorators.length - 1; i >= 0; i--)
                if (d = decorators[i])
                    r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (undefined && undefined.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function")
            return Reflect.metadata(k, v);
    };
    exports.Modal = (function (_super) {
        __extends(Modal$$1, _super);
        function Modal$$1(overlay) {
            return _super.call(this, overlay) || this;
        }
        Modal$$1.prototype.alert = function () {
            return new JSNativePresetBuilder(this, angular2Modal.DROP_IN_TYPE.alert);
        };
        Modal$$1.prototype.prompt = function () {
            return new JSNativePresetBuilder(this, angular2Modal.DROP_IN_TYPE.prompt);
        };
        Modal$$1.prototype.confirm = function () {
            return new JSNativePresetBuilder(this, angular2Modal.DROP_IN_TYPE.confirm);
        };
        Modal$$1.prototype.create = function (dialogRef, type, bindings) {
            return dialogRef;
        };
        return Modal$$1;
    }(angular2Modal.Modal));
    exports.Modal = __decorate([
        _angular_core.Injectable(),
        __metadata("design:paramtypes", [angular2Modal.Overlay])
    ], exports.Modal);
    var __decorate$2 = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function")
            r = Reflect.decorate(decorators, target, key, desc);
        else
            for (var i = decorators.length - 1; i >= 0; i--)
                if (d = decorators[i])
                    r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var providers = [
        { provide: angular2Modal.Modal, useClass: exports.Modal },
        { provide: exports.Modal, useClass: exports.Modal }
    ];
    exports.JSNativeModalModule = (function () {
        function JSNativeModalModule() {
        }
        JSNativeModalModule.getProviders = function () {
            return providers;
        };
        return JSNativeModalModule;
    }());
    exports.JSNativeModalModule = __decorate$2([
        _angular_core.NgModule({
            providers: providers
        })
    ], exports.JSNativeModalModule);
    exports.JSNativeModalContext = JSNativeModalContext;
    exports.JSNativeModalContextBuilder = JSNativeModalContextBuilder;
    exports.JSNativePresetBuilder = JSNativePresetBuilder;
    exports.providers = providers;
    Object.defineProperty(exports, '__esModule', { value: true });
})));
