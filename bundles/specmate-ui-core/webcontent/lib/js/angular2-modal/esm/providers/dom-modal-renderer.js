var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { Injectable, ReflectiveInjector } from '@angular/core';
import { createComponent } from '../framework/createComponent';
import { DialogRef } from '../models/dialog-ref';
import { ModalOverlay } from '../overlay/index';
var DOMOverlayRenderer = (function () {
    function DOMOverlayRenderer() {
    }
    DOMOverlayRenderer.prototype.render = function (dialog, vcRef, injector) {
        var bindings = ReflectiveInjector.resolve([
            { provide: DialogRef, useValue: dialog }
        ]);
        var cmpRef = createComponent({
            component: ModalOverlay,
            vcRef: vcRef,
            injector: injector,
            bindings: bindings
        });
        if (dialog.inElement) {
            vcRef.element.nativeElement.appendChild(cmpRef.location.nativeElement);
        }
        else {
            document.body.appendChild(cmpRef.location.nativeElement);
        }
        return cmpRef;
    };
    return DOMOverlayRenderer;
}());
DOMOverlayRenderer = __decorate([
    Injectable()
], DOMOverlayRenderer);
export { DOMOverlayRenderer };
//# sourceMappingURL=dom-modal-renderer.js.map