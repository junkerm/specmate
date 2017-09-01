(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory(require("@angular/core"));
	else if(typeof define === 'function' && define.amd)
		define(["@angular/core"], factory);
	else if(typeof exports === 'object')
		exports["ng2-dnd"] = factory(require("@angular/core"));
	else
		root["ng2-dnd"] = factory(root["@angular/core"]);
})(this, function(__WEBPACK_EXTERNAL_MODULE_0__) {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 8);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

module.exports = __WEBPACK_EXTERNAL_MODULE_0__;

/***/ }),
/* 1 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DataTransferEffect; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return DragImage; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "c", function() { return DragDropConfig; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__dnd_utils__ = __webpack_require__(7);
// Copyright (C) 2016 Sergey Akopkokhyants
// This project is licensed under the terms of the MIT license.
// https://github.com/akserg/ng2-dnd

var DataTransferEffect = (function () {
    function DataTransferEffect(name) {
        this.name = name;
    }
    return DataTransferEffect;
}());

DataTransferEffect.COPY = new DataTransferEffect('copy');
DataTransferEffect.LINK = new DataTransferEffect('link');
DataTransferEffect.MOVE = new DataTransferEffect('move');
DataTransferEffect.NONE = new DataTransferEffect('none');
var DragImage = (function () {
    function DragImage(imageElement, x_offset, y_offset) {
        if (x_offset === void 0) { x_offset = 0; }
        if (y_offset === void 0) { y_offset = 0; }
        this.imageElement = imageElement;
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        if (__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__dnd_utils__["b" /* isString */])(this.imageElement)) {
            // Create real image from string source
            var imgScr = this.imageElement;
            this.imageElement = new HTMLImageElement();
            this.imageElement.src = imgScr;
        }
    }
    return DragImage;
}());

var DragDropConfig = (function () {
    function DragDropConfig() {
        this.onDragStartClass = "dnd-drag-start";
        this.onDragEnterClass = "dnd-drag-enter";
        this.onDragOverClass = "dnd-drag-over";
        this.onSortableDragClass = "dnd-sortable-drag";
        this.dragEffect = DataTransferEffect.MOVE;
        this.dropEffect = DataTransferEffect.MOVE;
        this.dragCursor = "move";
        this.defaultCursor = "pointer";
    }
    return DragDropConfig;
}());



/***/ }),
/* 2 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DragDropData; });
/* harmony export (immutable) */ __webpack_exports__["b"] = dragDropServiceFactory;
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "c", function() { return DragDropService; });
/* harmony export (immutable) */ __webpack_exports__["d"] = dragDropSortableServiceFactory;
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "e", function() { return DragDropSortableService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__angular_core__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__dnd_config__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__dnd_utils__ = __webpack_require__(7);
// Copyright (C) 2016 Sergey Akopkokhyants
// This project is licensed under the terms of the MIT license.
// https://github.com/akserg/ng2-dnd
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var DragDropData = (function () {
    function DragDropData() {
    }
    return DragDropData;
}());

function dragDropServiceFactory() {
    return new DragDropService();
}
var DragDropService = (function () {
    function DragDropService() {
        this.allowedDropZones = [];
    }
    return DragDropService;
}());
DragDropService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])()
], DragDropService);

function dragDropSortableServiceFactory(config) {
    return new DragDropSortableService(config);
}
var DragDropSortableService = (function () {
    function DragDropSortableService(_config) {
        this._config = _config;
    }
    Object.defineProperty(DragDropSortableService.prototype, "elem", {
        get: function () {
            return this._elem;
        },
        enumerable: true,
        configurable: true
    });
    DragDropSortableService.prototype.markSortable = function (elem) {
        if (__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_2__dnd_utils__["a" /* isPresent */])(this._elem)) {
            this._elem.classList.remove(this._config.onSortableDragClass);
        }
        if (__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_2__dnd_utils__["a" /* isPresent */])(elem)) {
            this._elem = elem;
            this._elem.classList.add(this._config.onSortableDragClass);
        }
    };
    return DragDropSortableService;
}());
DragDropSortableService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__dnd_config__["c" /* DragDropConfig */]])
], DragDropSortableService);



/***/ }),
/* 3 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AbstractComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return AbstractHandleComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__angular_core__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__dnd_config__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__dnd_service__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__dnd_utils__ = __webpack_require__(7);
// Copyright (C) 2016 Sergey Akopkokhyants
// This project is licensed under the terms of the MIT license.
// https://github.com/akserg/ng2-dnd
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var AbstractComponent = (function () {
    function AbstractComponent(elemRef, _dragDropService, _config, _cdr) {
        var _this = this;
        this._dragDropService = _dragDropService;
        this._config = _config;
        this._cdr = _cdr;
        /**
         * Whether the object is draggable. Default is true.
         */
        this._dragEnabled = false;
        /**
         * Allows drop on this element
         */
        this.dropEnabled = false;
        this.dropZones = [];
        this.cloneItem = false;
        // Assign default cursor unless overridden
        this._defaultCursor = _config.defaultCursor;
        this._elem = elemRef.nativeElement;
        this._elem.style.cursor = this._defaultCursor; // set default cursor on our element
        //
        // DROP events
        //
        this._elem.ondragenter = function (event) {
            _this._onDragEnter(event);
        };
        this._elem.ondragover = function (event) {
            _this._onDragOver(event);
            //
            if (event.dataTransfer != null) {
                event.dataTransfer.dropEffect = _this._config.dropEffect.name;
            }
            return false;
        };
        this._elem.ondragleave = function (event) {
            _this._onDragLeave(event);
        };
        this._elem.ondrop = function (event) {
            _this._onDrop(event);
        };
        //
        // Drag events
        //
        this._elem.onmousedown = function (event) {
            _this._target = event.target;
        };
        this._elem.ondragstart = function (event) {
            if (_this._dragHandle) {
                if (!_this._dragHandle.contains(_this._target)) {
                    event.preventDefault();
                    return;
                }
            }
            _this._onDragStart(event);
            //
            if (event.dataTransfer != null) {
                event.dataTransfer.setData('text', '');
                // Change drag effect
                event.dataTransfer.effectAllowed = _this.effectAllowed || _this._config.dragEffect.name;
                // Change drag image
                if (__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_3__dnd_utils__["a" /* isPresent */])(_this.dragImage)) {
                    if (__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_3__dnd_utils__["b" /* isString */])(_this.dragImage)) {
                        event.dataTransfer.setDragImage(__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_3__dnd_utils__["c" /* createImage */])(_this.dragImage));
                    }
                    else if (__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_3__dnd_utils__["d" /* isFunction */])(_this.dragImage)) {
                        event.dataTransfer.setDragImage(__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_3__dnd_utils__["e" /* callFun */])(_this.dragImage));
                    }
                    else {
                        var img = _this.dragImage;
                        event.dataTransfer.setDragImage(img.imageElement, img.x_offset, img.y_offset);
                    }
                }
                else if (__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_3__dnd_utils__["a" /* isPresent */])(_this._config.dragImage)) {
                    var dragImage = _this._config.dragImage;
                    event.dataTransfer.setDragImage(dragImage.imageElement, dragImage.x_offset, dragImage.y_offset);
                }
                else if (_this.cloneItem) {
                    _this._dragHelper = _this._elem.cloneNode(true);
                    _this._dragHelper.classList.add('dnd-drag-item');
                    _this._dragHelper.style.position = "absolute";
                    _this._dragHelper.style.top = "0px";
                    _this._dragHelper.style.left = "-1000px";
                    _this._elem.parentElement.appendChild(_this._dragHelper);
                    event.dataTransfer.setDragImage(_this._dragHelper, event.offsetX, event.offsetY);
                }
                // Change drag cursor
                var cursorelem = (_this._dragHandle) ? _this._dragHandle : _this._elem;
                if (_this._dragEnabled) {
                    cursorelem.style.cursor = _this.effectCursor ? _this.effectCursor : _this._config.dragCursor;
                }
                else {
                    cursorelem.style.cursor = _this._defaultCursor;
                }
            }
        };
        this._elem.ondragend = function (event) {
            if (_this._elem.parentElement && _this._dragHelper) {
                _this._elem.parentElement.removeChild(_this._dragHelper);
            }
            // console.log('ondragend', event.target);
            _this._onDragEnd(event);
            // Restore style of dragged element
            var cursorelem = (_this._dragHandle) ? _this._dragHandle : _this._elem;
            cursorelem.style.cursor = _this._defaultCursor;
        };
    }
    Object.defineProperty(AbstractComponent.prototype, "dragEnabled", {
        get: function () {
            return this._dragEnabled;
        },
        set: function (enabled) {
            this._dragEnabled = !!enabled;
            this._elem.draggable = this._dragEnabled;
        },
        enumerable: true,
        configurable: true
    });
    AbstractComponent.prototype.setDragHandle = function (elem) {
        this._dragHandle = elem;
    };
    /******* Change detection ******/
    AbstractComponent.prototype.detectChanges = function () {
        var _this = this;
        // Programmatically run change detection to fix issue in Safari
        setTimeout(function () {
            _this._cdr.detectChanges();
        }, 250);
    };
    //****** Droppable *******//
    AbstractComponent.prototype._onDragEnter = function (event) {
        // console.log('ondragenter._isDropAllowed', this._isDropAllowed);
        if (this._isDropAllowed(event)) {
            // event.preventDefault();
            this._onDragEnterCallback(event);
        }
    };
    AbstractComponent.prototype._onDragOver = function (event) {
        // // console.log('ondragover._isDropAllowed', this._isDropAllowed);
        if (this._isDropAllowed(event)) {
            // The element is over the same source element - do nothing
            if (event.preventDefault) {
                // Necessary. Allows us to drop.
                event.preventDefault();
            }
            this._onDragOverCallback(event);
        }
    };
    AbstractComponent.prototype._onDragLeave = function (event) {
        // console.log('ondragleave._isDropAllowed', this._isDropAllowed);
        if (this._isDropAllowed(event)) {
            // event.preventDefault();
            this._onDragLeaveCallback(event);
        }
    };
    AbstractComponent.prototype._onDrop = function (event) {
        // Necessary. Allows us to drop.
        this._preventAndStop(event);
        // console.log('ondrop._isDropAllowed', this._isDropAllowed);
        if (this._isDropAllowed(event)) {
            this._onDropCallback(event);
            this.detectChanges();
        }
    };
    AbstractComponent.prototype._isDropAllowed = function (event) {
        if ((this._dragDropService.isDragged || (event.dataTransfer && event.dataTransfer.files)) && this.dropEnabled) {
            // First, if `allowDrop` is set, call it to determine whether the
            // dragged element can be dropped here.
            if (this.allowDrop) {
                return this.allowDrop(this._dragDropService.dragData);
            }
            // Otherwise, use dropZones if they are set.
            if (this.dropZones.length === 0 && this._dragDropService.allowedDropZones.length === 0) {
                return true;
            }
            for (var i = 0; i < this._dragDropService.allowedDropZones.length; i++) {
                var dragZone = this._dragDropService.allowedDropZones[i];
                if (this.dropZones.indexOf(dragZone) !== -1) {
                    return true;
                }
            }
        }
        return false;
    };
    AbstractComponent.prototype._preventAndStop = function (event) {
        if (event.preventDefault) {
            event.preventDefault();
        }
        if (event.stopPropagation) {
            event.stopPropagation();
        }
    };
    //*********** Draggable **********//
    AbstractComponent.prototype._onDragStart = function (event) {
        //console.log('ondragstart.dragEnabled', this._dragEnabled);
        if (this._dragEnabled) {
            this._dragDropService.allowedDropZones = this.dropZones;
            // console.log('ondragstart.allowedDropZones', this._dragDropService.allowedDropZones);
            this._onDragStartCallback(event);
        }
    };
    AbstractComponent.prototype._onDragEnd = function (event) {
        this._dragDropService.allowedDropZones = [];
        // console.log('ondragend.allowedDropZones', this._dragDropService.allowedDropZones);
        this._onDragEndCallback(event);
    };
    //**** Drop Callbacks ****//
    AbstractComponent.prototype._onDragEnterCallback = function (event) { };
    AbstractComponent.prototype._onDragOverCallback = function (event) { };
    AbstractComponent.prototype._onDragLeaveCallback = function (event) { };
    AbstractComponent.prototype._onDropCallback = function (event) { };
    //**** Drag Callbacks ****//
    AbstractComponent.prototype._onDragStartCallback = function (event) { };
    AbstractComponent.prototype._onDragEndCallback = function (event) { };
    return AbstractComponent;
}());
AbstractComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["ElementRef"], __WEBPACK_IMPORTED_MODULE_2__dnd_service__["c" /* DragDropService */], __WEBPACK_IMPORTED_MODULE_1__dnd_config__["c" /* DragDropConfig */],
        __WEBPACK_IMPORTED_MODULE_0__angular_core__["ChangeDetectorRef"]])
], AbstractComponent);

var AbstractHandleComponent = (function () {
    function AbstractHandleComponent(elemRef, _dragDropService, _config, _Component, _cdr) {
        this._dragDropService = _dragDropService;
        this._config = _config;
        this._Component = _Component;
        this._cdr = _cdr;
        this._elem = elemRef.nativeElement;
        this._Component.setDragHandle(this._elem);
    }
    return AbstractHandleComponent;
}());



/***/ }),
/* 4 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DraggableComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return DraggableHandleComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__angular_core__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__abstract_component__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__dnd_config__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__dnd_service__ = __webpack_require__(2);
// Copyright (C) 2016 Sergey Akopkokhyants
// This project is licensed under the terms of the MIT license.
// https://github.com/akserg/ng2-dnd
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var DraggableComponent = (function (_super) {
    __extends(DraggableComponent, _super);
    function DraggableComponent(elemRef, dragDropService, config, cdr) {
        var _this = _super.call(this, elemRef, dragDropService, config, cdr) || this;
        /**
         * Callback function called when the drag actions happened.
         */
        _this.onDragStart = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDragEnd = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        /**
         * Callback function called when the drag action ends with a valid drop action.
         * It is activated after the on-drop-success callback
         */
        _this.onDragSuccessCallback = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this._defaultCursor = _this._elem.style.cursor;
        _this.dragEnabled = true;
        return _this;
    }
    Object.defineProperty(DraggableComponent.prototype, "draggable", {
        set: function (value) {
            this.dragEnabled = !!value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DraggableComponent.prototype, "dropzones", {
        set: function (value) {
            this.dropZones = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DraggableComponent.prototype, "effectallowed", {
        /**
         * Drag allowed effect
         */
        set: function (value) {
            this.effectAllowed = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DraggableComponent.prototype, "effectcursor", {
        /**
         * Drag effect cursor
         */
        set: function (value) {
            this.effectCursor = value;
        },
        enumerable: true,
        configurable: true
    });
    DraggableComponent.prototype._onDragStartCallback = function (event) {
        this._dragDropService.isDragged = true;
        this._dragDropService.dragData = this.dragData;
        this._dragDropService.onDragSuccessCallback = this.onDragSuccessCallback;
        this._elem.classList.add(this._config.onDragStartClass);
        //
        this.onDragStart.emit({ dragData: this.dragData, mouseEvent: event });
    };
    DraggableComponent.prototype._onDragEndCallback = function (event) {
        this._dragDropService.isDragged = false;
        this._dragDropService.dragData = null;
        this._dragDropService.onDragSuccessCallback = null;
        this._elem.classList.remove(this._config.onDragStartClass);
        //
        this.onDragEnd.emit({ dragData: this.dragData, mouseEvent: event });
    };
    return DraggableComponent;
}(__WEBPACK_IMPORTED_MODULE_1__abstract_component__["a" /* AbstractComponent */]));
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dragEnabled"),
    __metadata("design:type", Boolean),
    __metadata("design:paramtypes", [Boolean])
], DraggableComponent.prototype, "draggable", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], DraggableComponent.prototype, "onDragStart", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], DraggableComponent.prototype, "onDragEnd", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Object)
], DraggableComponent.prototype, "dragData", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])("onDragSuccess"),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], DraggableComponent.prototype, "onDragSuccessCallback", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dropZones"),
    __metadata("design:type", Array),
    __metadata("design:paramtypes", [Array])
], DraggableComponent.prototype, "dropzones", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("effectAllowed"),
    __metadata("design:type", String),
    __metadata("design:paramtypes", [String])
], DraggableComponent.prototype, "effectallowed", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("effectCursor"),
    __metadata("design:type", String),
    __metadata("design:paramtypes", [String])
], DraggableComponent.prototype, "effectcursor", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Object)
], DraggableComponent.prototype, "dragImage", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Boolean)
], DraggableComponent.prototype, "cloneItem", void 0);
DraggableComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Directive"])({ selector: '[dnd-draggable]' }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["ElementRef"], __WEBPACK_IMPORTED_MODULE_3__dnd_service__["c" /* DragDropService */], __WEBPACK_IMPORTED_MODULE_2__dnd_config__["c" /* DragDropConfig */],
        __WEBPACK_IMPORTED_MODULE_0__angular_core__["ChangeDetectorRef"]])
], DraggableComponent);

var DraggableHandleComponent = (function (_super) {
    __extends(DraggableHandleComponent, _super);
    function DraggableHandleComponent(elemRef, dragDropService, config, _Component, cdr) {
        return _super.call(this, elemRef, dragDropService, config, _Component, cdr) || this;
    }
    return DraggableHandleComponent;
}(__WEBPACK_IMPORTED_MODULE_1__abstract_component__["b" /* AbstractHandleComponent */]));
DraggableHandleComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Directive"])({ selector: '[dnd-draggable-handle]' }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["ElementRef"], __WEBPACK_IMPORTED_MODULE_3__dnd_service__["c" /* DragDropService */], __WEBPACK_IMPORTED_MODULE_2__dnd_config__["c" /* DragDropConfig */], DraggableComponent,
        __WEBPACK_IMPORTED_MODULE_0__angular_core__["ChangeDetectorRef"]])
], DraggableHandleComponent);



/***/ }),
/* 5 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DroppableComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__angular_core__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__abstract_component__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__dnd_config__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__dnd_service__ = __webpack_require__(2);
// Copyright (C) 2016 Sergey Akopkokhyants
// This project is licensed under the terms of the MIT license.
// https://github.com/akserg/ng2-dnd
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var DroppableComponent = (function (_super) {
    __extends(DroppableComponent, _super);
    function DroppableComponent(elemRef, dragDropService, config, cdr) {
        var _this = _super.call(this, elemRef, dragDropService, config, cdr) || this;
        /**
         * Callback function called when the drop action completes correctly.
         * It is activated before the on-drag-success callback.
         */
        _this.onDropSuccess = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDragEnter = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDragOver = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDragLeave = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.dropEnabled = true;
        return _this;
    }
    Object.defineProperty(DroppableComponent.prototype, "droppable", {
        set: function (value) {
            this.dropEnabled = !!value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DroppableComponent.prototype, "allowdrop", {
        set: function (value) {
            this.allowDrop = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DroppableComponent.prototype, "dropzones", {
        set: function (value) {
            this.dropZones = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DroppableComponent.prototype, "effectallowed", {
        /**
         * Drag allowed effect
         */
        set: function (value) {
            this.effectAllowed = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DroppableComponent.prototype, "effectcursor", {
        /**
         * Drag effect cursor
         */
        set: function (value) {
            this.effectCursor = value;
        },
        enumerable: true,
        configurable: true
    });
    DroppableComponent.prototype._onDragEnterCallback = function (event) {
        if (this._dragDropService.isDragged) {
            this._elem.classList.add(this._config.onDragEnterClass);
            this.onDragEnter.emit({ dragData: this._dragDropService.dragData, mouseEvent: event });
        }
    };
    DroppableComponent.prototype._onDragOverCallback = function (event) {
        if (this._dragDropService.isDragged) {
            this._elem.classList.add(this._config.onDragOverClass);
            this.onDragOver.emit({ dragData: this._dragDropService.dragData, mouseEvent: event });
        }
    };
    ;
    DroppableComponent.prototype._onDragLeaveCallback = function (event) {
        if (this._dragDropService.isDragged) {
            this._elem.classList.remove(this._config.onDragOverClass);
            this._elem.classList.remove(this._config.onDragEnterClass);
            this.onDragLeave.emit({ dragData: this._dragDropService.dragData, mouseEvent: event });
        }
    };
    ;
    DroppableComponent.prototype._onDropCallback = function (event) {
        var dataTransfer = event.dataTransfer;
        if (this._dragDropService.isDragged || (dataTransfer && dataTransfer.files)) {
            this.onDropSuccess.emit({ dragData: this._dragDropService.dragData, mouseEvent: event });
            if (this._dragDropService.onDragSuccessCallback) {
                this._dragDropService.onDragSuccessCallback.emit({ dragData: this._dragDropService.dragData, mouseEvent: event });
            }
            this._elem.classList.remove(this._config.onDragOverClass);
            this._elem.classList.remove(this._config.onDragEnterClass);
        }
    };
    return DroppableComponent;
}(__WEBPACK_IMPORTED_MODULE_1__abstract_component__["a" /* AbstractComponent */]));
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dropEnabled"),
    __metadata("design:type", Boolean),
    __metadata("design:paramtypes", [Boolean])
], DroppableComponent.prototype, "droppable", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], DroppableComponent.prototype, "onDropSuccess", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], DroppableComponent.prototype, "onDragEnter", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], DroppableComponent.prototype, "onDragOver", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])(),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], DroppableComponent.prototype, "onDragLeave", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("allowDrop"),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Function])
], DroppableComponent.prototype, "allowdrop", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dropZones"),
    __metadata("design:type", Array),
    __metadata("design:paramtypes", [Array])
], DroppableComponent.prototype, "dropzones", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("effectAllowed"),
    __metadata("design:type", String),
    __metadata("design:paramtypes", [String])
], DroppableComponent.prototype, "effectallowed", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("effectCursor"),
    __metadata("design:type", String),
    __metadata("design:paramtypes", [String])
], DroppableComponent.prototype, "effectcursor", null);
DroppableComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Directive"])({ selector: '[dnd-droppable]' }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["ElementRef"], __WEBPACK_IMPORTED_MODULE_3__dnd_service__["c" /* DragDropService */], __WEBPACK_IMPORTED_MODULE_2__dnd_config__["c" /* DragDropConfig */],
        __WEBPACK_IMPORTED_MODULE_0__angular_core__["ChangeDetectorRef"]])
], DroppableComponent);



/***/ }),
/* 6 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SortableContainer; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return SortableComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "c", function() { return SortableHandleComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__angular_core__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__abstract_component__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__dnd_config__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__dnd_service__ = __webpack_require__(2);
// Copyright (C) 2016 Sergey Akopkokhyants
// This project is licensed under the terms of the MIT license.
// https://github.com/akserg/ng2-dnd
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var SortableContainer = (function (_super) {
    __extends(SortableContainer, _super);
    function SortableContainer(elemRef, dragDropService, config, cdr, _sortableDataService) {
        var _this = _super.call(this, elemRef, dragDropService, config, cdr) || this;
        _this._sortableDataService = _sortableDataService;
        _this._sortableData = [];
        _this.dragEnabled = false;
        return _this;
    }
    Object.defineProperty(SortableContainer.prototype, "draggable", {
        set: function (value) {
            this.dragEnabled = !!value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SortableContainer.prototype, "sortableData", {
        get: function () {
            return this._sortableData;
        },
        set: function (sortableData) {
            this._sortableData = sortableData;
            //
            this.dropEnabled = !!this._sortableData;
            // console.log("collection is changed, drop enabled: " + this.dropEnabled);
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SortableContainer.prototype, "dropzones", {
        set: function (value) {
            this.dropZones = value;
        },
        enumerable: true,
        configurable: true
    });
    SortableContainer.prototype._onDragEnterCallback = function (event) {
        if (this._sortableDataService.isDragged) {
            var item = this._sortableDataService.sortableContainer._sortableData[this._sortableDataService.index];
            // Check does element exist in sortableData of this Container
            if (this._sortableData.indexOf(item) === -1) {
                // Let's add it
                // console.log('Container._onDragEnterCallback. drag node [' + this._sortableDataService.index.toString() + '] over parent node');
                // Remove item from previouse list
                this._sortableDataService.sortableContainer._sortableData.splice(this._sortableDataService.index, 1);
                if (this._sortableDataService.sortableContainer._sortableData.length === 0) {
                    this._sortableDataService.sortableContainer.dropEnabled = true;
                }
                // Add item to new list
                this._sortableData.unshift(item);
                this._sortableDataService.sortableContainer = this;
                this._sortableDataService.index = 0;
            }
            // Refresh changes in properties of container component
            this.detectChanges();
        }
    };
    return SortableContainer;
}(__WEBPACK_IMPORTED_MODULE_1__abstract_component__["a" /* AbstractComponent */]));
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dragEnabled"),
    __metadata("design:type", Boolean),
    __metadata("design:paramtypes", [Boolean])
], SortableContainer.prototype, "draggable", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Array),
    __metadata("design:paramtypes", [Array])
], SortableContainer.prototype, "sortableData", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dropZones"),
    __metadata("design:type", Array),
    __metadata("design:paramtypes", [Array])
], SortableContainer.prototype, "dropzones", null);
SortableContainer = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Directive"])({ selector: '[dnd-sortable-container]' }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["ElementRef"], __WEBPACK_IMPORTED_MODULE_3__dnd_service__["c" /* DragDropService */], __WEBPACK_IMPORTED_MODULE_2__dnd_config__["c" /* DragDropConfig */], __WEBPACK_IMPORTED_MODULE_0__angular_core__["ChangeDetectorRef"],
        __WEBPACK_IMPORTED_MODULE_3__dnd_service__["e" /* DragDropSortableService */]])
], SortableContainer);

var SortableComponent = (function (_super) {
    __extends(SortableComponent, _super);
    function SortableComponent(elemRef, dragDropService, config, _sortableContainer, _sortableDataService, cdr) {
        var _this = _super.call(this, elemRef, dragDropService, config, cdr) || this;
        _this._sortableContainer = _sortableContainer;
        _this._sortableDataService = _sortableDataService;
        /**
         * Callback function called when the drag action ends with a valid drop action.
         * It is activated after the on-drop-success callback
         */
        _this.onDragSuccessCallback = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDragStartCallback = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDragOverCallback = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDragEndCallback = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.onDropSuccessCallback = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"]();
        _this.dropZones = _this._sortableContainer.dropZones;
        _this.dragEnabled = true;
        _this.dropEnabled = true;
        return _this;
    }
    Object.defineProperty(SortableComponent.prototype, "draggable", {
        set: function (value) {
            this.dragEnabled = !!value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SortableComponent.prototype, "droppable", {
        set: function (value) {
            this.dropEnabled = !!value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SortableComponent.prototype, "effectallowed", {
        /**
         * Drag allowed effect
         */
        set: function (value) {
            this.effectAllowed = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(SortableComponent.prototype, "effectcursor", {
        /**
         * Drag effect cursor
         */
        set: function (value) {
            this.effectCursor = value;
        },
        enumerable: true,
        configurable: true
    });
    SortableComponent.prototype._onDragStartCallback = function (event) {
        // console.log('_onDragStartCallback. dragging elem with index ' + this.index);
        this._sortableDataService.isDragged = true;
        this._sortableDataService.sortableContainer = this._sortableContainer;
        this._sortableDataService.index = this.index;
        this._sortableDataService.markSortable(this._elem);
        // Add dragData
        this._dragDropService.isDragged = true;
        this._dragDropService.dragData = this.dragData;
        this._dragDropService.onDragSuccessCallback = this.onDragSuccessCallback;
        //
        this.onDragStartCallback.emit(this._dragDropService.dragData);
    };
    SortableComponent.prototype._onDragOverCallback = function (event) {
        if (this._sortableDataService.isDragged && this._elem !== this._sortableDataService.elem) {
            // console.log('_onDragOverCallback. dragging elem with index ' + this.index);
            this._sortableDataService.sortableContainer = this._sortableContainer;
            this._sortableDataService.index = this.index;
            this._sortableDataService.markSortable(this._elem);
            this.onDragOverCallback.emit(this._dragDropService.dragData);
        }
    };
    SortableComponent.prototype._onDragEndCallback = function (event) {
        // console.log('_onDragEndCallback. end dragging elem with index ' + this.index);
        this._sortableDataService.isDragged = false;
        this._sortableDataService.sortableContainer = null;
        this._sortableDataService.index = null;
        this._sortableDataService.markSortable(null);
        // Add dragGata
        this._dragDropService.isDragged = false;
        this._dragDropService.dragData = null;
        this._dragDropService.onDragSuccessCallback = null;
        //
        this.onDragEndCallback.emit(this._dragDropService.dragData);
    };
    SortableComponent.prototype._onDragEnterCallback = function (event) {
        if (this._sortableDataService.isDragged) {
            this._sortableDataService.markSortable(this._elem);
            if ((this.index !== this._sortableDataService.index) ||
                (this._sortableDataService.sortableContainer.sortableData !== this._sortableContainer.sortableData)) {
                // console.log('Component._onDragEnterCallback. drag node [' + this.index + '] over node [' + this._sortableDataService.index + ']');
                // Get item
                var item = this._sortableDataService.sortableContainer.sortableData[this._sortableDataService.index];
                // Remove item from previouse list
                this._sortableDataService.sortableContainer.sortableData.splice(this._sortableDataService.index, 1);
                if (this._sortableDataService.sortableContainer.sortableData.length === 0) {
                    this._sortableDataService.sortableContainer.dropEnabled = true;
                }
                // Add item to new list
                this._sortableContainer.sortableData.splice(this.index, 0, item);
                if (this._sortableContainer.dropEnabled) {
                    this._sortableContainer.dropEnabled = false;
                }
                this._sortableDataService.sortableContainer = this._sortableContainer;
                this._sortableDataService.index = this.index;
            }
        }
    };
    SortableComponent.prototype._onDropCallback = function (event) {
        if (this._sortableDataService.isDragged) {
            // console.log('onDropCallback.onDropSuccessCallback.dragData', this._dragDropService.dragData);
            this.onDropSuccessCallback.emit(this._dragDropService.dragData);
            if (this._dragDropService.onDragSuccessCallback) {
                // console.log('onDropCallback.onDragSuccessCallback.dragData', this._dragDropService.dragData);
                this._dragDropService.onDragSuccessCallback.emit(this._dragDropService.dragData);
            }
            // Refresh changes in properties of container component
            this._sortableContainer.detectChanges();
        }
    };
    return SortableComponent;
}(__WEBPACK_IMPORTED_MODULE_1__abstract_component__["a" /* AbstractComponent */]));
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])('sortableIndex'),
    __metadata("design:type", Number)
], SortableComponent.prototype, "index", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dragEnabled"),
    __metadata("design:type", Boolean),
    __metadata("design:paramtypes", [Boolean])
], SortableComponent.prototype, "draggable", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("dropEnabled"),
    __metadata("design:type", Boolean),
    __metadata("design:paramtypes", [Boolean])
], SortableComponent.prototype, "droppable", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])(),
    __metadata("design:type", Object)
], SortableComponent.prototype, "dragData", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("effectAllowed"),
    __metadata("design:type", String),
    __metadata("design:paramtypes", [String])
], SortableComponent.prototype, "effectallowed", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Input"])("effectCursor"),
    __metadata("design:type", String),
    __metadata("design:paramtypes", [String])
], SortableComponent.prototype, "effectcursor", null);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])("onDragSuccess"),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], SortableComponent.prototype, "onDragSuccessCallback", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])("onDragStart"),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], SortableComponent.prototype, "onDragStartCallback", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])("onDragOver"),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], SortableComponent.prototype, "onDragOverCallback", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])("onDragEnd"),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], SortableComponent.prototype, "onDragEndCallback", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Output"])("onDropSuccess"),
    __metadata("design:type", __WEBPACK_IMPORTED_MODULE_0__angular_core__["EventEmitter"])
], SortableComponent.prototype, "onDropSuccessCallback", void 0);
SortableComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Directive"])({ selector: '[dnd-sortable]' }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["ElementRef"], __WEBPACK_IMPORTED_MODULE_3__dnd_service__["c" /* DragDropService */], __WEBPACK_IMPORTED_MODULE_2__dnd_config__["c" /* DragDropConfig */],
        SortableContainer,
        __WEBPACK_IMPORTED_MODULE_3__dnd_service__["e" /* DragDropSortableService */],
        __WEBPACK_IMPORTED_MODULE_0__angular_core__["ChangeDetectorRef"]])
], SortableComponent);

var SortableHandleComponent = (function (_super) {
    __extends(SortableHandleComponent, _super);
    function SortableHandleComponent(elemRef, dragDropService, config, _Component, cdr) {
        return _super.call(this, elemRef, dragDropService, config, _Component, cdr) || this;
    }
    return SortableHandleComponent;
}(__WEBPACK_IMPORTED_MODULE_1__abstract_component__["b" /* AbstractHandleComponent */]));
SortableHandleComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Directive"])({ selector: '[dnd-sortable-handle]' }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_core__["ElementRef"], __WEBPACK_IMPORTED_MODULE_3__dnd_service__["c" /* DragDropService */], __WEBPACK_IMPORTED_MODULE_2__dnd_config__["c" /* DragDropConfig */], SortableComponent,
        __WEBPACK_IMPORTED_MODULE_0__angular_core__["ChangeDetectorRef"]])
], SortableHandleComponent);



/***/ }),
/* 7 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (immutable) */ __webpack_exports__["b"] = isString;
/* harmony export (immutable) */ __webpack_exports__["a"] = isPresent;
/* harmony export (immutable) */ __webpack_exports__["d"] = isFunction;
/* harmony export (immutable) */ __webpack_exports__["c"] = createImage;
/* harmony export (immutable) */ __webpack_exports__["e"] = callFun;
/**
 * Check and return true if an object is type of string
 */
/**
 * Check and return true if an object is type of string
 */ function isString(obj) {
    return typeof obj === "string";
}
/**
 * Check and return true if an object not undefined or null
 */
function isPresent(obj) {
    return obj !== undefined && obj !== null;
}
/**
 * Check and return true if an object is type of Function
 */
function isFunction(obj) {
    return typeof obj === "function";
}
/**
 * Create Image element with specified url string
 */
function createImage(src) {
    var img = new HTMLImageElement();
    img.src = src;
    return img;
}
/**
 * Call the function
 */
function callFun(fun) {
    return fun();
}


/***/ }),
/* 8 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "providers", function() { return providers; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DndModule", function() { return DndModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__angular_core__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__src_dnd_config__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__src_draggable_component__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__src_droppable_component__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__src_abstract_component__ = __webpack_require__(3);
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "AbstractComponent", function() { return __WEBPACK_IMPORTED_MODULE_6__src_abstract_component__["a"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "AbstractHandleComponent", function() { return __WEBPACK_IMPORTED_MODULE_6__src_abstract_component__["b"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DataTransferEffect", function() { return __WEBPACK_IMPORTED_MODULE_1__src_dnd_config__["a"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DragImage", function() { return __WEBPACK_IMPORTED_MODULE_1__src_dnd_config__["b"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DragDropConfig", function() { return __WEBPACK_IMPORTED_MODULE_1__src_dnd_config__["c"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DragDropData", function() { return __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["a"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "dragDropServiceFactory", function() { return __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["b"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DragDropService", function() { return __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["c"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "dragDropSortableServiceFactory", function() { return __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["d"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DragDropSortableService", function() { return __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["e"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DraggableComponent", function() { return __WEBPACK_IMPORTED_MODULE_3__src_draggable_component__["a"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DraggableHandleComponent", function() { return __WEBPACK_IMPORTED_MODULE_3__src_draggable_component__["b"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "DroppableComponent", function() { return __WEBPACK_IMPORTED_MODULE_4__src_droppable_component__["a"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "SortableContainer", function() { return __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["a"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "SortableComponent", function() { return __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["b"]; });
/* harmony namespace reexport (by provided) */ __webpack_require__.d(__webpack_exports__, "SortableHandleComponent", function() { return __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["c"]; });
// Copyright (C) 2016 Sergey Akopkokhyants
// This project is licensed under the terms of the MIT license.
// https://github.com/akserg/ng2-dnd
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};












var providers = [
    __WEBPACK_IMPORTED_MODULE_1__src_dnd_config__["c" /* DragDropConfig */],
    { provide: __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["c" /* DragDropService */], useFactory: __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["b" /* dragDropServiceFactory */] },
    { provide: __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["e" /* DragDropSortableService */], useFactory: __WEBPACK_IMPORTED_MODULE_2__src_dnd_service__["d" /* dragDropSortableServiceFactory */], deps: [__WEBPACK_IMPORTED_MODULE_1__src_dnd_config__["c" /* DragDropConfig */]] }
];
var DndModule = DndModule_1 = (function () {
    function DndModule() {
    }
    DndModule.forRoot = function () {
        return {
            ngModule: DndModule_1,
            providers: providers
        };
    };
    return DndModule;
}());
DndModule = DndModule_1 = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
        declarations: [__WEBPACK_IMPORTED_MODULE_3__src_draggable_component__["a" /* DraggableComponent */], __WEBPACK_IMPORTED_MODULE_3__src_draggable_component__["b" /* DraggableHandleComponent */], __WEBPACK_IMPORTED_MODULE_4__src_droppable_component__["a" /* DroppableComponent */], __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["a" /* SortableContainer */], __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["b" /* SortableComponent */], __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["c" /* SortableHandleComponent */]],
        exports: [__WEBPACK_IMPORTED_MODULE_3__src_draggable_component__["a" /* DraggableComponent */], __WEBPACK_IMPORTED_MODULE_3__src_draggable_component__["b" /* DraggableHandleComponent */], __WEBPACK_IMPORTED_MODULE_4__src_droppable_component__["a" /* DroppableComponent */], __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["a" /* SortableContainer */], __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["b" /* SortableComponent */], __WEBPACK_IMPORTED_MODULE_5__src_sortable_component__["c" /* SortableHandleComponent */]],
    })
], DndModule);

var DndModule_1;


/***/ })
/******/ ]);
});
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vd2VicGFjay91bml2ZXJzYWxNb2R1bGVEZWZpbml0aW9uIiwid2VicGFjazovLy93ZWJwYWNrL2Jvb3RzdHJhcCA3YzRkOTdjYzMxYmJhMDI0ZjMwZSIsIndlYnBhY2s6Ly8vZXh0ZXJuYWwgXCJAYW5ndWxhci9jb3JlXCIiLCJ3ZWJwYWNrOi8vLy4vc3JjL2RuZC5jb25maWcudHMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2RuZC5zZXJ2aWNlLnRzIiwid2VicGFjazovLy8uL3NyYy9hYnN0cmFjdC5jb21wb25lbnQudHMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2RyYWdnYWJsZS5jb21wb25lbnQudHMiLCJ3ZWJwYWNrOi8vLy4vc3JjL2Ryb3BwYWJsZS5jb21wb25lbnQudHMiLCJ3ZWJwYWNrOi8vLy4vc3JjL3NvcnRhYmxlLmNvbXBvbmVudC50cyIsIndlYnBhY2s6Ly8vLi9zcmMvZG5kLnV0aWxzLnRzIiwid2VicGFjazovLy8uL2luZGV4LnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7QUFDRCxPO0FDVkE7QUFDQTs7QUFFQTtBQUNBOztBQUVBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBOztBQUVBO0FBQ0E7O0FBRUE7QUFDQTs7QUFFQTtBQUNBO0FBQ0E7OztBQUdBO0FBQ0E7O0FBRUE7QUFDQTs7QUFFQTtBQUNBLG1EQUEyQyxjQUFjOztBQUV6RDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLGFBQUs7QUFDTDtBQUNBOztBQUVBO0FBQ0E7QUFDQTtBQUNBLG1DQUEyQiwwQkFBMEIsRUFBRTtBQUN2RCx5Q0FBaUMsZUFBZTtBQUNoRDtBQUNBO0FBQ0E7O0FBRUE7QUFDQSw4REFBc0QsK0RBQStEOztBQUVySDtBQUNBOztBQUVBO0FBQ0E7Ozs7Ozs7QUNoRUEsK0M7Ozs7Ozs7Ozs7QUNB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QUFzQixFQUFFO0lBQ2hFLEVBQUUsT0FBTyxFQUFFLGlGQUF1QixFQUFFLFVBQVUsRUFBRSx3RkFBOEIsRUFBRSxJQUFJLEVBQUUsQ0FBQyx1RUFBYyxDQUFDLEVBQUU7Q0FDM0csQ0FBQztBQU9GLElBQWEsU0FBUztJQUF0QjtJQU9BLENBQUM7SUFOUSxpQkFBTyxHQUFkO1FBQ00sTUFBTSxDQUFDO1lBQ0gsUUFBUSxFQUFFLFdBQVM7WUFDbkIsU0FBUyxFQUFFLFNBQVM7U0FDdkIsQ0FBQztJQUNOLENBQUM7SUFDTCxnQkFBQztBQUFELENBQUM7QUFQWSxTQUFTO0lBTHJCLDhFQUFRLENBQUM7UUFDUixZQUFZLEVBQUUsQ0FBQyxvRkFBa0IsRUFBRSwwRkFBd0IsRUFBRSxvRkFBa0IsRUFBRSxrRkFBaUIsRUFBRSxrRkFBaUIsRUFBRSx3RkFBdUIsQ0FBQztRQUMvSSxPQUFPLEVBQUcsQ0FBQyxvRkFBa0IsRUFBRSwwRkFBd0IsRUFBRSxvRkFBa0IsRUFBRSxrRkFBaUIsRUFBRSxrRkFBaUIsRUFBRSx3RkFBdUIsQ0FBQztLQUU1SSxDQUFDO0dBQ1csU0FBUyxDQU9yQjtBQVBxQiIsImZpbGUiOiJpbmRleC51bWQuanMiLCJzb3VyY2VzQ29udGVudCI6WyIoZnVuY3Rpb24gd2VicGFja1VuaXZlcnNhbE1vZHVsZURlZmluaXRpb24ocm9vdCwgZmFjdG9yeSkge1xuXHRpZih0eXBlb2YgZXhwb3J0cyA9PT0gJ29iamVjdCcgJiYgdHlwZW9mIG1vZHVsZSA9PT0gJ29iamVjdCcpXG5cdFx0bW9kdWxlLmV4cG9ydHMgPSBmYWN0b3J5KHJlcXVpcmUoXCJAYW5ndWxhci9jb3JlXCIpKTtcblx0ZWxzZSBpZih0eXBlb2YgZGVmaW5lID09PSAnZnVuY3Rpb24nICYmIGRlZmluZS5hbWQpXG5cdFx0ZGVmaW5lKFtcIkBhbmd1bGFyL2NvcmVcIl0sIGZhY3RvcnkpO1xuXHRlbHNlIGlmKHR5cGVvZiBleHBvcnRzID09PSAnb2JqZWN0Jylcblx0XHRleHBvcnRzW1wibmcyLWRuZFwiXSA9IGZhY3RvcnkocmVxdWlyZShcIkBhbmd1bGFyL2NvcmVcIikpO1xuXHRlbHNlXG5cdFx0cm9vdFtcIm5nMi1kbmRcIl0gPSBmYWN0b3J5KHJvb3RbXCJAYW5ndWxhci9jb3JlXCJdKTtcbn0pKHRoaXMsIGZ1bmN0aW9uKF9fV0VCUEFDS19FWFRFUk5BTF9NT0RVTEVfMF9fKSB7XG5yZXR1cm4gXG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIHdlYnBhY2svdW5pdmVyc2FsTW9kdWxlRGVmaW5pdGlvbiIsIiBcdC8vIFRoZSBtb2R1bGUgY2FjaGVcbiBcdHZhciBpbnN0YWxsZWRNb2R1bGVzID0ge307XG5cbiBcdC8vIFRoZSByZXF1aXJlIGZ1bmN0aW9uXG4gXHRmdW5jdGlvbiBfX3dlYnBhY2tfcmVxdWlyZV9fKG1vZHVsZUlkKSB7XG5cbiBcdFx0Ly8gQ2hlY2sgaWYgbW9kdWxlIGlzIGluIGNhY2hlXG4gXHRcdGlmKGluc3RhbGxlZE1vZHVsZXNbbW9kdWxlSWRdKSB7XG4gXHRcdFx0cmV0dXJuIGluc3RhbGxlZE1vZHVsZXNbbW9kdWxlSWRdLmV4cG9ydHM7XG4gXHRcdH1cbiBcdFx0Ly8gQ3JlYXRlIGEgbmV3IG1vZHVsZSAoYW5kIHB1dCBpdCBpbnRvIHRoZSBjYWNoZSlcbiBcdFx0dmFyIG1vZHVsZSA9IGluc3RhbGxlZE1vZHVsZXNbbW9kdWxlSWRdID0ge1xuIFx0XHRcdGk6IG1vZHVsZUlkLFxuIFx0XHRcdGw6IGZhbHNlLFxuIFx0XHRcdGV4cG9ydHM6IHt9XG4gXHRcdH07XG5cbiBcdFx0Ly8gRXhlY3V0ZSB0aGUgbW9kdWxlIGZ1bmN0aW9uXG4gXHRcdG1vZHVsZXNbbW9kdWxlSWRdLmNhbGwobW9kdWxlLmV4cG9ydHMsIG1vZHVsZSwgbW9kdWxlLmV4cG9ydHMsIF9fd2VicGFja19yZXF1aXJlX18pO1xuXG4gXHRcdC8vIEZsYWcgdGhlIG1vZHVsZSBhcyBsb2FkZWRcbiBcdFx0bW9kdWxlLmwgPSB0cnVlO1xuXG4gXHRcdC8vIFJldHVybiB0aGUgZXhwb3J0cyBvZiB0aGUgbW9kdWxlXG4gXHRcdHJldHVybiBtb2R1bGUuZXhwb3J0cztcbiBcdH1cblxuXG4gXHQvLyBleHBvc2UgdGhlIG1vZHVsZXMgb2JqZWN0IChfX3dlYnBhY2tfbW9kdWxlc19fKVxuIFx0X193ZWJwYWNrX3JlcXVpcmVfXy5tID0gbW9kdWxlcztcblxuIFx0Ly8gZXhwb3NlIHRoZSBtb2R1bGUgY2FjaGVcbiBcdF9fd2VicGFja19yZXF1aXJlX18uYyA9IGluc3RhbGxlZE1vZHVsZXM7XG5cbiBcdC8vIGlkZW50aXR5IGZ1bmN0aW9uIGZvciBjYWxsaW5nIGhhcm1vbnkgaW1wb3J0cyB3aXRoIHRoZSBjb3JyZWN0IGNvbnRleHRcbiBcdF9fd2VicGFja19yZXF1aXJlX18uaSA9IGZ1bmN0aW9uKHZhbHVlKSB7IHJldHVybiB2YWx1ZTsgfTtcblxuIFx0Ly8gZGVmaW5lIGdldHRlciBmdW5jdGlvbiBmb3IgaGFybW9ueSBleHBvcnRzXG4gXHRfX3dlYnBhY2tfcmVxdWlyZV9fLmQgPSBmdW5jdGlvbihleHBvcnRzLCBuYW1lLCBnZXR0ZXIpIHtcbiBcdFx0aWYoIV9fd2VicGFja19yZXF1aXJlX18ubyhleHBvcnRzLCBuYW1lKSkge1xuIFx0XHRcdE9iamVjdC5kZWZpbmVQcm9wZXJ0eShleHBvcnRzLCBuYW1lLCB7XG4gXHRcdFx0XHRjb25maWd1cmFibGU6IGZhbHNlLFxuIFx0XHRcdFx0ZW51bWVyYWJsZTogdHJ1ZSxcbiBcdFx0XHRcdGdldDogZ2V0dGVyXG4gXHRcdFx0fSk7XG4gXHRcdH1cbiBcdH07XG5cbiBcdC8vIGdldERlZmF1bHRFeHBvcnQgZnVuY3Rpb24gZm9yIGNvbXBhdGliaWxpdHkgd2l0aCBub24taGFybW9ueSBtb2R1bGVzXG4gXHRfX3dlYnBhY2tfcmVxdWlyZV9fLm4gPSBmdW5jdGlvbihtb2R1bGUpIHtcbiBcdFx0dmFyIGdldHRlciA9IG1vZHVsZSAmJiBtb2R1bGUuX19lc01vZHVsZSA/XG4gXHRcdFx0ZnVuY3Rpb24gZ2V0RGVmYXVsdCgpIHsgcmV0dXJuIG1vZHVsZVsnZGVmYXVsdCddOyB9IDpcbiBcdFx0XHRmdW5jdGlvbiBnZXRNb2R1bGVFeHBvcnRzKCkgeyByZXR1cm4gbW9kdWxlOyB9O1xuIFx0XHRfX3dlYnBhY2tfcmVxdWlyZV9fLmQoZ2V0dGVyLCAnYScsIGdldHRlcik7XG4gXHRcdHJldHVybiBnZXR0ZXI7XG4gXHR9O1xuXG4gXHQvLyBPYmplY3QucHJvdG90eXBlLmhhc093blByb3BlcnR5LmNhbGxcbiBcdF9fd2VicGFja19yZXF1aXJlX18ubyA9IGZ1bmN0aW9uKG9iamVjdCwgcHJvcGVydHkpIHsgcmV0dXJuIE9iamVjdC5wcm90b3R5cGUuaGFzT3duUHJvcGVydHkuY2FsbChvYmplY3QsIHByb3BlcnR5KTsgfTtcblxuIFx0Ly8gX193ZWJwYWNrX3B1YmxpY19wYXRoX19cbiBcdF9fd2VicGFja19yZXF1aXJlX18ucCA9IFwiL1wiO1xuXG4gXHQvLyBMb2FkIGVudHJ5IG1vZHVsZSBhbmQgcmV0dXJuIGV4cG9ydHNcbiBcdHJldHVybiBfX3dlYnBhY2tfcmVxdWlyZV9fKF9fd2VicGFja19yZXF1aXJlX18ucyA9IDgpO1xuXG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIHdlYnBhY2svYm9vdHN0cmFwIDdjNGQ5N2NjMzFiYmEwMjRmMzBlIiwibW9kdWxlLmV4cG9ydHMgPSBfX1dFQlBBQ0tfRVhURVJOQUxfTU9EVUxFXzBfXztcblxuXG4vLy8vLy8vLy8vLy8vLy8vLy9cbi8vIFdFQlBBQ0sgRk9PVEVSXG4vLyBleHRlcm5hbCBcIkBhbmd1bGFyL2NvcmVcIlxuLy8gbW9kdWxlIGlkID0gMFxuLy8gbW9kdWxlIGNodW5rcyA9IDAiLCIvLyBDb3B5cmlnaHQgKEMpIDIwMTYgU2VyZ2V5IEFrb3Brb2toeWFudHNcbi8vIFRoaXMgcHJvamVjdCBpcyBsaWNlbnNlZCB1bmRlciB0aGUgdGVybXMgb2YgdGhlIE1JVCBsaWNlbnNlLlxuLy8gaHR0cHM6Ly9naXRodWIuY29tL2Frc2VyZy9uZzItZG5kXG5cbmltcG9ydCB7aXNTdHJpbmd9IGZyb20gJy4vZG5kLnV0aWxzJztcblxuZXhwb3J0IGNsYXNzIERhdGFUcmFuc2ZlckVmZmVjdCB7XG5cbiAgICBzdGF0aWMgQ09QWSA9IG5ldyBEYXRhVHJhbnNmZXJFZmZlY3QoJ2NvcHknKTtcbiAgICBzdGF0aWMgTElOSyA9IG5ldyBEYXRhVHJhbnNmZXJFZmZlY3QoJ2xpbmsnKTtcbiAgICBzdGF0aWMgTU9WRSA9IG5ldyBEYXRhVHJhbnNmZXJFZmZlY3QoJ21vdmUnKTtcbiAgICBzdGF0aWMgTk9ORSA9IG5ldyBEYXRhVHJhbnNmZXJFZmZlY3QoJ25vbmUnKTtcblxuICAgIGNvbnN0cnVjdG9yKHB1YmxpYyBuYW1lOiBzdHJpbmcpIHsgfVxufVxuXG5leHBvcnQgY2xhc3MgRHJhZ0ltYWdlIHtcbiAgICBjb25zdHJ1Y3RvcihcbiAgICAgICAgcHVibGljIGltYWdlRWxlbWVudDogYW55LFxuICAgICAgICBwdWJsaWMgeF9vZmZzZXQ6IG51bWJlciA9IDAsXG4gICAgICAgIHB1YmxpYyB5X29mZnNldDogbnVtYmVyID0gMCkge1xuICAgICAgICAgICAgaWYgKGlzU3RyaW5nKHRoaXMuaW1hZ2VFbGVtZW50KSkge1xuICAgICAgICAgICAgICAgIC8vIENyZWF0ZSByZWFsIGltYWdlIGZyb20gc3RyaW5nIHNvdXJjZVxuICAgICAgICAgICAgICAgIGxldCBpbWdTY3I6IHN0cmluZyA9IDxzdHJpbmc+dGhpcy5pbWFnZUVsZW1lbnQ7XG4gICAgICAgICAgICAgICAgdGhpcy5pbWFnZUVsZW1lbnQgPSBuZXcgSFRNTEltYWdlRWxlbWVudCgpO1xuICAgICAgICAgICAgICAgICg8SFRNTEltYWdlRWxlbWVudD50aGlzLmltYWdlRWxlbWVudCkuc3JjID0gaW1nU2NyO1xuICAgICAgICAgICAgfVxuICAgICAgICB9XG59XG5cbmV4cG9ydCBjbGFzcyBEcmFnRHJvcENvbmZpZyB7XG4gICAgcHVibGljIG9uRHJhZ1N0YXJ0Q2xhc3M6IHN0cmluZyA9IFwiZG5kLWRyYWctc3RhcnRcIjtcbiAgICBwdWJsaWMgb25EcmFnRW50ZXJDbGFzczogc3RyaW5nID0gXCJkbmQtZHJhZy1lbnRlclwiO1xuICAgIHB1YmxpYyBvbkRyYWdPdmVyQ2xhc3M6IHN0cmluZyA9IFwiZG5kLWRyYWctb3ZlclwiO1xuICAgIHB1YmxpYyBvblNvcnRhYmxlRHJhZ0NsYXNzOiBzdHJpbmcgPSBcImRuZC1zb3J0YWJsZS1kcmFnXCI7XG5cbiAgICBwdWJsaWMgZHJhZ0VmZmVjdDogRGF0YVRyYW5zZmVyRWZmZWN0ID0gRGF0YVRyYW5zZmVyRWZmZWN0Lk1PVkU7XG4gICAgcHVibGljIGRyb3BFZmZlY3Q6IERhdGFUcmFuc2ZlckVmZmVjdCA9IERhdGFUcmFuc2ZlckVmZmVjdC5NT1ZFO1xuICAgIHB1YmxpYyBkcmFnQ3Vyc29yOiBzdHJpbmcgPSBcIm1vdmVcIjtcbiAgICBwdWJsaWMgZHJhZ0ltYWdlOiBEcmFnSW1hZ2U7XG4gICAgcHVibGljIGRlZmF1bHRDdXJzb3I6IHN0cmluZyA9IFwicG9pbnRlclwiO1xufVxuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL34vdHNsaW50LWxvYWRlciEuL3NyYy9kbmQuY29uZmlnLnRzIiwiLy8gQ29weXJpZ2h0IChDKSAyMDE2IFNlcmdleSBBa29wa29raHlhbnRzXG4vLyBUaGlzIHByb2plY3QgaXMgbGljZW5zZWQgdW5kZXIgdGhlIHRlcm1zIG9mIHRoZSBNSVQgbGljZW5zZS5cbi8vIGh0dHBzOi8vZ2l0aHViLmNvbS9ha3NlcmcvbmcyLWRuZFxuXG5pbXBvcnQge0luamVjdGFibGUsIEV2ZW50RW1pdHRlcn0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XG5cbmltcG9ydCB7RHJhZ0Ryb3BDb25maWd9IGZyb20gJy4vZG5kLmNvbmZpZyc7XG5pbXBvcnQge2lzUHJlc2VudH0gZnJvbSAnLi9kbmQudXRpbHMnO1xuaW1wb3J0IHtTb3J0YWJsZUNvbnRhaW5lcn0gZnJvbSAnLi9zb3J0YWJsZS5jb21wb25lbnQnO1xuXG5leHBvcnQgY2xhc3MgRHJhZ0Ryb3BEYXRhIHtcbiAgICBkcmFnRGF0YTogYW55O1xuICAgIG1vdXNlRXZlbnQ6IE1vdXNlRXZlbnQ7XG59XG5cbmV4cG9ydCBmdW5jdGlvbiBkcmFnRHJvcFNlcnZpY2VGYWN0b3J5KCk6IERyYWdEcm9wU2VydmljZSAge1xuICAgIHJldHVybiBuZXcgRHJhZ0Ryb3BTZXJ2aWNlKCk7XG59XG5cbkBJbmplY3RhYmxlKClcbmV4cG9ydCBjbGFzcyBEcmFnRHJvcFNlcnZpY2Uge1xuICAgIGFsbG93ZWREcm9wWm9uZXM6IEFycmF5PHN0cmluZz4gPSBbXTtcbiAgICBvbkRyYWdTdWNjZXNzQ2FsbGJhY2s6IEV2ZW50RW1pdHRlcjxEcmFnRHJvcERhdGE+O1xuICAgIGRyYWdEYXRhOiBhbnk7XG4gICAgaXNEcmFnZ2VkOiBib29sZWFuO1xufVxuXG5leHBvcnQgZnVuY3Rpb24gZHJhZ0Ryb3BTb3J0YWJsZVNlcnZpY2VGYWN0b3J5KGNvbmZpZzogRHJhZ0Ryb3BDb25maWcpOiBEcmFnRHJvcFNvcnRhYmxlU2VydmljZSAge1xuICAgIHJldHVybiBuZXcgRHJhZ0Ryb3BTb3J0YWJsZVNlcnZpY2UoY29uZmlnKTtcbn1cblxuQEluamVjdGFibGUoKVxuZXhwb3J0IGNsYXNzIERyYWdEcm9wU29ydGFibGVTZXJ2aWNlIHtcbiAgICBpbmRleDogbnVtYmVyO1xuICAgIHNvcnRhYmxlQ29udGFpbmVyOiBTb3J0YWJsZUNvbnRhaW5lcjtcbiAgICBpc0RyYWdnZWQ6IGJvb2xlYW47XG5cbiAgICBwcml2YXRlIF9lbGVtOiBIVE1MRWxlbWVudDtcbiAgICBwdWJsaWMgZ2V0IGVsZW0oKTogSFRNTEVsZW1lbnQge1xuICAgICAgICByZXR1cm4gdGhpcy5fZWxlbTtcbiAgICB9XG5cbiAgICBjb25zdHJ1Y3Rvcihwcml2YXRlIF9jb25maWc6RHJhZ0Ryb3BDb25maWcpIHt9XG5cbiAgICBtYXJrU29ydGFibGUoZWxlbTogSFRNTEVsZW1lbnQpIHtcbiAgICAgICAgaWYgKGlzUHJlc2VudCh0aGlzLl9lbGVtKSkge1xuICAgICAgICAgICAgdGhpcy5fZWxlbS5jbGFzc0xpc3QucmVtb3ZlKHRoaXMuX2NvbmZpZy5vblNvcnRhYmxlRHJhZ0NsYXNzKTtcbiAgICAgICAgfVxuICAgICAgICBpZiAoaXNQcmVzZW50KGVsZW0pKSB7XG4gICAgICAgICAgICB0aGlzLl9lbGVtID0gZWxlbTtcbiAgICAgICAgICAgIHRoaXMuX2VsZW0uY2xhc3NMaXN0LmFkZCh0aGlzLl9jb25maWcub25Tb3J0YWJsZURyYWdDbGFzcyk7XG4gICAgICAgIH1cbiAgICB9XG59XG5cblxuXG4vLyBXRUJQQUNLIEZPT1RFUiAvL1xuLy8gLi9+L3RzbGludC1sb2FkZXIhLi9zcmMvZG5kLnNlcnZpY2UudHMiLCIvLyBDb3B5cmlnaHQgKEMpIDIwMTYgU2VyZ2V5IEFrb3Brb2toeWFudHNcbi8vIFRoaXMgcHJvamVjdCBpcyBsaWNlbnNlZCB1bmRlciB0aGUgdGVybXMgb2YgdGhlIE1JVCBsaWNlbnNlLlxuLy8gaHR0cHM6Ly9naXRodWIuY29tL2Frc2VyZy9uZzItZG5kXG5cbmltcG9ydCB7SW5qZWN0YWJsZSwgQ2hhbmdlRGV0ZWN0b3JSZWZ9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0IHtFbGVtZW50UmVmfSBmcm9tICdAYW5ndWxhci9jb3JlJztcblxuaW1wb3J0IHtEcmFnRHJvcENvbmZpZywgRHJhZ0ltYWdlfSBmcm9tICcuL2RuZC5jb25maWcnO1xuaW1wb3J0IHtEcmFnRHJvcFNlcnZpY2V9IGZyb20gJy4vZG5kLnNlcnZpY2UnO1xuaW1wb3J0IHtpc1N0cmluZywgaXNGdW5jdGlvbiwgaXNQcmVzZW50LCBjcmVhdGVJbWFnZSwgY2FsbEZ1bn0gZnJvbSAnLi9kbmQudXRpbHMnO1xuXG5ASW5qZWN0YWJsZSgpXG5leHBvcnQgYWJzdHJhY3QgY2xhc3MgQWJzdHJhY3RDb21wb25lbnQge1xuICAgIF9lbGVtOiBIVE1MRWxlbWVudDtcbiAgICBfZHJhZ0hhbmRsZTogSFRNTEVsZW1lbnQ7XG4gICAgX2RyYWdIZWxwZXI6IEhUTUxFbGVtZW50O1xuICAgIF9kZWZhdWx0Q3Vyc29yOiBzdHJpbmc7XG5cbiAgICAvKipcbiAgICAgKiBMYXN0IGVsZW1lbnQgdGhhdCB3YXMgbW91c2Vkb3duJ2VkXG4gICAgICovXG4gICAgX3RhcmdldDogRXZlbnRUYXJnZXQ7XG5cbiAgICAvKipcbiAgICAgKiBXaGV0aGVyIHRoZSBvYmplY3QgaXMgZHJhZ2dhYmxlLiBEZWZhdWx0IGlzIHRydWUuXG4gICAgICovXG4gICAgcHJpdmF0ZSBfZHJhZ0VuYWJsZWQ6IGJvb2xlYW4gPSBmYWxzZTtcbiAgICBzZXQgZHJhZ0VuYWJsZWQoZW5hYmxlZDogYm9vbGVhbikge1xuICAgICAgICB0aGlzLl9kcmFnRW5hYmxlZCA9ICEhZW5hYmxlZDtcbiAgICAgICAgdGhpcy5fZWxlbS5kcmFnZ2FibGUgPSB0aGlzLl9kcmFnRW5hYmxlZDtcbiAgICB9XG4gICAgZ2V0IGRyYWdFbmFibGVkKCk6IGJvb2xlYW4ge1xuICAgICAgICByZXR1cm4gdGhpcy5fZHJhZ0VuYWJsZWQ7XG4gICAgfVxuXG4gICAgLyoqXG4gICAgICogQWxsb3dzIGRyb3Agb24gdGhpcyBlbGVtZW50XG4gICAgICovXG4gICAgZHJvcEVuYWJsZWQ6IGJvb2xlYW4gPSBmYWxzZTtcbiAgICAvKipcbiAgICAgKiBEcmFnIGVmZmVjdFxuICAgICAqL1xuICAgIGVmZmVjdEFsbG93ZWQ6IHN0cmluZztcbiAgICAvKipcbiAgICAgKiBEcmFnIGN1cnNvclxuICAgICAqL1xuICAgIGVmZmVjdEN1cnNvcjogc3RyaW5nO1xuXG4gICAgLyoqXG4gICAgICogUmVzdHJpY3QgcGxhY2VzIHdoZXJlIGEgZHJhZ2dhYmxlIGVsZW1lbnQgY2FuIGJlIGRyb3BwZWQuIEVpdGhlciBvbmUgb2ZcbiAgICAgKiB0aGVzZSB0d28gbWVjaGFuaXNtcyBjYW4gYmUgdXNlZDpcbiAgICAgKlxuICAgICAqIC0gZHJvcFpvbmVzOiBhbiBhcnJheSBvZiBzdHJpbmdzIHRoYXQgcGVybWl0cyB0byBzcGVjaWZ5IHRoZSBkcm9wIHpvbmVzXG4gICAgICogICBhc3NvY2lhdGVkIHdpdGggdGhpcyBjb21wb25lbnQuIEJ5IGRlZmF1bHQsIGlmIHRoZSBkcm9wLXpvbmVzIGF0dHJpYnV0ZVxuICAgICAqICAgaXMgbm90IHNwZWNpZmllZCwgdGhlIGRyb3BwYWJsZSBjb21wb25lbnQgYWNjZXB0cyBkcm9wIG9wZXJhdGlvbnMgYnlcbiAgICAgKiAgIGFsbCB0aGUgZHJhZ2dhYmxlIGNvbXBvbmVudHMgdGhhdCBkbyBub3Qgc3BlY2lmeSB0aGUgYWxsb3dlZC1kcm9wLXpvbmVzXG4gICAgICpcbiAgICAgKiAtIGFsbG93RHJvcDogYSBib29sZWFuIGZ1bmN0aW9uIGZvciBkcm9wcGFibGUgY29tcG9uZW50cywgdGhhdCBpcyBjaGVja2VkXG4gICAgICogICB3aGVuIGFuIGl0ZW0gaXMgZHJhZ2dlZC4gVGhlIGZ1bmN0aW9uIGlzIHBhc3NlZCB0aGUgZHJhZ0RhdGEgb2YgdGhpc1xuICAgICAqICAgaXRlbS5cbiAgICAgKiAgIC0gaWYgaXQgcmV0dXJucyB0cnVlLCB0aGUgaXRlbSBjYW4gYmUgZHJvcHBlZCBpbiB0aGlzIGNvbXBvbmVudFxuICAgICAqICAgLSBpZiBpdCByZXR1cm5zIGZhbHNlLCB0aGUgaXRlbSBjYW5ub3QgYmUgZHJvcHBlZCBoZXJlXG4gICAgICovXG4gICAgYWxsb3dEcm9wOiAoZHJvcERhdGE6IGFueSkgPT4gYm9vbGVhbjtcbiAgICBkcm9wWm9uZXM6IHN0cmluZ1tdID0gW107XG5cbiAgICAvKipcbiAgICAgKiBIZXJlIGlzIHRoZSBwcm9wZXJ0eSBkcmFnSW1hZ2UgeW91IGNhbiB1c2U6XG4gICAgICogLSBUaGUgc3RyaW5nIHZhbHVlIGFzIHVybCB0byB0aGUgaW1hZ2VcbiAgICAgKiAgIDxkaXYgY2xhc3M9XCJwYW5lbCBwYW5lbC1kZWZhdWx0XCJcbiAgICAgKiAgICAgICAgZG5kLWRyYWdnYWJsZSBbZHJhZ0VuYWJsZWRdPVwidHJ1ZVwiXG4gICAgICogICAgICAgIFtkcmFnSW1hZ2VdPVwiL2ltYWdlcy9zaW1wbGVyLnBuZ1wiPlxuICAgICAqIC4uLlxuICAgICAqIC0gVGhlIERyYWdJbWFnZSB2YWx1ZSB3aXRoIEltYWdlIGFuZCBvcHRpb25hbCBvZmZzZXQgYnkgeCBhbmQgeTpcbiAgICAgKiAgIGxldCBteURyYWdJbWFnZTogRHJhZ0ltYWdlID0gbmV3IERyYWdJbWFnZShcIi9pbWFnZXMvc2ltcGxlcjEucG5nXCIsIDAsIDApO1xuICAgICAqIC4uLlxuICAgICAqICAgPGRpdiBjbGFzcz1cInBhbmVsIHBhbmVsLWRlZmF1bHRcIlxuICAgICAqICAgICAgICBkbmQtZHJhZ2dhYmxlIFtkcmFnRW5hYmxlZF09XCJ0cnVlXCJcbiAgICAgKiAgICAgICAgW2RyYWdJbWFnZV09XCJteURyYWdJbWFnZVwiPlxuICAgICAqIC4uLlxuICAgICAqIC0gVGhlIGN1c3RvbSBmdW5jdGlvbiB0byByZXR1cm4gdGhlIHZhbHVlIG9mIGRyYWdJbWFnZSBwcm9ncmFtbWF0aWNhbGx5OlxuICAgICAqICAgPGRpdiBjbGFzcz1cInBhbmVsIHBhbmVsLWRlZmF1bHRcIlxuICAgICAqICAgICAgICBkbmQtZHJhZ2dhYmxlIFtkcmFnRW5hYmxlZF09XCJ0cnVlXCJcbiAgICAgKiAgICAgICAgW2RyYWdJbWFnZV09XCJnZXREcmFnSW1hZ2Uoc29tZURhdGEpXCI+XG4gICAgICogLi4uXG4gICAgICogICBnZXREcmFnSW1hZ2UodmFsdWU6YW55KTogc3RyaW5nIHtcbiAgICAgKiAgICAgcmV0dXJuIHZhbHVlID8gXCIvaW1hZ2VzL3NpbXBsZXIxLnBuZ1wiIDogXCIvaW1hZ2VzL3NpbXBsZXIyLnBuZ1wiXG4gICAgICogICB9XG4gICAgICovXG4gICAgZHJhZ0ltYWdlOiBzdHJpbmcgfCBEcmFnSW1hZ2UgfCBGdW5jdGlvbjtcblxuICAgIGNsb25lSXRlbTogYm9vbGVhbiA9IGZhbHNlO1xuXG4gICAgY29uc3RydWN0b3IoZWxlbVJlZjogRWxlbWVudFJlZiwgcHVibGljIF9kcmFnRHJvcFNlcnZpY2U6IERyYWdEcm9wU2VydmljZSwgcHVibGljIF9jb25maWc6IERyYWdEcm9wQ29uZmlnLFxuICAgICAgICBwcml2YXRlIF9jZHI6IENoYW5nZURldGVjdG9yUmVmKSB7XG5cbiAgICAgICAgLy8gQXNzaWduIGRlZmF1bHQgY3Vyc29yIHVubGVzcyBvdmVycmlkZGVuXG4gICAgICAgIHRoaXMuX2RlZmF1bHRDdXJzb3IgPSBfY29uZmlnLmRlZmF1bHRDdXJzb3I7XG4gICAgICAgIHRoaXMuX2VsZW0gPSBlbGVtUmVmLm5hdGl2ZUVsZW1lbnQ7XG4gICAgICAgIHRoaXMuX2VsZW0uc3R5bGUuY3Vyc29yID0gdGhpcy5fZGVmYXVsdEN1cnNvcjsgIC8vIHNldCBkZWZhdWx0IGN1cnNvciBvbiBvdXIgZWxlbWVudFxuICAgICAgICAvL1xuICAgICAgICAvLyBEUk9QIGV2ZW50c1xuICAgICAgICAvL1xuICAgICAgICB0aGlzLl9lbGVtLm9uZHJhZ2VudGVyID0gKGV2ZW50OiBFdmVudCkgPT4ge1xuICAgICAgICAgICAgdGhpcy5fb25EcmFnRW50ZXIoZXZlbnQpO1xuICAgICAgICB9O1xuICAgICAgICB0aGlzLl9lbGVtLm9uZHJhZ292ZXIgPSAoZXZlbnQ6IERyYWdFdmVudCkgPT4ge1xuICAgICAgICAgICAgdGhpcy5fb25EcmFnT3ZlcihldmVudCk7XG4gICAgICAgICAgICAvL1xuICAgICAgICAgICAgaWYgKGV2ZW50LmRhdGFUcmFuc2ZlciAhPSBudWxsKSB7XG4gICAgICAgICAgICAgICAgZXZlbnQuZGF0YVRyYW5zZmVyLmRyb3BFZmZlY3QgPSB0aGlzLl9jb25maWcuZHJvcEVmZmVjdC5uYW1lO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICByZXR1cm4gZmFsc2U7XG4gICAgICAgIH07XG4gICAgICAgIHRoaXMuX2VsZW0ub25kcmFnbGVhdmUgPSAoZXZlbnQ6IEV2ZW50KSA9PiB7XG4gICAgICAgICAgICB0aGlzLl9vbkRyYWdMZWF2ZShldmVudCk7XG4gICAgICAgIH07XG4gICAgICAgIHRoaXMuX2VsZW0ub25kcm9wID0gKGV2ZW50OiBFdmVudCkgPT4ge1xuICAgICAgICAgICAgdGhpcy5fb25Ecm9wKGV2ZW50KTtcbiAgICAgICAgfTtcbiAgICAgICAgLy9cbiAgICAgICAgLy8gRHJhZyBldmVudHNcbiAgICAgICAgLy9cbiAgICAgICAgdGhpcy5fZWxlbS5vbm1vdXNlZG93biA9IChldmVudDogTW91c2VFdmVudCkgPT4ge1xuICAgICAgICAgICAgdGhpcy5fdGFyZ2V0ID0gZXZlbnQudGFyZ2V0O1xuICAgICAgICB9O1xuICAgICAgICB0aGlzLl9lbGVtLm9uZHJhZ3N0YXJ0ID0gKGV2ZW50OiBEcmFnRXZlbnQpID0+IHtcbiAgICAgICAgICAgIGlmICh0aGlzLl9kcmFnSGFuZGxlKSB7XG4gICAgICAgICAgICAgICAgaWYgKCF0aGlzLl9kcmFnSGFuZGxlLmNvbnRhaW5zKDxFbGVtZW50PnRoaXMuX3RhcmdldCkpIHtcbiAgICAgICAgICAgICAgICAgICAgZXZlbnQucHJldmVudERlZmF1bHQoKTtcbiAgICAgICAgICAgICAgICAgICAgcmV0dXJuO1xuICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgdGhpcy5fb25EcmFnU3RhcnQoZXZlbnQpO1xuICAgICAgICAgICAgLy9cbiAgICAgICAgICAgIGlmIChldmVudC5kYXRhVHJhbnNmZXIgIT0gbnVsbCkge1xuICAgICAgICAgICAgICAgIGV2ZW50LmRhdGFUcmFuc2Zlci5zZXREYXRhKCd0ZXh0JywgJycpO1xuICAgICAgICAgICAgICAgIC8vIENoYW5nZSBkcmFnIGVmZmVjdFxuICAgICAgICAgICAgICAgIGV2ZW50LmRhdGFUcmFuc2Zlci5lZmZlY3RBbGxvd2VkID0gdGhpcy5lZmZlY3RBbGxvd2VkIHx8IHRoaXMuX2NvbmZpZy5kcmFnRWZmZWN0Lm5hbWU7XG4gICAgICAgICAgICAgICAgLy8gQ2hhbmdlIGRyYWcgaW1hZ2VcbiAgICAgICAgICAgICAgICBpZiAoaXNQcmVzZW50KHRoaXMuZHJhZ0ltYWdlKSkge1xuICAgICAgICAgICAgICAgICAgICBpZiAoaXNTdHJpbmcodGhpcy5kcmFnSW1hZ2UpKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAoPGFueT5ldmVudC5kYXRhVHJhbnNmZXIpLnNldERyYWdJbWFnZShjcmVhdGVJbWFnZSg8c3RyaW5nPnRoaXMuZHJhZ0ltYWdlKSk7XG4gICAgICAgICAgICAgICAgICAgIH0gZWxzZSBpZiAoaXNGdW5jdGlvbih0aGlzLmRyYWdJbWFnZSkpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICg8YW55PmV2ZW50LmRhdGFUcmFuc2Zlcikuc2V0RHJhZ0ltYWdlKGNhbGxGdW4oPEZ1bmN0aW9uPnRoaXMuZHJhZ0ltYWdlKSk7XG4gICAgICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICAgICBsZXQgaW1nOiBEcmFnSW1hZ2UgPSA8RHJhZ0ltYWdlPnRoaXMuZHJhZ0ltYWdlO1xuICAgICAgICAgICAgICAgICAgICAgICAgKDxhbnk+ZXZlbnQuZGF0YVRyYW5zZmVyKS5zZXREcmFnSW1hZ2UoaW1nLmltYWdlRWxlbWVudCwgaW1nLnhfb2Zmc2V0LCBpbWcueV9vZmZzZXQpO1xuICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgfSBlbHNlIGlmIChpc1ByZXNlbnQodGhpcy5fY29uZmlnLmRyYWdJbWFnZSkpIHtcbiAgICAgICAgICAgICAgICAgICAgbGV0IGRyYWdJbWFnZTogRHJhZ0ltYWdlID0gdGhpcy5fY29uZmlnLmRyYWdJbWFnZTtcbiAgICAgICAgICAgICAgICAgICAgKDxhbnk+ZXZlbnQuZGF0YVRyYW5zZmVyKS5zZXREcmFnSW1hZ2UoZHJhZ0ltYWdlLmltYWdlRWxlbWVudCwgZHJhZ0ltYWdlLnhfb2Zmc2V0LCBkcmFnSW1hZ2UueV9vZmZzZXQpO1xuICAgICAgICAgICAgICAgIH0gZWxzZSBpZiAodGhpcy5jbG9uZUl0ZW0pIHtcbiAgICAgICAgICAgICAgICAgICAgdGhpcy5fZHJhZ0hlbHBlciA9IDxIVE1MRWxlbWVudD50aGlzLl9lbGVtLmNsb25lTm9kZSh0cnVlKTtcbiAgICAgICAgICAgICAgICAgICAgdGhpcy5fZHJhZ0hlbHBlci5jbGFzc0xpc3QuYWRkKCdkbmQtZHJhZy1pdGVtJyk7XG4gICAgICAgICAgICAgICAgICAgIHRoaXMuX2RyYWdIZWxwZXIuc3R5bGUucG9zaXRpb24gPSBcImFic29sdXRlXCI7XG4gICAgICAgICAgICAgICAgICAgIHRoaXMuX2RyYWdIZWxwZXIuc3R5bGUudG9wID0gXCIwcHhcIjtcbiAgICAgICAgICAgICAgICAgICAgdGhpcy5fZHJhZ0hlbHBlci5zdHlsZS5sZWZ0ID0gXCItMTAwMHB4XCI7XG4gICAgICAgICAgICAgICAgICAgIHRoaXMuX2VsZW0ucGFyZW50RWxlbWVudC5hcHBlbmRDaGlsZCh0aGlzLl9kcmFnSGVscGVyKTtcbiAgICAgICAgICAgICAgICAgICAgKDxhbnk+ZXZlbnQuZGF0YVRyYW5zZmVyKS5zZXREcmFnSW1hZ2UodGhpcy5fZHJhZ0hlbHBlciwgZXZlbnQub2Zmc2V0WCwgZXZlbnQub2Zmc2V0WSk7XG4gICAgICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICAgICAgLy8gQ2hhbmdlIGRyYWcgY3Vyc29yXG4gICAgICAgICAgICAgICAgbGV0IGN1cnNvcmVsZW0gPSAodGhpcy5fZHJhZ0hhbmRsZSkgPyB0aGlzLl9kcmFnSGFuZGxlIDogdGhpcy5fZWxlbTtcblxuICAgICAgICAgICAgICAgIGlmICh0aGlzLl9kcmFnRW5hYmxlZCkge1xuICAgICAgICAgICAgICAgICAgICBjdXJzb3JlbGVtLnN0eWxlLmN1cnNvciA9IHRoaXMuZWZmZWN0Q3Vyc29yID8gdGhpcy5lZmZlY3RDdXJzb3IgOiB0aGlzLl9jb25maWcuZHJhZ0N1cnNvcjtcbiAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICBjdXJzb3JlbGVtLnN0eWxlLmN1cnNvciA9IHRoaXMuX2RlZmF1bHRDdXJzb3I7XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgfVxuICAgICAgICB9O1xuXG4gICAgICAgIHRoaXMuX2VsZW0ub25kcmFnZW5kID0gKGV2ZW50OiBFdmVudCkgPT4ge1xuICAgICAgICAgICAgaWYgKHRoaXMuX2VsZW0ucGFyZW50RWxlbWVudCAmJiB0aGlzLl9kcmFnSGVscGVyKSB7XG4gICAgICAgICAgICAgICAgdGhpcy5fZWxlbS5wYXJlbnRFbGVtZW50LnJlbW92ZUNoaWxkKHRoaXMuX2RyYWdIZWxwZXIpO1xuICAgICAgICAgICAgfVxuICAgICAgICAgICAgLy8gY29uc29sZS5sb2coJ29uZHJhZ2VuZCcsIGV2ZW50LnRhcmdldCk7XG4gICAgICAgICAgICB0aGlzLl9vbkRyYWdFbmQoZXZlbnQpO1xuICAgICAgICAgICAgLy8gUmVzdG9yZSBzdHlsZSBvZiBkcmFnZ2VkIGVsZW1lbnRcbiAgICAgICAgICAgIGxldCBjdXJzb3JlbGVtID0gKHRoaXMuX2RyYWdIYW5kbGUpID8gdGhpcy5fZHJhZ0hhbmRsZSA6IHRoaXMuX2VsZW07XG4gICAgICAgICAgICBjdXJzb3JlbGVtLnN0eWxlLmN1cnNvciA9IHRoaXMuX2RlZmF1bHRDdXJzb3I7XG4gICAgICAgIH07XG4gICAgfVxuXG4gICAgcHVibGljIHNldERyYWdIYW5kbGUoZWxlbTogSFRNTEVsZW1lbnQpIHtcbiAgICAgICAgdGhpcy5fZHJhZ0hhbmRsZSA9IGVsZW07XG4gICAgfVxuICAgIC8qKioqKioqIENoYW5nZSBkZXRlY3Rpb24gKioqKioqL1xuXG4gICAgZGV0ZWN0Q2hhbmdlcygpIHtcbiAgICAgICAgLy8gUHJvZ3JhbW1hdGljYWxseSBydW4gY2hhbmdlIGRldGVjdGlvbiB0byBmaXggaXNzdWUgaW4gU2FmYXJpXG4gICAgICAgIHNldFRpbWVvdXQoKCkgPT4ge1xuICAgICAgICAgICAgdGhpcy5fY2RyLmRldGVjdENoYW5nZXMoKTtcbiAgICAgICAgfSwgMjUwKTtcbiAgICB9XG5cbiAgICAvLyoqKioqKiBEcm9wcGFibGUgKioqKioqKi8vXG4gICAgcHJpdmF0ZSBfb25EcmFnRW50ZXIoZXZlbnQ6IEV2ZW50KTogdm9pZCB7XG4gICAgICAgIC8vIGNvbnNvbGUubG9nKCdvbmRyYWdlbnRlci5faXNEcm9wQWxsb3dlZCcsIHRoaXMuX2lzRHJvcEFsbG93ZWQpO1xuICAgICAgICBpZiAodGhpcy5faXNEcm9wQWxsb3dlZChldmVudCkpIHtcbiAgICAgICAgICAgIC8vIGV2ZW50LnByZXZlbnREZWZhdWx0KCk7XG4gICAgICAgICAgICB0aGlzLl9vbkRyYWdFbnRlckNhbGxiYWNrKGV2ZW50KTtcbiAgICAgICAgfVxuICAgIH1cblxuICAgIHByaXZhdGUgX29uRHJhZ092ZXIoZXZlbnQ6IEV2ZW50KSB7XG4gICAgICAgIC8vIC8vIGNvbnNvbGUubG9nKCdvbmRyYWdvdmVyLl9pc0Ryb3BBbGxvd2VkJywgdGhpcy5faXNEcm9wQWxsb3dlZCk7XG4gICAgICAgIGlmICh0aGlzLl9pc0Ryb3BBbGxvd2VkKGV2ZW50KSkge1xuICAgICAgICAgICAgLy8gVGhlIGVsZW1lbnQgaXMgb3ZlciB0aGUgc2FtZSBzb3VyY2UgZWxlbWVudCAtIGRvIG5vdGhpbmdcbiAgICAgICAgICAgIGlmIChldmVudC5wcmV2ZW50RGVmYXVsdCkge1xuICAgICAgICAgICAgICAgIC8vIE5lY2Vzc2FyeS4gQWxsb3dzIHVzIHRvIGRyb3AuXG4gICAgICAgICAgICAgICAgZXZlbnQucHJldmVudERlZmF1bHQoKTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgdGhpcy5fb25EcmFnT3ZlckNhbGxiYWNrKGV2ZW50KTtcbiAgICAgICAgfVxuICAgIH1cbiAgICBcbiAgICBwcml2YXRlIF9vbkRyYWdMZWF2ZShldmVudDogRXZlbnQpOiB2b2lkIHtcbiAgICAgICAgLy8gY29uc29sZS5sb2coJ29uZHJhZ2xlYXZlLl9pc0Ryb3BBbGxvd2VkJywgdGhpcy5faXNEcm9wQWxsb3dlZCk7XG4gICAgICAgIGlmICh0aGlzLl9pc0Ryb3BBbGxvd2VkKGV2ZW50KSkge1xuICAgICAgICAgICAgLy8gZXZlbnQucHJldmVudERlZmF1bHQoKTtcbiAgICAgICAgICAgIHRoaXMuX29uRHJhZ0xlYXZlQ2FsbGJhY2soZXZlbnQpO1xuICAgICAgICB9XG4gICAgfVxuXG4gICAgcHJpdmF0ZSBfb25Ecm9wKGV2ZW50OiBFdmVudCk6IHZvaWQge1xuICAgICAgICAvLyBOZWNlc3NhcnkuIEFsbG93cyB1cyB0byBkcm9wLlxuICAgICAgICB0aGlzLl9wcmV2ZW50QW5kU3RvcChldmVudCk7XG4gICAgICAgIC8vIGNvbnNvbGUubG9nKCdvbmRyb3AuX2lzRHJvcEFsbG93ZWQnLCB0aGlzLl9pc0Ryb3BBbGxvd2VkKTtcbiAgICAgICAgaWYgKHRoaXMuX2lzRHJvcEFsbG93ZWQoZXZlbnQpKSB7ICAgICAgICAgICAgXG5cbiAgICAgICAgICAgIHRoaXMuX29uRHJvcENhbGxiYWNrKGV2ZW50KTtcblxuICAgICAgICAgICAgdGhpcy5kZXRlY3RDaGFuZ2VzKCk7XG4gICAgICAgIH1cbiAgICB9XG5cbiAgICBwcml2YXRlIF9pc0Ryb3BBbGxvd2VkKGV2ZW50OiBhbnkpOiBib29sZWFuIHtcbiAgICAgICAgaWYgKCh0aGlzLl9kcmFnRHJvcFNlcnZpY2UuaXNEcmFnZ2VkIHx8IChldmVudC5kYXRhVHJhbnNmZXIgJiYgZXZlbnQuZGF0YVRyYW5zZmVyLmZpbGVzKSkgJiYgdGhpcy5kcm9wRW5hYmxlZCkge1xuICAgICAgICAgICAgLy8gRmlyc3QsIGlmIGBhbGxvd0Ryb3BgIGlzIHNldCwgY2FsbCBpdCB0byBkZXRlcm1pbmUgd2hldGhlciB0aGVcbiAgICAgICAgICAgIC8vIGRyYWdnZWQgZWxlbWVudCBjYW4gYmUgZHJvcHBlZCBoZXJlLlxuICAgICAgICAgICAgaWYgKHRoaXMuYWxsb3dEcm9wKSB7XG4gICAgICAgICAgICAgICAgcmV0dXJuIHRoaXMuYWxsb3dEcm9wKHRoaXMuX2RyYWdEcm9wU2VydmljZS5kcmFnRGF0YSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIC8vIE90aGVyd2lzZSwgdXNlIGRyb3Bab25lcyBpZiB0aGV5IGFyZSBzZXQuXG4gICAgICAgICAgICBpZiAodGhpcy5kcm9wWm9uZXMubGVuZ3RoID09PSAwICYmIHRoaXMuX2RyYWdEcm9wU2VydmljZS5hbGxvd2VkRHJvcFpvbmVzLmxlbmd0aCA9PT0gMCkge1xuICAgICAgICAgICAgICAgIHJldHVybiB0cnVlO1xuICAgICAgICAgICAgfVxuICAgICAgICAgICAgZm9yIChsZXQgaTogbnVtYmVyID0gMDsgaSA8IHRoaXMuX2RyYWdEcm9wU2VydmljZS5hbGxvd2VkRHJvcFpvbmVzLmxlbmd0aDsgaSsrKSB7XG4gICAgICAgICAgICAgICAgbGV0IGRyYWdab25lOiBzdHJpbmcgPSB0aGlzLl9kcmFnRHJvcFNlcnZpY2UuYWxsb3dlZERyb3Bab25lc1tpXTtcbiAgICAgICAgICAgICAgICBpZiAodGhpcy5kcm9wWm9uZXMuaW5kZXhPZihkcmFnWm9uZSkgIT09IC0xKSB7XG4gICAgICAgICAgICAgICAgICAgIHJldHVybiB0cnVlO1xuICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgIH1cbiAgICAgICAgfVxuICAgICAgICByZXR1cm4gZmFsc2U7XG4gICAgfVxuXG4gICAgcHJpdmF0ZSBfcHJldmVudEFuZFN0b3AoZXZlbnQ6IEV2ZW50KTogYW55IHtcbiAgICAgICAgaWYgKGV2ZW50LnByZXZlbnREZWZhdWx0KSB7XG4gICAgICAgICAgICBldmVudC5wcmV2ZW50RGVmYXVsdCgpO1xuICAgICAgICB9XG4gICAgICAgIGlmIChldmVudC5zdG9wUHJvcGFnYXRpb24pIHtcbiAgICAgICAgICAgIGV2ZW50LnN0b3BQcm9wYWdhdGlvbigpO1xuICAgICAgICB9XG4gICAgfVxuXG4gICAgLy8qKioqKioqKioqKiBEcmFnZ2FibGUgKioqKioqKioqKi8vXG5cbiAgICBwcml2YXRlIF9vbkRyYWdTdGFydChldmVudDogRXZlbnQpOiB2b2lkIHtcbiAgICAgICAgLy9jb25zb2xlLmxvZygnb25kcmFnc3RhcnQuZHJhZ0VuYWJsZWQnLCB0aGlzLl9kcmFnRW5hYmxlZCk7XG4gICAgICAgIGlmICh0aGlzLl9kcmFnRW5hYmxlZCkge1xuICAgICAgICAgICAgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmFsbG93ZWREcm9wWm9uZXMgPSB0aGlzLmRyb3Bab25lcztcbiAgICAgICAgICAgIC8vIGNvbnNvbGUubG9nKCdvbmRyYWdzdGFydC5hbGxvd2VkRHJvcFpvbmVzJywgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmFsbG93ZWREcm9wWm9uZXMpO1xuICAgICAgICAgICAgdGhpcy5fb25EcmFnU3RhcnRDYWxsYmFjayhldmVudCk7XG4gICAgICAgIH1cbiAgICB9XG5cbiAgICBwcml2YXRlIF9vbkRyYWdFbmQoZXZlbnQ6IEV2ZW50KTogdm9pZCB7XG4gICAgICAgIHRoaXMuX2RyYWdEcm9wU2VydmljZS5hbGxvd2VkRHJvcFpvbmVzID0gW107XG4gICAgICAgIC8vIGNvbnNvbGUubG9nKCdvbmRyYWdlbmQuYWxsb3dlZERyb3Bab25lcycsIHRoaXMuX2RyYWdEcm9wU2VydmljZS5hbGxvd2VkRHJvcFpvbmVzKTtcbiAgICAgICAgdGhpcy5fb25EcmFnRW5kQ2FsbGJhY2soZXZlbnQpO1xuICAgIH1cblxuICAgIC8vKioqKiBEcm9wIENhbGxiYWNrcyAqKioqLy9cbiAgICBfb25EcmFnRW50ZXJDYWxsYmFjayhldmVudDogRXZlbnQpIHsgfVxuICAgIF9vbkRyYWdPdmVyQ2FsbGJhY2soZXZlbnQ6IEV2ZW50KSB7IH1cbiAgICBfb25EcmFnTGVhdmVDYWxsYmFjayhldmVudDogRXZlbnQpIHsgfVxuICAgIF9vbkRyb3BDYWxsYmFjayhldmVudDogRXZlbnQpIHsgfVxuXG4gICAgLy8qKioqIERyYWcgQ2FsbGJhY2tzICoqKiovL1xuICAgIF9vbkRyYWdTdGFydENhbGxiYWNrKGV2ZW50OiBFdmVudCkgeyB9XG4gICAgX29uRHJhZ0VuZENhbGxiYWNrKGV2ZW50OiBFdmVudCkgeyB9XG59XG5cbmV4cG9ydCBjbGFzcyBBYnN0cmFjdEhhbmRsZUNvbXBvbmVudCB7XG4gICAgX2VsZW06IEhUTUxFbGVtZW50O1xuICAgIGNvbnN0cnVjdG9yKGVsZW1SZWY6IEVsZW1lbnRSZWYsIHB1YmxpYyBfZHJhZ0Ryb3BTZXJ2aWNlOiBEcmFnRHJvcFNlcnZpY2UsIHB1YmxpYyBfY29uZmlnOiBEcmFnRHJvcENvbmZpZyxcbiAgICAgICAgcHJpdmF0ZSBfQ29tcG9uZW50OiBBYnN0cmFjdENvbXBvbmVudCwgcHJpdmF0ZSBfY2RyOiBDaGFuZ2VEZXRlY3RvclJlZikge1xuICAgICAgICB0aGlzLl9lbGVtID0gZWxlbVJlZi5uYXRpdmVFbGVtZW50O1xuICAgICAgICB0aGlzLl9Db21wb25lbnQuc2V0RHJhZ0hhbmRsZSh0aGlzLl9lbGVtKTtcbiAgICB9XG59XG5cblxuXG4vLyBXRUJQQUNLIEZPT1RFUiAvL1xuLy8gLi9+L3RzbGludC1sb2FkZXIhLi9zcmMvYWJzdHJhY3QuY29tcG9uZW50LnRzIiwiLy8gQ29weXJpZ2h0IChDKSAyMDE2IFNlcmdleSBBa29wa29raHlhbnRzXG4vLyBUaGlzIHByb2plY3QgaXMgbGljZW5zZWQgdW5kZXIgdGhlIHRlcm1zIG9mIHRoZSBNSVQgbGljZW5zZS5cbi8vIGh0dHBzOi8vZ2l0aHViLmNvbS9ha3NlcmcvbmcyLWRuZFxuXG5pbXBvcnQge0NoYW5nZURldGVjdG9yUmVmfSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCB7RGlyZWN0aXZlLCBJbnB1dCwgT3V0cHV0LCBFdmVudEVtaXR0ZXIsIEVsZW1lbnRSZWZ9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuXG5pbXBvcnQge0Fic3RyYWN0Q29tcG9uZW50LCBBYnN0cmFjdEhhbmRsZUNvbXBvbmVudH0gZnJvbSAnLi9hYnN0cmFjdC5jb21wb25lbnQnO1xuaW1wb3J0IHtEcmFnRHJvcENvbmZpZywgRHJhZ0ltYWdlfSBmcm9tICcuL2RuZC5jb25maWcnO1xuaW1wb3J0IHtEcmFnRHJvcFNlcnZpY2UsIERyYWdEcm9wRGF0YX0gZnJvbSAnLi9kbmQuc2VydmljZSc7XG5cbkBEaXJlY3RpdmUoeyBzZWxlY3RvcjogJ1tkbmQtZHJhZ2dhYmxlXScgfSlcbmV4cG9ydCBjbGFzcyBEcmFnZ2FibGVDb21wb25lbnQgZXh0ZW5kcyBBYnN0cmFjdENvbXBvbmVudCB7XG5cbiAgICBASW5wdXQoXCJkcmFnRW5hYmxlZFwiKSBzZXQgZHJhZ2dhYmxlKHZhbHVlOmJvb2xlYW4pIHtcbiAgICAgICAgdGhpcy5kcmFnRW5hYmxlZCA9ICEhdmFsdWU7XG4gICAgfVxuXG4gICAgLyoqXG4gICAgICogQ2FsbGJhY2sgZnVuY3Rpb24gY2FsbGVkIHdoZW4gdGhlIGRyYWcgYWN0aW9ucyBoYXBwZW5lZC5cbiAgICAgKi9cbiAgICBAT3V0cHV0KCkgb25EcmFnU3RhcnQ6IEV2ZW50RW1pdHRlcjxEcmFnRHJvcERhdGE+ID0gbmV3IEV2ZW50RW1pdHRlcjxEcmFnRHJvcERhdGE+KCk7XG4gICAgQE91dHB1dCgpIG9uRHJhZ0VuZDogRXZlbnRFbWl0dGVyPERyYWdEcm9wRGF0YT4gPSBuZXcgRXZlbnRFbWl0dGVyPERyYWdEcm9wRGF0YT4oKTtcblxuICAgIC8qKlxuICAgICAqIFRoZSBkYXRhIHRoYXQgaGFzIHRvIGJlIGRyYWdnZWQuIEl0IGNhbiBiZSBhbnkgSlMgb2JqZWN0XG4gICAgICovXG4gICAgQElucHV0KCkgZHJhZ0RhdGE6IGFueTtcblxuICAgIC8qKlxuICAgICAqIENhbGxiYWNrIGZ1bmN0aW9uIGNhbGxlZCB3aGVuIHRoZSBkcmFnIGFjdGlvbiBlbmRzIHdpdGggYSB2YWxpZCBkcm9wIGFjdGlvbi5cbiAgICAgKiBJdCBpcyBhY3RpdmF0ZWQgYWZ0ZXIgdGhlIG9uLWRyb3Atc3VjY2VzcyBjYWxsYmFja1xuICAgICAqL1xuICAgIEBPdXRwdXQoXCJvbkRyYWdTdWNjZXNzXCIpIG9uRHJhZ1N1Y2Nlc3NDYWxsYmFjazogRXZlbnRFbWl0dGVyPGFueT4gPSBuZXcgRXZlbnRFbWl0dGVyPGFueT4oKTtcblxuICAgIEBJbnB1dChcImRyb3Bab25lc1wiKSBzZXQgZHJvcHpvbmVzKHZhbHVlOkFycmF5PHN0cmluZz4pIHtcbiAgICAgICAgdGhpcy5kcm9wWm9uZXMgPSB2YWx1ZTtcbiAgICB9XG5cbiAgICAvKipcbiAgICAgKiBEcmFnIGFsbG93ZWQgZWZmZWN0XG4gICAgICovXG4gICAgQElucHV0KFwiZWZmZWN0QWxsb3dlZFwiKSBzZXQgZWZmZWN0YWxsb3dlZCh2YWx1ZTogc3RyaW5nKSB7XG4gICAgICAgIHRoaXMuZWZmZWN0QWxsb3dlZCA9IHZhbHVlO1xuICAgIH1cblxuICAgIC8qKlxuICAgICAqIERyYWcgZWZmZWN0IGN1cnNvclxuICAgICAqL1xuICAgIEBJbnB1dChcImVmZmVjdEN1cnNvclwiKSBzZXQgZWZmZWN0Y3Vyc29yKHZhbHVlOiBzdHJpbmcpIHtcbiAgICAgICAgdGhpcy5lZmZlY3RDdXJzb3IgPSB2YWx1ZTtcbiAgICB9XG5cbiAgICAvKipcbiAgICAgKiBIZXJlIGlzIHRoZSBwcm9wZXJ0eSBkcmFnSW1hZ2UgeW91IGNhbiB1c2U6XG4gICAgICogLSBUaGUgc3RyaW5nIHZhbHVlIGFzIHVybCB0byB0aGUgaW1hZ2VcbiAgICAgKiAgIDxkaXYgY2xhc3M9XCJwYW5lbCBwYW5lbC1kZWZhdWx0XCJcbiAgICAgKiAgICAgICAgZG5kLWRyYWdnYWJsZSBbZHJhZ0VuYWJsZWRdPVwidHJ1ZVwiXG4gICAgICogICAgICAgIFtkcmFnSW1hZ2VdPVwiL2ltYWdlcy9zaW1wbGVyLnBuZ1wiPlxuICAgICAqIC4uLlxuICAgICAqIC0gVGhlIERyYWdJbWFnZSB2YWx1ZSB3aXRoIEltYWdlIGFuZCBvZmZzZXQgYnkgeCBhbmQgeTpcbiAgICAgKiAgIGxldCBteURyYWdJbWFnZTogRHJhZ0ltYWdlID0gbmV3IERyYWdJbWFnZShcIi9pbWFnZXMvc2ltcGxlcjEucG5nXCIsIDAsIDApO1xuICAgICAqIC4uLlxuICAgICAqICAgPGRpdiBjbGFzcz1cInBhbmVsIHBhbmVsLWRlZmF1bHRcIlxuICAgICAqICAgICAgICBkbmQtZHJhZ2dhYmxlIFtkcmFnRW5hYmxlZF09XCJ0cnVlXCJcbiAgICAgKiAgICAgICAgW2RyYWdJbWFnZV09XCJteURyYWdJbWFnZVwiPlxuICAgICAqIC4uLlxuICAgICAqIC0gVGhlIGN1c3RvbSBmdW5jdGlvbiB0byByZXR1cm4gdGhlIHZhbHVlIG9mIGRyYWdJbWFnZSBwcm9ncmFtbWF0aWNhbGx5OlxuICAgICAqICAgPGRpdiBjbGFzcz1cInBhbmVsIHBhbmVsLWRlZmF1bHRcIlxuICAgICAqICAgICAgICBkbmQtZHJhZ2dhYmxlIFtkcmFnRW5hYmxlZF09XCJ0cnVlXCJcbiAgICAgKiAgICAgICAgW2RyYWdJbWFnZV09XCJnZXREcmFnSW1hZ2Uoc29tZURhdGEpXCI+XG4gICAgICogLi4uXG4gICAgICogICBnZXREcmFnSW1hZ2UodmFsdWU6YW55KTogc3RyaW5nIHtcbiAgICAgKiAgICAgcmV0dXJuIHZhbHVlID8gXCIvaW1hZ2VzL3NpbXBsZXIxLnBuZ1wiIDogXCIvaW1hZ2VzL3NpbXBsZXIyLnBuZ1wiXG4gICAgICogICB9XG4gICAgICovXG4gICAgQElucHV0KCkgZHJhZ0ltYWdlOiBzdHJpbmcgfCBEcmFnSW1hZ2UgfCBGdW5jdGlvbjtcblxuICAgIFxuICAgIEBJbnB1dCgpIGNsb25lSXRlbTogYm9vbGVhbjtcblxuICAgIGNvbnN0cnVjdG9yKGVsZW1SZWY6IEVsZW1lbnRSZWYsIGRyYWdEcm9wU2VydmljZTogRHJhZ0Ryb3BTZXJ2aWNlLCBjb25maWc6RHJhZ0Ryb3BDb25maWcsXG4gICAgICAgIGNkcjpDaGFuZ2VEZXRlY3RvclJlZikge1xuXG4gICAgICAgIHN1cGVyKGVsZW1SZWYsIGRyYWdEcm9wU2VydmljZSwgY29uZmlnLCBjZHIpO1xuICAgICAgICB0aGlzLl9kZWZhdWx0Q3Vyc29yID0gdGhpcy5fZWxlbS5zdHlsZS5jdXJzb3I7XG4gICAgICAgIHRoaXMuZHJhZ0VuYWJsZWQgPSB0cnVlO1xuICAgIH1cblxuICAgIF9vbkRyYWdTdGFydENhbGxiYWNrKGV2ZW50OiBNb3VzZUV2ZW50KSB7XG4gICAgICAgIHRoaXMuX2RyYWdEcm9wU2VydmljZS5pc0RyYWdnZWQgPSB0cnVlO1xuICAgICAgICB0aGlzLl9kcmFnRHJvcFNlcnZpY2UuZHJhZ0RhdGEgPSB0aGlzLmRyYWdEYXRhO1xuICAgICAgICB0aGlzLl9kcmFnRHJvcFNlcnZpY2Uub25EcmFnU3VjY2Vzc0NhbGxiYWNrID0gdGhpcy5vbkRyYWdTdWNjZXNzQ2FsbGJhY2s7XG4gICAgICAgIHRoaXMuX2VsZW0uY2xhc3NMaXN0LmFkZCh0aGlzLl9jb25maWcub25EcmFnU3RhcnRDbGFzcyk7XG4gICAgICAgIC8vXG4gICAgICAgIHRoaXMub25EcmFnU3RhcnQuZW1pdCh7ZHJhZ0RhdGE6IHRoaXMuZHJhZ0RhdGEsIG1vdXNlRXZlbnQ6IGV2ZW50fSk7XG4gICAgfVxuXG4gICAgX29uRHJhZ0VuZENhbGxiYWNrKGV2ZW50OiBNb3VzZUV2ZW50KSB7XG4gICAgICAgIHRoaXMuX2RyYWdEcm9wU2VydmljZS5pc0RyYWdnZWQgPSBmYWxzZTtcbiAgICAgICAgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhID0gbnVsbDtcbiAgICAgICAgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLm9uRHJhZ1N1Y2Nlc3NDYWxsYmFjayA9IG51bGw7XG4gICAgICAgIHRoaXMuX2VsZW0uY2xhc3NMaXN0LnJlbW92ZSh0aGlzLl9jb25maWcub25EcmFnU3RhcnRDbGFzcyk7XG4gICAgICAgIC8vXG4gICAgICAgIHRoaXMub25EcmFnRW5kLmVtaXQoe2RyYWdEYXRhOiB0aGlzLmRyYWdEYXRhLCBtb3VzZUV2ZW50OiBldmVudH0pO1xuICAgIH1cbn1cblxuXG5ARGlyZWN0aXZlKHsgc2VsZWN0b3I6ICdbZG5kLWRyYWdnYWJsZS1oYW5kbGVdJyB9KVxuZXhwb3J0IGNsYXNzIERyYWdnYWJsZUhhbmRsZUNvbXBvbmVudCBleHRlbmRzIEFic3RyYWN0SGFuZGxlQ29tcG9uZW50IHtcbiAgICBjb25zdHJ1Y3RvcihlbGVtUmVmOiBFbGVtZW50UmVmLCBkcmFnRHJvcFNlcnZpY2U6IERyYWdEcm9wU2VydmljZSwgY29uZmlnOkRyYWdEcm9wQ29uZmlnLCBfQ29tcG9uZW50OiBEcmFnZ2FibGVDb21wb25lbnQsXG4gICAgICAgIGNkcjpDaGFuZ2VEZXRlY3RvclJlZikge1xuXG4gICAgICAgIHN1cGVyKGVsZW1SZWYsIGRyYWdEcm9wU2VydmljZSwgY29uZmlnLCBfQ29tcG9uZW50LCBjZHIpO1xuICAgIH1cbn1cblxuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL34vdHNsaW50LWxvYWRlciEuL3NyYy9kcmFnZ2FibGUuY29tcG9uZW50LnRzIiwiLy8gQ29weXJpZ2h0IChDKSAyMDE2IFNlcmdleSBBa29wa29raHlhbnRzXG4vLyBUaGlzIHByb2plY3QgaXMgbGljZW5zZWQgdW5kZXIgdGhlIHRlcm1zIG9mIHRoZSBNSVQgbGljZW5zZS5cbi8vIGh0dHBzOi8vZ2l0aHViLmNvbS9ha3NlcmcvbmcyLWRuZFxuXG5pbXBvcnQge0NoYW5nZURldGVjdG9yUmVmfSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCB7RGlyZWN0aXZlLCBJbnB1dCwgT3V0cHV0LCBFdmVudEVtaXR0ZXIsIEVsZW1lbnRSZWZ9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuXG5pbXBvcnQge0Fic3RyYWN0Q29tcG9uZW50fSBmcm9tICcuL2Fic3RyYWN0LmNvbXBvbmVudCc7XG5pbXBvcnQge0RyYWdEcm9wQ29uZmlnfSBmcm9tICcuL2RuZC5jb25maWcnO1xuaW1wb3J0IHtEcmFnRHJvcFNlcnZpY2UsIERyYWdEcm9wRGF0YX0gZnJvbSAnLi9kbmQuc2VydmljZSc7XG5cbkBEaXJlY3RpdmUoeyBzZWxlY3RvcjogJ1tkbmQtZHJvcHBhYmxlXScgfSlcbmV4cG9ydCBjbGFzcyBEcm9wcGFibGVDb21wb25lbnQgZXh0ZW5kcyBBYnN0cmFjdENvbXBvbmVudCB7XG5cbiAgICBASW5wdXQoXCJkcm9wRW5hYmxlZFwiKSBzZXQgZHJvcHBhYmxlKHZhbHVlOmJvb2xlYW4pIHtcbiAgICAgICAgdGhpcy5kcm9wRW5hYmxlZCA9ICEhdmFsdWU7XG4gICAgfVxuXG4gICAgLyoqXG4gICAgICogQ2FsbGJhY2sgZnVuY3Rpb24gY2FsbGVkIHdoZW4gdGhlIGRyb3AgYWN0aW9uIGNvbXBsZXRlcyBjb3JyZWN0bHkuXG4gICAgICogSXQgaXMgYWN0aXZhdGVkIGJlZm9yZSB0aGUgb24tZHJhZy1zdWNjZXNzIGNhbGxiYWNrLlxuICAgICAqL1xuICAgIEBPdXRwdXQoKSBvbkRyb3BTdWNjZXNzOiBFdmVudEVtaXR0ZXI8RHJhZ0Ryb3BEYXRhPiA9IG5ldyBFdmVudEVtaXR0ZXI8RHJhZ0Ryb3BEYXRhPigpO1xuICAgIEBPdXRwdXQoKSBvbkRyYWdFbnRlcjogRXZlbnRFbWl0dGVyPERyYWdEcm9wRGF0YT4gPSBuZXcgRXZlbnRFbWl0dGVyPERyYWdEcm9wRGF0YT4oKTtcbiAgICBAT3V0cHV0KCkgb25EcmFnT3ZlcjogRXZlbnRFbWl0dGVyPERyYWdEcm9wRGF0YT4gPSBuZXcgRXZlbnRFbWl0dGVyPERyYWdEcm9wRGF0YT4oKTtcbiAgICBAT3V0cHV0KCkgb25EcmFnTGVhdmU6IEV2ZW50RW1pdHRlcjxEcmFnRHJvcERhdGE+ID0gbmV3IEV2ZW50RW1pdHRlcjxEcmFnRHJvcERhdGE+KCk7XG5cbiAgICBASW5wdXQoXCJhbGxvd0Ryb3BcIikgc2V0IGFsbG93ZHJvcCh2YWx1ZTogKGRyb3BEYXRhOiBhbnkpID0+IGJvb2xlYW4pIHtcbiAgICAgICAgdGhpcy5hbGxvd0Ryb3AgPSB2YWx1ZTtcbiAgICB9XG5cbiAgICBASW5wdXQoXCJkcm9wWm9uZXNcIikgc2V0IGRyb3B6b25lcyh2YWx1ZTpBcnJheTxzdHJpbmc+KSB7XG4gICAgICAgIHRoaXMuZHJvcFpvbmVzID0gdmFsdWU7XG4gICAgfVxuXG4gICAgLyoqXG4gICAgICogRHJhZyBhbGxvd2VkIGVmZmVjdFxuICAgICAqL1xuICAgIEBJbnB1dChcImVmZmVjdEFsbG93ZWRcIikgc2V0IGVmZmVjdGFsbG93ZWQodmFsdWU6IHN0cmluZykge1xuICAgICAgICB0aGlzLmVmZmVjdEFsbG93ZWQgPSB2YWx1ZTtcbiAgICB9XG5cbiAgICAvKipcbiAgICAgKiBEcmFnIGVmZmVjdCBjdXJzb3JcbiAgICAgKi9cbiAgICBASW5wdXQoXCJlZmZlY3RDdXJzb3JcIikgc2V0IGVmZmVjdGN1cnNvcih2YWx1ZTogc3RyaW5nKSB7XG4gICAgICAgIHRoaXMuZWZmZWN0Q3Vyc29yID0gdmFsdWU7XG4gICAgfVxuXG4gICAgY29uc3RydWN0b3IoZWxlbVJlZjogRWxlbWVudFJlZiwgZHJhZ0Ryb3BTZXJ2aWNlOiBEcmFnRHJvcFNlcnZpY2UsIGNvbmZpZzpEcmFnRHJvcENvbmZpZyxcbiAgICAgICAgY2RyOkNoYW5nZURldGVjdG9yUmVmKSB7XG5cbiAgICAgICAgc3VwZXIoZWxlbVJlZiwgZHJhZ0Ryb3BTZXJ2aWNlLCBjb25maWcsIGNkcik7XG5cbiAgICAgICAgdGhpcy5kcm9wRW5hYmxlZCA9IHRydWU7XG4gICAgfVxuXG4gICAgX29uRHJhZ0VudGVyQ2FsbGJhY2soZXZlbnQ6IE1vdXNlRXZlbnQpIHtcbiAgICAgICAgaWYgKHRoaXMuX2RyYWdEcm9wU2VydmljZS5pc0RyYWdnZWQpIHtcbiAgICAgICAgICAgIHRoaXMuX2VsZW0uY2xhc3NMaXN0LmFkZCh0aGlzLl9jb25maWcub25EcmFnRW50ZXJDbGFzcyk7XG4gICAgICAgICAgICB0aGlzLm9uRHJhZ0VudGVyLmVtaXQoe2RyYWdEYXRhOiB0aGlzLl9kcmFnRHJvcFNlcnZpY2UuZHJhZ0RhdGEsIG1vdXNlRXZlbnQ6IGV2ZW50fSk7XG4gICAgICAgIH1cbiAgICB9XG5cbiAgICBfb25EcmFnT3ZlckNhbGxiYWNrIChldmVudDogTW91c2VFdmVudCkge1xuICAgICAgICBpZiAodGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmlzRHJhZ2dlZCkge1xuICAgICAgICAgICAgdGhpcy5fZWxlbS5jbGFzc0xpc3QuYWRkKHRoaXMuX2NvbmZpZy5vbkRyYWdPdmVyQ2xhc3MpO1xuICAgICAgICAgICAgdGhpcy5vbkRyYWdPdmVyLmVtaXQoe2RyYWdEYXRhOiB0aGlzLl9kcmFnRHJvcFNlcnZpY2UuZHJhZ0RhdGEsIG1vdXNlRXZlbnQ6IGV2ZW50fSk7XG4gICAgICAgIH1cbiAgICB9O1xuXG4gICAgX29uRHJhZ0xlYXZlQ2FsbGJhY2sgKGV2ZW50OiBNb3VzZUV2ZW50KSB7XG4gICAgICAgIGlmICh0aGlzLl9kcmFnRHJvcFNlcnZpY2UuaXNEcmFnZ2VkKSB7XG4gICAgICAgICAgICB0aGlzLl9lbGVtLmNsYXNzTGlzdC5yZW1vdmUodGhpcy5fY29uZmlnLm9uRHJhZ092ZXJDbGFzcyk7XG4gICAgICAgICAgICB0aGlzLl9lbGVtLmNsYXNzTGlzdC5yZW1vdmUodGhpcy5fY29uZmlnLm9uRHJhZ0VudGVyQ2xhc3MpO1xuICAgICAgICAgICAgdGhpcy5vbkRyYWdMZWF2ZS5lbWl0KHtkcmFnRGF0YTogdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhLCBtb3VzZUV2ZW50OiBldmVudH0pO1xuICAgICAgICB9XG4gICAgfTtcblxuICAgIF9vbkRyb3BDYWxsYmFjayAoZXZlbnQ6IE1vdXNlRXZlbnQpIHtcbiAgICAgICAgbGV0IGRhdGFUcmFuc2ZlciA9IChldmVudCBhcyBhbnkpLmRhdGFUcmFuc2ZlcjtcbiAgICAgICAgaWYgKHRoaXMuX2RyYWdEcm9wU2VydmljZS5pc0RyYWdnZWQgfHwgKGRhdGFUcmFuc2ZlciAmJiBkYXRhVHJhbnNmZXIuZmlsZXMpKSB7XG4gICAgICAgICAgICB0aGlzLm9uRHJvcFN1Y2Nlc3MuZW1pdCh7ZHJhZ0RhdGE6IHRoaXMuX2RyYWdEcm9wU2VydmljZS5kcmFnRGF0YSwgbW91c2VFdmVudDogZXZlbnR9KTtcbiAgICAgICAgICAgIGlmICh0aGlzLl9kcmFnRHJvcFNlcnZpY2Uub25EcmFnU3VjY2Vzc0NhbGxiYWNrKSB7XG4gICAgICAgICAgICAgICAgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLm9uRHJhZ1N1Y2Nlc3NDYWxsYmFjay5lbWl0KHtkcmFnRGF0YTogdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhLCBtb3VzZUV2ZW50OiBldmVudH0pO1xuICAgICAgICAgICAgfVxuICAgICAgICAgICAgdGhpcy5fZWxlbS5jbGFzc0xpc3QucmVtb3ZlKHRoaXMuX2NvbmZpZy5vbkRyYWdPdmVyQ2xhc3MpO1xuICAgICAgICAgICAgdGhpcy5fZWxlbS5jbGFzc0xpc3QucmVtb3ZlKHRoaXMuX2NvbmZpZy5vbkRyYWdFbnRlckNsYXNzKTtcbiAgICAgICAgfVxuICAgIH1cbn1cblxuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL34vdHNsaW50LWxvYWRlciEuL3NyYy9kcm9wcGFibGUuY29tcG9uZW50LnRzIiwiLy8gQ29weXJpZ2h0IChDKSAyMDE2IFNlcmdleSBBa29wa29raHlhbnRzXG4vLyBUaGlzIHByb2plY3QgaXMgbGljZW5zZWQgdW5kZXIgdGhlIHRlcm1zIG9mIHRoZSBNSVQgbGljZW5zZS5cbi8vIGh0dHBzOi8vZ2l0aHViLmNvbS9ha3NlcmcvbmcyLWRuZFxuXG5pbXBvcnQge0NoYW5nZURldGVjdG9yUmVmfSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCB7RGlyZWN0aXZlLCBJbnB1dCwgT3V0cHV0LCBFdmVudEVtaXR0ZXIsIEVsZW1lbnRSZWZ9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuXG5pbXBvcnQge0Fic3RyYWN0Q29tcG9uZW50LCBBYnN0cmFjdEhhbmRsZUNvbXBvbmVudH0gZnJvbSAnLi9hYnN0cmFjdC5jb21wb25lbnQnO1xuaW1wb3J0IHtEcmFnRHJvcENvbmZpZ30gZnJvbSAnLi9kbmQuY29uZmlnJztcbmltcG9ydCB7RHJhZ0Ryb3BTZXJ2aWNlLCBEcmFnRHJvcFNvcnRhYmxlU2VydmljZX0gZnJvbSAnLi9kbmQuc2VydmljZSc7XG5cbkBEaXJlY3RpdmUoeyBzZWxlY3RvcjogJ1tkbmQtc29ydGFibGUtY29udGFpbmVyXScgfSlcbmV4cG9ydCBjbGFzcyBTb3J0YWJsZUNvbnRhaW5lciBleHRlbmRzIEFic3RyYWN0Q29tcG9uZW50IHtcblxuICAgIEBJbnB1dChcImRyYWdFbmFibGVkXCIpIHNldCBkcmFnZ2FibGUodmFsdWU6Ym9vbGVhbikge1xuICAgICAgICB0aGlzLmRyYWdFbmFibGVkID0gISF2YWx1ZTtcbiAgICB9XG5cbiAgICBwcml2YXRlIF9zb3J0YWJsZURhdGE6IEFycmF5PGFueT4gPSBbXTtcblxuICAgIEBJbnB1dCgpIHNldCBzb3J0YWJsZURhdGEoc29ydGFibGVEYXRhOiBBcnJheTxhbnk+KSB7XG4gICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YSA9IHNvcnRhYmxlRGF0YTtcbiAgICAgICAgLy9cbiAgICAgICAgdGhpcy5kcm9wRW5hYmxlZCA9ICEhdGhpcy5fc29ydGFibGVEYXRhO1xuICAgICAgICAvLyBjb25zb2xlLmxvZyhcImNvbGxlY3Rpb24gaXMgY2hhbmdlZCwgZHJvcCBlbmFibGVkOiBcIiArIHRoaXMuZHJvcEVuYWJsZWQpO1xuICAgIH1cbiAgICBnZXQgc29ydGFibGVEYXRhKCk6IEFycmF5PGFueT4ge1xuICAgICAgICByZXR1cm4gdGhpcy5fc29ydGFibGVEYXRhO1xuICAgIH1cblxuICAgIEBJbnB1dChcImRyb3Bab25lc1wiKSBzZXQgZHJvcHpvbmVzKHZhbHVlOkFycmF5PHN0cmluZz4pIHtcbiAgICAgICAgdGhpcy5kcm9wWm9uZXMgPSB2YWx1ZTtcbiAgICB9XG5cbiAgICBjb25zdHJ1Y3RvcihlbGVtUmVmOiBFbGVtZW50UmVmLCBkcmFnRHJvcFNlcnZpY2U6IERyYWdEcm9wU2VydmljZSwgY29uZmlnOkRyYWdEcm9wQ29uZmlnLCBjZHI6Q2hhbmdlRGV0ZWN0b3JSZWYsXG4gICAgICAgIHByaXZhdGUgX3NvcnRhYmxlRGF0YVNlcnZpY2U6IERyYWdEcm9wU29ydGFibGVTZXJ2aWNlKSB7XG5cbiAgICAgICAgc3VwZXIoZWxlbVJlZiwgZHJhZ0Ryb3BTZXJ2aWNlLCBjb25maWcsIGNkcik7XG4gICAgICAgIHRoaXMuZHJhZ0VuYWJsZWQgPSBmYWxzZTtcbiAgICB9XG5cbiAgICBfb25EcmFnRW50ZXJDYWxsYmFjayhldmVudDogRXZlbnQpIHtcbiAgICAgICAgaWYgKHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaXNEcmFnZ2VkKSB7XG4gICAgICAgICAgICBsZXQgaXRlbTphbnkgPSB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLnNvcnRhYmxlQ29udGFpbmVyLl9zb3J0YWJsZURhdGFbdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5pbmRleF07XG4gICAgICAgICAgICAvLyBDaGVjayBkb2VzIGVsZW1lbnQgZXhpc3QgaW4gc29ydGFibGVEYXRhIG9mIHRoaXMgQ29udGFpbmVyXG4gICAgICAgICAgICBpZiAodGhpcy5fc29ydGFibGVEYXRhLmluZGV4T2YoaXRlbSkgPT09IC0xKSB7XG4gICAgICAgICAgICAgICAgLy8gTGV0J3MgYWRkIGl0XG4gICAgICAgICAgICAgICAgLy8gY29uc29sZS5sb2coJ0NvbnRhaW5lci5fb25EcmFnRW50ZXJDYWxsYmFjay4gZHJhZyBub2RlIFsnICsgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5pbmRleC50b1N0cmluZygpICsgJ10gb3ZlciBwYXJlbnQgbm9kZScpO1xuICAgICAgICAgICAgICAgIC8vIFJlbW92ZSBpdGVtIGZyb20gcHJldmlvdXNlIGxpc3RcbiAgICAgICAgICAgICAgICB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLnNvcnRhYmxlQ29udGFpbmVyLl9zb3J0YWJsZURhdGEuc3BsaWNlKHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaW5kZXgsIDEpO1xuICAgICAgICAgICAgICAgIGlmICh0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLnNvcnRhYmxlQ29udGFpbmVyLl9zb3J0YWJsZURhdGEubGVuZ3RoID09PSAwKSB7XG4gICAgICAgICAgICAgICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2Uuc29ydGFibGVDb250YWluZXIuZHJvcEVuYWJsZWQgPSB0cnVlO1xuICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICAvLyBBZGQgaXRlbSB0byBuZXcgbGlzdFxuICAgICAgICAgICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YS51bnNoaWZ0KGl0ZW0pO1xuICAgICAgICAgICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2Uuc29ydGFibGVDb250YWluZXIgPSB0aGlzO1xuICAgICAgICAgICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaW5kZXggPSAwO1xuICAgICAgICAgICAgfVxuICAgICAgICAgICAgLy8gUmVmcmVzaCBjaGFuZ2VzIGluIHByb3BlcnRpZXMgb2YgY29udGFpbmVyIGNvbXBvbmVudFxuICAgICAgICAgICAgdGhpcy5kZXRlY3RDaGFuZ2VzKCk7XG4gICAgICAgIH1cbiAgICB9XG59XG5cbkBEaXJlY3RpdmUoeyBzZWxlY3RvcjogJ1tkbmQtc29ydGFibGVdJyB9KVxuZXhwb3J0IGNsYXNzIFNvcnRhYmxlQ29tcG9uZW50IGV4dGVuZHMgQWJzdHJhY3RDb21wb25lbnQge1xuXG4gICAgQElucHV0KCdzb3J0YWJsZUluZGV4JykgaW5kZXg6IG51bWJlcjtcblxuICAgIEBJbnB1dChcImRyYWdFbmFibGVkXCIpIHNldCBkcmFnZ2FibGUodmFsdWU6Ym9vbGVhbikge1xuICAgICAgICB0aGlzLmRyYWdFbmFibGVkID0gISF2YWx1ZTtcbiAgICB9XG5cbiAgICBASW5wdXQoXCJkcm9wRW5hYmxlZFwiKSBzZXQgZHJvcHBhYmxlKHZhbHVlOmJvb2xlYW4pIHtcbiAgICAgICAgdGhpcy5kcm9wRW5hYmxlZCA9ICEhdmFsdWU7XG4gICAgfVxuXG4gICAgLyoqXG4gICAgICogVGhlIGRhdGEgdGhhdCBoYXMgdG8gYmUgZHJhZ2dlZC4gSXQgY2FuIGJlIGFueSBKUyBvYmplY3RcbiAgICAgKi9cbiAgICBASW5wdXQoKSBkcmFnRGF0YTogYW55O1xuXG4gICAgLyoqXG4gICAgICogRHJhZyBhbGxvd2VkIGVmZmVjdFxuICAgICAqL1xuICAgIEBJbnB1dChcImVmZmVjdEFsbG93ZWRcIikgc2V0IGVmZmVjdGFsbG93ZWQodmFsdWU6IHN0cmluZykge1xuICAgICAgICB0aGlzLmVmZmVjdEFsbG93ZWQgPSB2YWx1ZTtcbiAgICB9XG5cbiAgICAvKipcbiAgICAgKiBEcmFnIGVmZmVjdCBjdXJzb3JcbiAgICAgKi9cbiAgICBASW5wdXQoXCJlZmZlY3RDdXJzb3JcIikgc2V0IGVmZmVjdGN1cnNvcih2YWx1ZTogc3RyaW5nKSB7XG4gICAgICAgIHRoaXMuZWZmZWN0Q3Vyc29yID0gdmFsdWU7XG4gICAgfVxuXG4gICAgLyoqXG4gICAgICogQ2FsbGJhY2sgZnVuY3Rpb24gY2FsbGVkIHdoZW4gdGhlIGRyYWcgYWN0aW9uIGVuZHMgd2l0aCBhIHZhbGlkIGRyb3AgYWN0aW9uLlxuICAgICAqIEl0IGlzIGFjdGl2YXRlZCBhZnRlciB0aGUgb24tZHJvcC1zdWNjZXNzIGNhbGxiYWNrXG4gICAgICovXG4gICAgQE91dHB1dChcIm9uRHJhZ1N1Y2Nlc3NcIikgb25EcmFnU3VjY2Vzc0NhbGxiYWNrOiBFdmVudEVtaXR0ZXI8YW55PiA9IG5ldyBFdmVudEVtaXR0ZXI8YW55PigpO1xuXG4gICAgQE91dHB1dChcIm9uRHJhZ1N0YXJ0XCIpIG9uRHJhZ1N0YXJ0Q2FsbGJhY2s6IEV2ZW50RW1pdHRlcjxhbnk+ID0gbmV3IEV2ZW50RW1pdHRlcjxhbnk+KCk7XG4gICAgQE91dHB1dChcIm9uRHJhZ092ZXJcIikgb25EcmFnT3ZlckNhbGxiYWNrOiBFdmVudEVtaXR0ZXI8YW55PiA9IG5ldyBFdmVudEVtaXR0ZXI8YW55PigpO1xuICAgIEBPdXRwdXQoXCJvbkRyYWdFbmRcIikgb25EcmFnRW5kQ2FsbGJhY2s6IEV2ZW50RW1pdHRlcjxhbnk+ID0gbmV3IEV2ZW50RW1pdHRlcjxhbnk+KCk7XG4gICAgQE91dHB1dChcIm9uRHJvcFN1Y2Nlc3NcIikgb25Ecm9wU3VjY2Vzc0NhbGxiYWNrOiBFdmVudEVtaXR0ZXI8YW55PiA9IG5ldyBFdmVudEVtaXR0ZXI8YW55PigpO1xuXG4gICAgY29uc3RydWN0b3IoZWxlbVJlZjogRWxlbWVudFJlZiwgZHJhZ0Ryb3BTZXJ2aWNlOiBEcmFnRHJvcFNlcnZpY2UsIGNvbmZpZzpEcmFnRHJvcENvbmZpZyxcbiAgICAgICAgcHJpdmF0ZSBfc29ydGFibGVDb250YWluZXI6IFNvcnRhYmxlQ29udGFpbmVyLFxuICAgICAgICBwcml2YXRlIF9zb3J0YWJsZURhdGFTZXJ2aWNlOiBEcmFnRHJvcFNvcnRhYmxlU2VydmljZSxcbiAgICAgICAgY2RyOkNoYW5nZURldGVjdG9yUmVmKSB7XG4gICAgICAgIHN1cGVyKGVsZW1SZWYsIGRyYWdEcm9wU2VydmljZSwgY29uZmlnLCBjZHIpO1xuICAgICAgICB0aGlzLmRyb3Bab25lcyA9IHRoaXMuX3NvcnRhYmxlQ29udGFpbmVyLmRyb3Bab25lcztcbiAgICAgICAgdGhpcy5kcmFnRW5hYmxlZCA9IHRydWU7XG4gICAgICAgIHRoaXMuZHJvcEVuYWJsZWQgPSB0cnVlO1xuICAgIH1cblxuICAgIF9vbkRyYWdTdGFydENhbGxiYWNrKGV2ZW50OiBFdmVudCkge1xuICAgICAgICAvLyBjb25zb2xlLmxvZygnX29uRHJhZ1N0YXJ0Q2FsbGJhY2suIGRyYWdnaW5nIGVsZW0gd2l0aCBpbmRleCAnICsgdGhpcy5pbmRleCk7XG4gICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaXNEcmFnZ2VkID0gdHJ1ZTtcbiAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5zb3J0YWJsZUNvbnRhaW5lciA9IHRoaXMuX3NvcnRhYmxlQ29udGFpbmVyO1xuICAgICAgICB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLmluZGV4ID0gdGhpcy5pbmRleDtcbiAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5tYXJrU29ydGFibGUodGhpcy5fZWxlbSk7XG4gICAgICAgIC8vIEFkZCBkcmFnRGF0YVxuICAgICAgICB0aGlzLl9kcmFnRHJvcFNlcnZpY2UuaXNEcmFnZ2VkID0gdHJ1ZTtcbiAgICAgICAgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhID0gdGhpcy5kcmFnRGF0YTtcbiAgICAgICAgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLm9uRHJhZ1N1Y2Nlc3NDYWxsYmFjayA9IHRoaXMub25EcmFnU3VjY2Vzc0NhbGxiYWNrO1xuICAgICAgICAvL1xuICAgICAgICB0aGlzLm9uRHJhZ1N0YXJ0Q2FsbGJhY2suZW1pdCh0aGlzLl9kcmFnRHJvcFNlcnZpY2UuZHJhZ0RhdGEpO1xuICAgIH1cblxuICAgIF9vbkRyYWdPdmVyQ2FsbGJhY2soZXZlbnQ6IEV2ZW50KSB7XG4gICAgICAgIGlmICh0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLmlzRHJhZ2dlZCAmJiB0aGlzLl9lbGVtICE9PSB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLmVsZW0pIHtcbiAgICAgICAgICAgIC8vIGNvbnNvbGUubG9nKCdfb25EcmFnT3ZlckNhbGxiYWNrLiBkcmFnZ2luZyBlbGVtIHdpdGggaW5kZXggJyArIHRoaXMuaW5kZXgpO1xuICAgICAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5zb3J0YWJsZUNvbnRhaW5lciA9IHRoaXMuX3NvcnRhYmxlQ29udGFpbmVyO1xuICAgICAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5pbmRleCA9IHRoaXMuaW5kZXg7XG4gICAgICAgICAgICB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLm1hcmtTb3J0YWJsZSh0aGlzLl9lbGVtKTtcbiAgICAgICAgICAgIHRoaXMub25EcmFnT3ZlckNhbGxiYWNrLmVtaXQodGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhKTtcbiAgICAgICAgfVxuICAgIH1cblxuICAgIF9vbkRyYWdFbmRDYWxsYmFjayhldmVudDogRXZlbnQpIHtcbiAgICAgICAgLy8gY29uc29sZS5sb2coJ19vbkRyYWdFbmRDYWxsYmFjay4gZW5kIGRyYWdnaW5nIGVsZW0gd2l0aCBpbmRleCAnICsgdGhpcy5pbmRleCk7XG4gICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaXNEcmFnZ2VkID0gZmFsc2U7XG4gICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2Uuc29ydGFibGVDb250YWluZXIgPSBudWxsO1xuICAgICAgICB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLmluZGV4ID0gbnVsbDtcbiAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5tYXJrU29ydGFibGUobnVsbCk7XG4gICAgICAgIC8vIEFkZCBkcmFnR2F0YVxuICAgICAgICB0aGlzLl9kcmFnRHJvcFNlcnZpY2UuaXNEcmFnZ2VkID0gZmFsc2U7XG4gICAgICAgIHRoaXMuX2RyYWdEcm9wU2VydmljZS5kcmFnRGF0YSA9IG51bGw7XG4gICAgICAgIHRoaXMuX2RyYWdEcm9wU2VydmljZS5vbkRyYWdTdWNjZXNzQ2FsbGJhY2sgPSBudWxsO1xuICAgICAgICAvL1xuICAgICAgICB0aGlzLm9uRHJhZ0VuZENhbGxiYWNrLmVtaXQodGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhKTtcbiAgICB9XG5cbiAgICBfb25EcmFnRW50ZXJDYWxsYmFjayhldmVudDogRXZlbnQpIHtcbiAgICAgICAgaWYgKHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaXNEcmFnZ2VkKSB7XG4gICAgICAgICAgICB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLm1hcmtTb3J0YWJsZSh0aGlzLl9lbGVtKTtcbiAgICAgICAgICAgIGlmICgodGhpcy5pbmRleCAhPT0gdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5pbmRleCkgfHxcbiAgICAgICAgICAgICAgICAodGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5zb3J0YWJsZUNvbnRhaW5lci5zb3J0YWJsZURhdGEgIT09IHRoaXMuX3NvcnRhYmxlQ29udGFpbmVyLnNvcnRhYmxlRGF0YSkpIHtcbiAgICAgICAgICAgICAgICAvLyBjb25zb2xlLmxvZygnQ29tcG9uZW50Ll9vbkRyYWdFbnRlckNhbGxiYWNrLiBkcmFnIG5vZGUgWycgKyB0aGlzLmluZGV4ICsgJ10gb3ZlciBub2RlIFsnICsgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5pbmRleCArICddJyk7XG4gICAgICAgICAgICAgICAgLy8gR2V0IGl0ZW1cbiAgICAgICAgICAgICAgICBsZXQgaXRlbTphbnkgPSB0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLnNvcnRhYmxlQ29udGFpbmVyLnNvcnRhYmxlRGF0YVt0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLmluZGV4XTtcbiAgICAgICAgICAgICAgICAvLyBSZW1vdmUgaXRlbSBmcm9tIHByZXZpb3VzZSBsaXN0XG4gICAgICAgICAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5zb3J0YWJsZUNvbnRhaW5lci5zb3J0YWJsZURhdGEuc3BsaWNlKHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaW5kZXgsIDEpO1xuICAgICAgICAgICAgICAgIGlmICh0aGlzLl9zb3J0YWJsZURhdGFTZXJ2aWNlLnNvcnRhYmxlQ29udGFpbmVyLnNvcnRhYmxlRGF0YS5sZW5ndGggPT09IDApIHtcbiAgICAgICAgICAgICAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5zb3J0YWJsZUNvbnRhaW5lci5kcm9wRW5hYmxlZCA9IHRydWU7XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgIC8vIEFkZCBpdGVtIHRvIG5ldyBsaXN0XG4gICAgICAgICAgICAgICAgdGhpcy5fc29ydGFibGVDb250YWluZXIuc29ydGFibGVEYXRhLnNwbGljZSh0aGlzLmluZGV4LCAwLCBpdGVtKTtcbiAgICAgICAgICAgICAgICBpZiAodGhpcy5fc29ydGFibGVDb250YWluZXIuZHJvcEVuYWJsZWQpIHtcbiAgICAgICAgICAgICAgICAgICAgdGhpcy5fc29ydGFibGVDb250YWluZXIuZHJvcEVuYWJsZWQgPSBmYWxzZTtcbiAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgdGhpcy5fc29ydGFibGVEYXRhU2VydmljZS5zb3J0YWJsZUNvbnRhaW5lciA9IHRoaXMuX3NvcnRhYmxlQ29udGFpbmVyO1xuICAgICAgICAgICAgICAgIHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaW5kZXggPSB0aGlzLmluZGV4O1xuICAgICAgICAgICAgfVxuICAgICAgICB9XG4gICAgfVxuXG4gICAgX29uRHJvcENhbGxiYWNrIChldmVudDogRXZlbnQpIHtcbiAgICAgICAgaWYgKHRoaXMuX3NvcnRhYmxlRGF0YVNlcnZpY2UuaXNEcmFnZ2VkKSB7XG4gICAgICAgICAgICAvLyBjb25zb2xlLmxvZygnb25Ecm9wQ2FsbGJhY2sub25Ecm9wU3VjY2Vzc0NhbGxiYWNrLmRyYWdEYXRhJywgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhKTtcbiAgICAgICAgICAgIHRoaXMub25Ecm9wU3VjY2Vzc0NhbGxiYWNrLmVtaXQodGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLmRyYWdEYXRhKTtcbiAgICAgICAgICAgIGlmICh0aGlzLl9kcmFnRHJvcFNlcnZpY2Uub25EcmFnU3VjY2Vzc0NhbGxiYWNrKSB7XG4gICAgICAgICAgICAgICAgLy8gY29uc29sZS5sb2coJ29uRHJvcENhbGxiYWNrLm9uRHJhZ1N1Y2Nlc3NDYWxsYmFjay5kcmFnRGF0YScsIHRoaXMuX2RyYWdEcm9wU2VydmljZS5kcmFnRGF0YSk7XG4gICAgICAgICAgICAgICAgdGhpcy5fZHJhZ0Ryb3BTZXJ2aWNlLm9uRHJhZ1N1Y2Nlc3NDYWxsYmFjay5lbWl0KHRoaXMuX2RyYWdEcm9wU2VydmljZS5kcmFnRGF0YSk7XG4gICAgICAgICAgICB9XG4gICAgICAgICAgICAvLyBSZWZyZXNoIGNoYW5nZXMgaW4gcHJvcGVydGllcyBvZiBjb250YWluZXIgY29tcG9uZW50XG4gICAgICAgICAgICB0aGlzLl9zb3J0YWJsZUNvbnRhaW5lci5kZXRlY3RDaGFuZ2VzKCk7XG4gICAgICAgIH1cbiAgICB9XG59XG5cbkBEaXJlY3RpdmUoeyBzZWxlY3RvcjogJ1tkbmQtc29ydGFibGUtaGFuZGxlXScgfSlcbmV4cG9ydCBjbGFzcyBTb3J0YWJsZUhhbmRsZUNvbXBvbmVudCBleHRlbmRzIEFic3RyYWN0SGFuZGxlQ29tcG9uZW50IHtcbiAgICBjb25zdHJ1Y3RvcihlbGVtUmVmOiBFbGVtZW50UmVmLCBkcmFnRHJvcFNlcnZpY2U6IERyYWdEcm9wU2VydmljZSwgY29uZmlnOkRyYWdEcm9wQ29uZmlnLCBfQ29tcG9uZW50OiBTb3J0YWJsZUNvbXBvbmVudCxcbiAgICAgICAgY2RyOkNoYW5nZURldGVjdG9yUmVmKSB7XG5cbiAgICAgICAgc3VwZXIoZWxlbVJlZiwgZHJhZ0Ryb3BTZXJ2aWNlLCBjb25maWcsIF9Db21wb25lbnQsIGNkcik7XG4gICAgfVxufVxuXG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIC4vfi90c2xpbnQtbG9hZGVyIS4vc3JjL3NvcnRhYmxlLmNvbXBvbmVudC50cyIsIi8qKlxuICogQ2hlY2sgYW5kIHJldHVybiB0cnVlIGlmIGFuIG9iamVjdCBpcyB0eXBlIG9mIHN0cmluZ1xuICovXG5leHBvcnQgZnVuY3Rpb24gaXNTdHJpbmcob2JqOmFueSkge1xuICAgIHJldHVybiB0eXBlb2Ygb2JqID09PSBcInN0cmluZ1wiO1xufVxuXG4vKipcbiAqIENoZWNrIGFuZCByZXR1cm4gdHJ1ZSBpZiBhbiBvYmplY3Qgbm90IHVuZGVmaW5lZCBvciBudWxsXG4gKi9cbmV4cG9ydCBmdW5jdGlvbiBpc1ByZXNlbnQob2JqOiBhbnkpIHtcbiAgICByZXR1cm4gb2JqICE9PSB1bmRlZmluZWQgJiYgb2JqICE9PSBudWxsO1xufVxuXG4vKipcbiAqIENoZWNrIGFuZCByZXR1cm4gdHJ1ZSBpZiBhbiBvYmplY3QgaXMgdHlwZSBvZiBGdW5jdGlvblxuICovXG5leHBvcnQgZnVuY3Rpb24gaXNGdW5jdGlvbihvYmo6IGFueSkge1xuICAgIHJldHVybiB0eXBlb2Ygb2JqID09PSBcImZ1bmN0aW9uXCI7XG59XG5cbi8qKlxuICogQ3JlYXRlIEltYWdlIGVsZW1lbnQgd2l0aCBzcGVjaWZpZWQgdXJsIHN0cmluZ1xuICovXG5leHBvcnQgZnVuY3Rpb24gY3JlYXRlSW1hZ2Uoc3JjOiBzdHJpbmcpIHtcbiAgICBsZXQgaW1nOkhUTUxJbWFnZUVsZW1lbnQgPSBuZXcgSFRNTEltYWdlRWxlbWVudCgpO1xuICAgIGltZy5zcmMgPSBzcmM7XG4gICAgcmV0dXJuIGltZztcbn1cblxuLyoqXG4gKiBDYWxsIHRoZSBmdW5jdGlvblxuICovXG5leHBvcnQgZnVuY3Rpb24gY2FsbEZ1bihmdW46IEZ1bmN0aW9uKSB7XG4gICAgcmV0dXJuIGZ1bigpO1xufVxuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL34vdHNsaW50LWxvYWRlciEuL3NyYy9kbmQudXRpbHMudHMiLCIvLyBDb3B5cmlnaHQgKEMpIDIwMTYgU2VyZ2V5IEFrb3Brb2toeWFudHNcbi8vIFRoaXMgcHJvamVjdCBpcyBsaWNlbnNlZCB1bmRlciB0aGUgdGVybXMgb2YgdGhlIE1JVCBsaWNlbnNlLlxuLy8gaHR0cHM6Ly9naXRodWIuY29tL2Frc2VyZy9uZzItZG5kXG5cbmltcG9ydCB7IE5nTW9kdWxlLCBNb2R1bGVXaXRoUHJvdmlkZXJzIH0gZnJvbSBcIkBhbmd1bGFyL2NvcmVcIjtcblxuaW1wb3J0IHtEcmFnRHJvcENvbmZpZ30gZnJvbSAnLi9zcmMvZG5kLmNvbmZpZyc7XG5pbXBvcnQge0RyYWdEcm9wU2VydmljZSwgRHJhZ0Ryb3BTb3J0YWJsZVNlcnZpY2UsIGRyYWdEcm9wU2VydmljZUZhY3RvcnksIGRyYWdEcm9wU29ydGFibGVTZXJ2aWNlRmFjdG9yeX0gZnJvbSAnLi9zcmMvZG5kLnNlcnZpY2UnO1xuaW1wb3J0IHtEcmFnZ2FibGVDb21wb25lbnQsIERyYWdnYWJsZUhhbmRsZUNvbXBvbmVudH0gZnJvbSAnLi9zcmMvZHJhZ2dhYmxlLmNvbXBvbmVudCc7XG5pbXBvcnQge0Ryb3BwYWJsZUNvbXBvbmVudH0gZnJvbSAnLi9zcmMvZHJvcHBhYmxlLmNvbXBvbmVudCc7XG5pbXBvcnQge1NvcnRhYmxlQ29udGFpbmVyLCBTb3J0YWJsZUNvbXBvbmVudCwgU29ydGFibGVIYW5kbGVDb21wb25lbnR9IGZyb20gJy4vc3JjL3NvcnRhYmxlLmNvbXBvbmVudCc7XG5cbmV4cG9ydCAqIGZyb20gJy4vc3JjL2Fic3RyYWN0LmNvbXBvbmVudCc7XG5leHBvcnQgKiBmcm9tICcuL3NyYy9kbmQuY29uZmlnJztcbmV4cG9ydCAqIGZyb20gJy4vc3JjL2RuZC5zZXJ2aWNlJztcbmV4cG9ydCAqIGZyb20gJy4vc3JjL2RyYWdnYWJsZS5jb21wb25lbnQnO1xuZXhwb3J0ICogZnJvbSAnLi9zcmMvZHJvcHBhYmxlLmNvbXBvbmVudCc7XG5leHBvcnQgKiBmcm9tICcuL3NyYy9zb3J0YWJsZS5jb21wb25lbnQnO1xuXG5leHBvcnQgbGV0IHByb3ZpZGVycyA9IFtcbiAgICBEcmFnRHJvcENvbmZpZyxcbiAgICB7IHByb3ZpZGU6IERyYWdEcm9wU2VydmljZSwgdXNlRmFjdG9yeTogZHJhZ0Ryb3BTZXJ2aWNlRmFjdG9yeSB9LFxuICAgIHsgcHJvdmlkZTogRHJhZ0Ryb3BTb3J0YWJsZVNlcnZpY2UsIHVzZUZhY3Rvcnk6IGRyYWdEcm9wU29ydGFibGVTZXJ2aWNlRmFjdG9yeSwgZGVwczogW0RyYWdEcm9wQ29uZmlnXSB9XG5dO1xuXG5ATmdNb2R1bGUoe1xuICBkZWNsYXJhdGlvbnM6IFtEcmFnZ2FibGVDb21wb25lbnQsIERyYWdnYWJsZUhhbmRsZUNvbXBvbmVudCwgRHJvcHBhYmxlQ29tcG9uZW50LCBTb3J0YWJsZUNvbnRhaW5lciwgU29ydGFibGVDb21wb25lbnQsIFNvcnRhYmxlSGFuZGxlQ29tcG9uZW50XSxcbiAgZXhwb3J0cyA6IFtEcmFnZ2FibGVDb21wb25lbnQsIERyYWdnYWJsZUhhbmRsZUNvbXBvbmVudCwgRHJvcHBhYmxlQ29tcG9uZW50LCBTb3J0YWJsZUNvbnRhaW5lciwgU29ydGFibGVDb21wb25lbnQsIFNvcnRhYmxlSGFuZGxlQ29tcG9uZW50XSxcblxufSlcbmV4cG9ydCBjbGFzcyBEbmRNb2R1bGUge1xuICBzdGF0aWMgZm9yUm9vdCgpOiBNb2R1bGVXaXRoUHJvdmlkZXJzIHtcbiAgICAgICAgcmV0dXJuIHtcbiAgICAgICAgICAgIG5nTW9kdWxlOiBEbmRNb2R1bGUsXG4gICAgICAgICAgICBwcm92aWRlcnM6IHByb3ZpZGVyc1xuICAgICAgICB9O1xuICAgIH1cbn1cblxuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL34vdHNsaW50LWxvYWRlciEuL2luZGV4LnRzIl0sInNvdXJjZVJvb3QiOiIifQ==