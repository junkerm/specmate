var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var model;
(function (model) {
    var ObjectStateBase = (function () {
        function ObjectStateBase() {
        }
        ObjectStateBase.prototype.attach = function (object, baseUrl, internalUri) {
            throw new Error("state transition not allowed");
        };
        ObjectStateBase.prototype.load = function (object, jsonObject) {
            throw new Error("state transition not allowed");
        };
        ObjectStateBase.prototype.initObject = function (object, json) {
            if (json) {
                if (!json.___proxy) {
                    object.populateFromJSON(json);
                }
                else {
                    throw new Error("Attempt to create SpecmateModelObject from proxy");
                }
            }
            object.installEventListener();
        };
        return ObjectStateBase;
    })();
    var ObjectStateMachine = (function () {
        function ObjectStateMachine(object) {
            this._state = TransientState.getInstance();
            this.object = object;
        }
        Object.defineProperty(ObjectStateMachine.prototype, "state", {
            get: function () {
                return this._state;
            },
            enumerable: true,
            configurable: true
        });
        ObjectStateMachine.prototype.attach = function (baseUrl, internalUri) {
            this._state = this._state.attach(this.object, baseUrl, internalUri);
        };
        ObjectStateMachine.prototype.load = function (jsonObject) {
            this._state = this._state.load(this.object, jsonObject);
        };
        return ObjectStateMachine;
    })();
    model.ObjectStateMachine = ObjectStateMachine;
    var TransientState;
    (function (TransientState) {
        var _TransientState = (function (_super) {
            __extends(_TransientState, _super);
            function _TransientState() {
                _super.apply(this, arguments);
            }
            _TransientState.prototype.attach = function (object, baseUrl, internalUri) {
                object.baseUrl = baseUrl;
                object.internalUri = internalUri;
                return NewState.getInstance();
            };
            return _TransientState;
        })(ObjectStateBase);
        var _instance = new _TransientState();
        function getInstance() {
            return _instance;
        }
        TransientState.getInstance = getInstance;
    })(TransientState || (TransientState = {}));
    var NewState;
    (function (NewState) {
        var _NewState = (function (_super) {
            __extends(_NewState, _super);
            function _NewState() {
                _super.apply(this, arguments);
            }
            _NewState.prototype.load = function (object, jsonObject) {
                if (jsonObject) {
                    this.initObject(object, jsonObject);
                }
                return ConnectedState.getInstance();
            };
            return _NewState;
        })(ObjectStateBase);
        var _instance = new _NewState();
        function getInstance() {
            return _instance;
        }
        NewState.getInstance = getInstance;
    })(NewState || (NewState = {}));
    var ConnectedState;
    (function (ConnectedState) {
        var _ConnectedState = (function (_super) {
            __extends(_ConnectedState, _super);
            function _ConnectedState() {
                _super.apply(this, arguments);
            }
            return _ConnectedState;
        })(ObjectStateBase);
        var _instance = new _ConnectedState();
        function getInstance() {
            return _instance;
        }
        ConnectedState.getInstance = getInstance;
    })(ConnectedState || (ConnectedState = {}));
})(model || (model = {}));
var model;
(function (model) {
    var ModelUtils;
    (function (ModelUtils) {
        var newAction = function (parent) {
            var action = new model.Action();
            action.name("New Action");
            action.id(String(Date.now()).replace(".", ""));
            action.x(100);
            action.y(10);
            store.createObject(action, parent.internalUri, "contents");
        };
        function newBaseModelNode(parent) {
            var node = new model.BaseModelNode();
            node.name("New Node");
            node.id(String(Date.now()).replace(".", ""));
            store.createObject(node, parent.internalUri, "contents");
        }
        ModelUtils.newBaseModelNode = newBaseModelNode;
        function newBlueprint(parent) {
            var bp = new model.Blueprint();
            bp.name("New Blueprint");
            bp.id(String(Date.now()).replace(".", ""));
            store.createObject(bp, parent.internalUri, "contents");
        }
        ModelUtils.newBlueprint = newBlueprint;
        function newTestSpecification(parent) {
            var ts = new model.TestSpecification();
            ts.name("New Test Specification");
            ts.id(String(Date.now()).replace(".", ""));
            store.createObject(ts, parent.internalUri, "contents");
        }
        ModelUtils.newTestSpecification = newTestSpecification;
        function newSlice(parent) {
            var slice = new model.Slice();
            slice.name("New Slice");
            slice.id(String(Date.now()).replace(".", ""));
            store.createObject(slice, parent.internalUri, "testprocedure");
        }
        ModelUtils.newSlice = newSlice;
        function newProject() {
            var node = new model.BaseModelNode();
            node.name("New Project");
            node.id(String(Date.now()).replace(".", ""));
            store.createObject(node, "", "");
        }
        ModelUtils.newProject = newProject;
        function newConnection(selection) {
            if (selection.length >= 2) {
                var action1 = selection[0];
                action1.successors.value.push(selection[1]);
            }
        }
        ModelUtils.newConnection = newConnection;
    })(ModelUtils = model.ModelUtils || (model.ModelUtils = {}));
})(model || (model = {}));
var components;
(function (components) {
    ko.components.register("element-chooser", {
        viewModel: {
            createViewModel: function (params) {
                return {
                    resource: params.resource,
                    selectionObservable: params.selectionObservable,
                    visibilityObservable: ko.observable(false)
                };
            }
        },
        template: '\
<span data-bind="with:selectionObservable"><span class="specmate-editor-meta-data-other" data-bind="text:name" /></span> <span style="margin-left:15px"><i class="fa fa-arrow-circle-right" /> <a href="" data-bind="click:function(){visibilityObservable(true)}" >choose</a></span>\
<project-explorer-popup params="resource: resource, selectionObservable:selectionObservable, visibilityObservable:visibilityObservable"></project-explorer-popoup>\
'
    });
})(components || (components = {}));
var util;
(function (util) {
    var JournalingObservableWrappere = (function (_super) {
        __extends(JournalingObservableWrappere, _super);
        function JournalingObservableWrappere(observable, defaultSource) {
            _super.call(this);
            observable.e;
            this.defaultSource = defaultSource;
        }
        JournalingObservableWrappere.prototype.getValue = function () {
        };
        JournalingObservableWrappere.prototype.setValue = function (value) {
        };
        JournalingObservableWrappere.prototype.setValue = function (value, source) {
            observable;
        };
        // If no default is given, this is used as default source
        JournalingObservableWrappere.DEFAULT_SOURCE = 'default';
        return JournalingObservableWrappere;
    })(util.ObservableWrapperBase);
    util.JournalingObservableWrappere = JournalingObservableWrappere;
})(util || (util = {}));
/// <reference path="../definitions/d3.d.ts" />
var customBindings;
(function (customBindings) {
    ko.bindingHandlers["drag"] = {
        init: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
            var selected = d3.select(element);
            var xObs = valueAccessor().x;
            var yObs = valueAccessor().y;
            selected.attr('transform', 'translate(' + xObs() + ',' + yObs() + ')');
            var drag = d3.behavior.drag();
            drag = drag.on('drag', function () {
                var dx = d3.event.dx;
                var dy = d3.event.dy;
                console.log('delta is:' + dx + ',' + dy);
                var xObs = valueAccessor().x;
                var yObs = valueAccessor().y;
                console.log('current is:' + xObs() + ',' + yObs());
                xObs(xObs() + dx);
                yObs(yObs() + dy);
                console.log('new  is:' + xObs() + ',' + yObs());
                selected.attr('transform', 'translate(' + xObs() + ',' + yObs() + ')');
            });
            drag.call(selected);
        },
        update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
            var selected = d3.select(element);
            var xObs = valueAccessor().x;
            var yObs = valueAccessor().y;
            selected.attr('transform', 'translate(' + xObs() + ',' + yObs() + ')');
        }
    };
})(customBindings || (customBindings = {}));
var util;
(function (util) {
    var ObservableWrapperBase = (function () {
        function ObservableWrapperBase(observable) {
            if (observable) {
                this.observable = observable;
            }
        }
        ObservableWrapperBase.prototype.getObservable = function () {
            return this.observable;
        };
        ObservableWrapperBase.prototype.setObservable = function (observable) {
            this.observable = observable;
        };
        ObservableWrapperBase.prototype.getValue = function () {
            return this.observable();
        };
        ObservableWrapperBase.prototype.setValue = function (value) {
            this.observable(value);
        };
        return ObservableWrapperBase;
    })();
    util.ObservableWrapperBase = ObservableWrapperBase;
})(util || (util = {}));
/// <referece path="SpecmateModelObjects.ts" />
var model;
(function (model) {
    var ViewModel = (function () {
        function ViewModel(baseUrl) {
            var _this = this;
            this.selected = ko.observable();
            this.selected2 = ko.observableArray();
            this.resource = new model.Resource(baseUrl);
            this.selected.subscribe(function (newValue) {
                _this.selected2.splice(0, _this.selected2().length);
            });
        }
        ViewModel.prototype.select = function (selection) {
            this.selected(selection);
        };
        ViewModel.prototype.toggleSelected2 = function (selection) {
            console.log("toggle");
            var removed = this.selected2.remove(selection);
            if (removed.length == 0) {
                this.selected2.push(selection);
                console.log("added " + selection);
            }
            else {
                console.log("removed " + selection);
            }
        };
        ViewModel.prototype.isSelected2 = function (obj) {
            return this.selected2().indexOf(obj) > -1;
        };
        return ViewModel;
    })();
    model.ViewModel = ViewModel;
})(model || (model = {}));
/// <reference path="../definitions/sse.d.ts"/>
var model;
(function (model) {
    var SpecmateEventSource = (function () {
        function SpecmateEventSource(baseUrl) {
            var _this = this;
            this.registrations = new Array();
            this.eventSource = new EventSource(baseUrl + '/_events');
            this.eventSource.addEventListener('specmate_model_event', function (e) {
                var event = e;
                var modelEvent = JSON.parse(event.data);
                var url = modelEvent.uri;
                for (var i = 0; i < _this.registrations.length; i++) {
                    if (url == _this.registrations[i].url) {
                        _this.registrations[i].listener(modelEvent);
                    }
                }
            });
        }
        SpecmateEventSource.prototype.registerForEvent = function (eventUrl, listener) {
            this.registrations.push({ url: eventUrl, listener: listener });
        };
        return SpecmateEventSource;
    })();
    model.SpecmateEventSource = SpecmateEventSource;
})(model || (model = {}));
/// <reference path="../definitions/jquery.d.ts"/>
/// <reference path="../definitions/knockout.d.ts"/>
var model;
(function (model) {
    var SpecmateModelObject = (function () {
        function SpecmateModelObject(baseUrl, internalUri, json) {
            this.opened = ko.observable(false);
            this.opened2 = ko.observable(false);
            this.url = ko.observable("");
            this.internalUri = internalUri;
            this.baseUrl = baseUrl;
            if (json && !json.___proxy) {
                this.populateFromJSON(json);
            }
            else if (baseUrl && internalUri) {
                this.populate();
            }
            if (this.internalUri) {
                this.installEventListener();
            }
        }
        SpecmateModelObject.prototype.toggleOpen = function () {
            this.opened(!this.opened());
        };
        SpecmateModelObject.prototype.toggleOpen2 = function () {
            this.opened2(!this.opened2());
        };
        SpecmateModelObject.prototype.getInternalUri = function () {
            return this.internalUri;
        };
        SpecmateModelObject.prototype.getBaseUrl = function () {
            return this.baseUrl;
        };
        SpecmateModelObject.prototype.populate = function () {
            var _this = this;
            $.getJSON(this.baseUrl + "/" + this.internalUri, function (result) { _this.populateFromJSON(result); });
        };
        SpecmateModelObject.prototype.populateFromJSON = function (jsonInput) {
            this.url(jsonInput.___uri);
            var attributes = this._getMetaData().getAttributes();
            for (var i = 0; i < attributes.length; i++) {
                if (jsonInput.hasOwnProperty(attributes[i])) {
                    this._set(attributes[i], jsonInput[attributes[i]]);
                }
            }
        };
        SpecmateModelObject.prototype.installEventListener = function () {
            var _this = this;
            console.log("Install event listener for " + this);
            model.globalEventSource.registerForEvent(this.internalUri, function (modelEvent) {
                console.log("Event: " + modelEvent.type + " " + modelEvent.uri + " " + modelEvent.featureName);
                switch (modelEvent.type) {
                    case "set":
                        _this._set(modelEvent.featureName, _this.deserializeValue(modelEvent.value));
                        break;
                    case "add":
                        var obsArray = _this._get(modelEvent.featureName);
                        obsArray.value.push(_this.deserializeValue(modelEvent.value));
                        break;
                    case "remove":
                        var obsArray = _this._get(modelEvent.featureName);
                        var value = obsArray.value;
                        value.splice(modelEvent.index, 1);
                        break;
                    case "clear":
                        var obsArray = _this._get(modelEvent.featureName);
                        var value = obsArray.value;
                        value.splice(0, value().length);
                        break;
                }
            });
            this.attributesObserver = ko.computed(function () {
                var json = model.modelFactory.getJSONFromModel(_this);
                if (_this.initialized) {
                    // use standard means from computed observables (deferEvaluation)
                    $.ajax({
                        url: _this.baseUrl + "/" + _this.internalUri,
                        type: 'PUT',
                        contentType: "application/json",
                        data: JSON.stringify(json)
                    });
                }
                _this.initialized = true;
            }).extend({ rateLimit: 500 });
        };
        SpecmateModelObject.prototype.deserializeValue = function (value) {
            if ((typeof value) == "object") {
                return model.modelFactory.getModelFromJSON(value);
            }
            else {
                return value;
            }
        };
        SpecmateModelObject.prototype.delete = function () {
            $.ajax({
                url: this.baseUrl + "/" + this.internalUri,
                type: 'DELETE'
            });
        };
        return SpecmateModelObject;
    })();
    model.SpecmateModelObject = SpecmateModelObject;
    var Resource = (function (_super) {
        __extends(Resource, _super);
        function Resource(baseUrl) {
            this.createContents();
            _super.call(this, baseUrl, "");
            this.installEventListener();
        }
        Resource.prototype.createContents = function () {
            var _this = this;
            if (!this.contents) {
                this.contents = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl(), function (result) {
                        _this.contents(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
            return this.contents;
        };
        Resource.prototype._get = function (feature) {
            switch (feature) {
                case "contents":
                    return this.contents;
                    break;
                default: throw new Error("Feature not defined: " + feature);
            }
        };
        Resource.prototype._set = function (feature, value) {
            switch (feature) {
                default: throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        Resource.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "Resource";
                },
                getNsUri: function () {
                    return "http://www.eclipse.org/emf/2002/Ecore";
                },
                getAttributes: function () {
                    return [];
                },
                getReferences: function () {
                    return [];
                }
            };
        };
        return Resource;
    })(SpecmateModelObject);
    model.Resource = Resource;
})(model || (model = {}));
/// <reference path="SpecmateModelObject.ts"/>
var model;
(function (model_1) {
    var ModelFactory = (function () {
        function ModelFactory(baseUrl) {
            var _this = this;
            this.baseUrl = baseUrl;
            this.objectStore = {};
            this.newAction = function (parent) {
                var action = new model_1.Action();
                action.name("New Action");
                action.id(String(Date.now()).replace(".", ""));
                action.x(100);
                action.y(10);
                var json = _this.getJSONFromModel(action);
                $.ajax({
                    url: _this.baseUrl + "/" + parent.url() + "/contents",
                    type: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(json)
                });
            };
            this.newBaseModelNode = function (parent) {
                var node = new model_1.BaseModelNode();
                node.name("New Node");
                node.id(String(Date.now()).replace(".", ""));
                var json = _this.getJSONFromModel(node);
                $.ajax({
                    url: _this.baseUrl + "/" + parent.url() + "/contents",
                    type: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(json)
                });
            };
            this.newBlueprint = function (parent) {
                var bp = new model_1.Blueprint();
                bp.name("New Blueprint");
                bp.id(String(Date.now()).replace(".", ""));
                var json = _this.getJSONFromModel(bp);
                $.ajax({
                    url: _this.baseUrl + "/" + parent.url() + "/contents",
                    type: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(json)
                });
            };
            this.newTestSpecification = function (parent) {
                var ts = new model_1.TestSpecification();
                ts.name("New Test Specification");
                ts.id(String(Date.now()).replace(".", ""));
                var json = _this.getJSONFromModel(ts);
                $.ajax({
                    url: _this.baseUrl + "/" + parent.url() + "/contents",
                    type: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(json)
                });
            };
            this.newSlice = function (parent) {
                var slice = new model_1.Slice();
                slice.name("New Slice");
                slice.id(String(Date.now()).replace(".", ""));
                var json = _this.getJSONFromModel(slice);
                $.ajax({
                    url: _this.baseUrl + "/" + parent.url() + "/testprocedure",
                    type: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(json)
                });
            };
            this.newProject = function () {
                var node = new model_1.BaseModelNode();
                node.name("New Project");
                node.id(String(Date.now()).replace(".", ""));
                var json = _this.getJSONFromModel(node);
                $.ajax({
                    url: _this.baseUrl + "/",
                    type: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(json)
                });
            };
            this.newConnection = function (selection) {
                if (selection.length >= 2) {
                    var action1 = selection[0];
                    action1.successors.value.push(selection[1]);
                }
            };
        }
        ModelFactory.prototype.getModelFromJSON = function (json) {
            if (typeof (json) == "object") {
                if (!this.objectStore[json.___uri]) {
                    var newObject;
                    switch (json.___eclass) {
                        case "BaseModelNode":
                            newObject = new model_1.BaseModelNode(this.baseUrl, json.___uri, json);
                            break;
                        case "Blueprint":
                            newObject = new model_1.Blueprint(this.baseUrl, json.___uri, json);
                            break;
                        case "Action":
                            newObject = new model_1.Action(this.baseUrl, json.___uri, json);
                            break;
                        case "TestSpecification":
                            newObject = new model_1.TestSpecification(this.baseUrl, json.___uri, json);
                            break;
                        case "Slice":
                            newObject = new model_1.Slice(this.baseUrl, json.___uri, json);
                            break;
                        case "SliceProcedure":
                            newObject = new model_1.SliceProcedure(this.baseUrl, json.___uri, json);
                            break;
                        case "TestProcedure":
                            newObject = new model_1.TestProcedure(this.baseUrl, json.___uri, json);
                            break;
                        case "TestProcedureAction":
                            newObject = new model_1.TestProcedureAction(this.baseUrl, json.___uri, json);
                            break;
                        default:
                            throw new Error("Model of type " + json.___eclass + " is not known");
                    }
                    this.objectStore[json.___uri] = newObject;
                }
                return this.objectStore[json.___uri];
            }
            else {
                throw new Error("Attemp to create model object from non-object");
            }
        };
        ModelFactory.prototype.getJSONFromModel = function (modelObject) {
            var _this = this;
            var json = {};
            var attributes = modelObject._getMetaData().getAttributes();
            var references = modelObject._getMetaData().getReferences();
            for (var i = 0; i < attributes.length; i++) {
                json[attributes[i]] = String(modelObject._get(attributes[i]));
            }
            for (var i = 0; i < references.length; i++) {
                var ref = modelObject._get(references[i]);
                var value;
                if (ko.isObservable(ref)) {
                    value = ref();
                }
                else {
                    value = ref;
                }
                if (value) {
                    if (Array.isArray(value)) {
                        json[references[i]] = value.map(function (m) { return _this.referenceToJson(m); });
                    }
                    else {
                        json[references[i]] = this.referenceToJson(value);
                    }
                }
            }
            json.___eclass = modelObject._getMetaData().getClassName();
            json.___nsuri = modelObject._getMetaData().getNsUri();
            return json;
        };
        ModelFactory.prototype.referenceToJson = function (modelObject) {
            var specmateModel = modelObject;
            var json = {
                ___uri: (specmateModel.url()),
                ___eclass: specmateModel._getMetaData().getClassName(),
                ___proxy: true
            };
            return json;
        };
        return ModelFactory;
    })();
    model_1.ModelFactory = ModelFactory;
})(model || (model = {}));
/// <reference path="SpecmateEventSource.ts"/>
/// <reference path="ModelFactory.ts"/>
/// <reference path="ViewModel.ts" />
var model;
(function (model) {
    function initSpecmate(baseUrl) {
        model.globalEventSource = new model.SpecmateEventSource(baseUrl);
        model.modelFactory = new model.ModelFactory(baseUrl);
        model.viewModel = new model.ViewModel(baseUrl);
    }
    model.initSpecmate = initSpecmate;
    initSpecmate("/services/rest");
})(model || (model = {}));
var components;
(function (components) {
    ko.components.register("project-explorer-popup", {
        viewModel: {
            createViewModel: function (params) {
                return {
                    resource: params.resource,
                    selectionObservable: params.selectionObservable,
                    visibilityObservable: params.visibilityObservable
                };
            }
        },
        template: '\
<!-- ko if:visibilityObservable -->\
<div class="specmate-modal">\
<div  class="specmate-modal-content">\
  <div class="specmate-modal-header">\
    <span class="specmate-close"><a href="" data-bind="click:function(){visibilityObservable(false)}"><i class="fa fa-times" /></a></span>\
    <h2>Choose Element</h2>\
  </div>\
  <div class="specmate-modal-body">\
    <project-explorer params="resource: resource, selectionObservable: selectionObservable, secondLevel:true"></project-eplorer>\
  </div>\
  <div class="specmate-modal-footer">\
  </div>\
</div>\
</div>\
<!-- /ko -->\
'
    });
})(components || (components = {}));
/// <reference path="SpecmateModelObject.ts" />
/// <reference path="ModelFactory.ts" />
/// <reference path="../definitions/knockout.d.ts" />
/// <reference path="../definitions/jquery.d.ts" />
/**
 * Model file for project
 */
var model;
(function (model) {
    var Blueprint = (function (_super) {
        __extends(Blueprint, _super);
        function Blueprint(baseUrl, internalUri, json) {
            this.createNameObservable();
            this.createIdObservable();
            this.createDescriptionObservable();
            this.createContentsObservable();
            this.iconClass = "fa fa-code-fork";
            _super.call(this, baseUrl, internalUri, json);
        }
        Blueprint.prototype.createContentsObservable = function () {
            var _this = this;
            if (!this.contents) {
                this.contents = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/contents", function (result) {
                        _this.contents(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
            return this.contents;
        };
        Blueprint.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        Blueprint.prototype.createNameObservable = function () {
            if (!this.name) {
                this.name = ko.observable("");
            }
        };
        Blueprint.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        Blueprint.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "description":
                    return this.description();
                    break;
                case "contents":
                    return this.contents;
                default: throw new Error("Blueprint: Feature not defined: " + feature);
            }
        };
        Blueprint.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "description":
                    this.description(value);
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        Blueprint.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "Blueprint";
                },
                getNsUri: function () {
                    return "http://allianz.com/20151023_05/model/blueprint";
                },
                getAttributes: function () {
                    return ["id", "name", "description"];
                },
                getReferences: function () {
                    return [];
                }
            };
        };
        return Blueprint;
    })(model.SpecmateModelObject);
    model.Blueprint = Blueprint;
    var BaseModelNode = (function (_super) {
        __extends(BaseModelNode, _super);
        function BaseModelNode(baseUrl, internalUri, json) {
            this.createIdObservable();
            this.createNameObservable();
            this.createContentsObservable();
            this.createDescriptionObservable();
            this.iconClass = "fa fa-folder";
            _super.call(this, baseUrl, internalUri, json);
        }
        BaseModelNode.prototype.createContentsObservable = function () {
            var _this = this;
            if (!this.contents) {
                this.contents = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/contents", function (result) {
                        _this.contents(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
            return this.contents;
        };
        BaseModelNode.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        BaseModelNode.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        BaseModelNode.prototype.createNameObservable = function () {
            var _this = this;
            if (!this.name) {
                this.name = ko.observable("");
                this.shortName = ko.computed(function () {
                    if (_this.name().length > 10) {
                        return _this.name().substr(0, 8) + "...";
                    }
                    else
                        return _this.name();
                });
            }
            return this.name;
        };
        BaseModelNode.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "description":
                    return this.description();
                    break;
                case "contents":
                    return this.contents;
                case "views":
                    return this.contents;
                default: throw new Error("BlueprintFolder: Feature not defined: " + feature);
            }
        };
        BaseModelNode.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "description":
                    this.description(value);
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        BaseModelNode.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "BaseModelNode";
                },
                getAttributes: function () {
                    return ["id", "name", "description"];
                },
                getNsUri: function () {
                    return "http://specmate.com/20151203_01/model/basemodel";
                },
                getReferences: function () {
                    return [];
                }
            };
        };
        return BaseModelNode;
    })(model.SpecmateModelObject);
    model.BaseModelNode = BaseModelNode;
    var Action = (function (_super) {
        __extends(Action, _super);
        function Action(baseUrl, internalUri, json) {
            this.createNameObservable();
            this.createDescriptionObservable();
            this.createIdObservable();
            this.createXObservable();
            this.createYObservable();
            this.createSuccessorObservable();
            this.createPredecessorObservable();
            this.createTextYObservable();
            this.createLineXObservable();
            this.createLineYObservable();
            this.createOptionalObservable();
            this.createContentsObservable();
            _super.call(this, baseUrl, internalUri, json);
            this.createIconObservable();
        }
        Action.prototype.createNameObservable = function () {
            var _this = this;
            if (!this.name) {
                this.name = ko.observable("");
                this.shortName = ko.computed(function () {
                    if (_this.name().length > 25) {
                        return _this.name().substr(0, 23) + "...";
                    }
                    else
                        return _this.name();
                });
            }
            return this.name;
        };
        Action.prototype.createOptionalObservable = function () {
            //if (this.optional === undefined) {
            this.optional = ko.observable(false);
            //}
        };
        Action.prototype.createContentsObservable = function () {
            var _this = this;
            if (!this.contents) {
                this.contents = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/contents", function (result) {
                        _this.contents(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
            return this.contents;
        };
        Action.prototype.createXObservable = function () {
            if (!this.x) {
                this.x = ko.observable(0);
            }
        };
        Action.prototype.createYObservable = function () {
            if (!this.y) {
                this.y = ko.observable(0);
            }
        };
        Action.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        Action.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        Action.prototype.createTextYObservable = function () {
            var _this = this;
            this.textY = ko.pureComputed(function () { return _this.y() + 35; });
        };
        Action.prototype.createLineXObservable = function () {
            var _this = this;
            this.lineX = ko.pureComputed(function () { return _this.x() + 110; });
        };
        Action.prototype.createLineYObservable = function () {
            var _this = this;
            this.lineY = ko.pureComputed(function () { return _this.y() + 50; });
        };
        Action.prototype.createSuccessorObservable = function () {
            var _this = this;
            if (!this.successors) {
                this.successors = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/successors", function (result) {
                        _this.successors(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
        };
        Action.prototype.createPredecessorObservable = function () {
            var _this = this;
            if (!this.predecessors) {
                this.predecessors = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/predecessors", function (result) {
                        _this.predecessors(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
        };
        Action.prototype.createIconObservable = function () {
            var _this = this;
            this.iconClass = ko.computed(function () {
                if (_this.contents().length > 0) {
                    return "fa fa-dot-circle-o";
                }
                else {
                    return "fa fa-circle-o";
                }
            });
        };
        Action.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "optional":
                    return this.optional();
                    break;
                case "description":
                    return this.description();
                    break;
                case "x":
                    return this.x();
                    break;
                case "y":
                    return this.y();
                    break;
                case "successors":
                    return this.successors;
                    break;
                case "predecessors":
                    return this.predecessors;
                    break;
                case "contents":
                    return this.contents;
                default: throw new Error("Action: Feature not defined: " + feature);
            }
        };
        Action.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "optional":
                    this.optional((value === "true"));
                    break;
                case "description":
                    this.description(value);
                    break;
                case "x":
                    this.x(parseInt(value));
                    break;
                case "y":
                    this.y(parseInt(value));
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        Action.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "Action";
                },
                getNsUri: function () {
                    return "http://allianz.com/20151023_05/model/blueprint";
                },
                getAttributes: function () {
                    return ["id", "name", "description", "optional", "x", "y"];
                },
                getReferences: function () {
                    return ["successors", "predecessors"];
                }
            };
        };
        return Action;
    })(model.SpecmateModelObject);
    model.Action = Action;
    var TestSpecification = (function (_super) {
        __extends(TestSpecification, _super);
        function TestSpecification(baseUrl, internalUri, json) {
            this.createIdObservable();
            this.createNameObservable();
            this.createPreconditionsObservable();
            this.createTestProcedureObservable();
            this.createDescriptionObservable();
            this.iconClass = "fa fa-cogs";
            _super.call(this, baseUrl, internalUri, json);
            this.createContentObservable();
        }
        TestSpecification.prototype.createPreconditionsObservable = function () {
            var _this = this;
            if (!this.preconditions) {
                this.preconditions = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/precondition", function (result) {
                        _this.preconditions(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
            return this.preconditions;
        };
        TestSpecification.prototype.createTestProcedureObservable = function () {
            var _this = this;
            if (!this.testprocedures) {
                this.testprocedures = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/testprocedure", function (result) {
                        _this.testprocedures(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
            return this.testprocedures;
        };
        TestSpecification.prototype.createContentObservable = function () {
            var _this = this;
            this.contents = ko.computed(function () {
                return _this.preconditions().concat(_this.testprocedures());
            });
        };
        TestSpecification.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        TestSpecification.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        TestSpecification.prototype.createNameObservable = function () {
            if (!this.name) {
                this.name = ko.observable("");
            }
            return this.name;
        };
        TestSpecification.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "description":
                    return this.description();
                    break;
                case "precondition":
                    return this.preconditions;
                case "testprocedure":
                    return this.testprocedures;
                default: throw new Error("TestSpecification: Feature not defined: " + feature);
            }
        };
        TestSpecification.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "description":
                    this.description(value);
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        TestSpecification.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "TestSpecification";
                },
                getAttributes: function () {
                    return ["id", "name", "description"];
                },
                getNsUri: function () {
                    return "http://allianz.com/20151023_05/model/test";
                },
                getReferences: function () {
                    return [];
                }
            };
        };
        return TestSpecification;
    })(model.SpecmateModelObject);
    model.TestSpecification = TestSpecification;
    var Slice = (function (_super) {
        __extends(Slice, _super);
        function Slice(baseUrl, internalUri, json) {
            this.createIdObservable();
            this.createNameObservable();
            this.createFromObservable();
            this.createToObservable();
            this.createDescriptionObservable();
            this.iconClass = "fa fa-cog";
            _super.call(this, baseUrl, internalUri, json);
        }
        Slice.prototype.createFromObservable = function () {
            var _this = this;
            if (!this.from) {
                this.from = ko.onDemandObservable(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/from", function (result) {
                        if (result[0]) {
                            _this.from(model.modelFactory.getModelFromJSON(result[0]));
                        }
                    });
                });
            }
            return this.from;
        };
        Slice.prototype.createToObservable = function () {
            var _this = this;
            if (!this.to) {
                this.to = ko.onDemandObservable(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/to", function (result) {
                        if (result[0]) {
                            _this.to(model.modelFactory.getModelFromJSON(result[0]));
                        }
                    });
                });
            }
            return this.to;
        };
        Slice.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        Slice.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        Slice.prototype.createNameObservable = function () {
            if (!this.name) {
                this.name = ko.observable("");
            }
            return this.name;
        };
        Slice.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "description":
                    return this.description();
                    break;
                case "from":
                    return this.from;
                case "to":
                    return this.to;
                default: throw new Error("Slice: Feature not defined: " + feature);
            }
        };
        Slice.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "description":
                    this.description(value);
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        Slice.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "Slice";
                },
                getAttributes: function () {
                    return ["id", "name", "description"];
                },
                getNsUri: function () {
                    return "http://allianz.com/20151023_05/model/test";
                },
                getReferences: function () {
                    return ["from", "to"];
                }
            };
        };
        return Slice;
    })(model.SpecmateModelObject);
    model.Slice = Slice;
    var TestProcedure = (function (_super) {
        __extends(TestProcedure, _super);
        function TestProcedure(baseUrl, internalUri, json) {
            this.createNameObservable();
            this.createIdObservable();
            this.createDescriptionObservable();
            this.iconClass = "fa fa-list";
            _super.call(this, baseUrl, internalUri, json);
            this.createSlicesObservable();
            this.createContentObservable();
        }
        TestProcedure.prototype.createContentObservable = function () {
            this.contents = this.slices;
        };
        TestProcedure.prototype.createSlicesObservable = function () {
            var _this = this;
            if (!this.slices) {
                this.slices = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/slices", function (result) {
                        _this.slices(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
        };
        TestProcedure.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        TestProcedure.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        TestProcedure.prototype.createNameObservable = function () {
            if (!this.name) {
                this.name = ko.observable("");
            }
            return this.name;
        };
        TestProcedure.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "description":
                    return this.description();
                    break;
                case "slices":
                    return this.slices;
                case "contents":
                    return this.contents;
                default: throw new Error("TestProcedure: Feature not defined: " + feature);
            }
        };
        TestProcedure.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "description":
                    this.description(value);
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        TestProcedure.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "TestProcedure";
                },
                getAttributes: function () {
                    return ["id", "name", "description"];
                },
                getNsUri: function () {
                    return "http://allianz.com/20151023_05/model/testprocedures";
                },
                getReferences: function () {
                    return [];
                }
            };
        };
        return TestProcedure;
    })(model.SpecmateModelObject);
    model.TestProcedure = TestProcedure;
    var SliceProcedure = (function (_super) {
        __extends(SliceProcedure, _super);
        function SliceProcedure(baseUrl, internalUri, json) {
            this.createNameObservable();
            this.createIdObservable();
            this.createStepsObservable();
            this.createDescriptionObservable();
            this.iconClass = "fa fa-sort-numeric-asc";
            _super.call(this, baseUrl, internalUri, json);
        }
        SliceProcedure.prototype.createStepsObservable = function () {
            var _this = this;
            if (!this.contents) {
                this.contents = ko.onDemandObservableArray(function () {
                    $.getJSON(_this.getBaseUrl() + "/" + _this.getInternalUri() + "/steps", function (result) {
                        _this.contents(result.map(function (json) { return model.modelFactory.getModelFromJSON(json); }));
                    });
                });
            }
            this.steps = this.contents;
        };
        SliceProcedure.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        SliceProcedure.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        SliceProcedure.prototype.createNameObservable = function () {
            if (!this.name) {
                this.name = ko.observable("");
            }
            return this.name;
        };
        SliceProcedure.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "description":
                    return this.description();
                    break;
                case "steps":
                    return this.contents;
                    break;
                default: throw new Error("SliceProcedure: Feature not defined: " + feature);
            }
        };
        SliceProcedure.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "description":
                    this.description(value);
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        SliceProcedure.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "SliceProcedure";
                },
                getAttributes: function () {
                    return ["id", "name", "description"];
                },
                getNsUri: function () {
                    return "http://allianz.com/20151023_05/model/testprocedures";
                },
                getReferences: function () {
                    return [];
                }
            };
        };
        return SliceProcedure;
    })(model.SpecmateModelObject);
    model.SliceProcedure = SliceProcedure;
    var TestProcedureAction = (function (_super) {
        __extends(TestProcedureAction, _super);
        /* Observable for resource content to bind against */
        function TestProcedureAction(baseUrl, internalUri, json) {
            this.createNameObservable();
            this.createIdObservable();
            this.createDescriptionObservable();
            _super.call(this, baseUrl, internalUri, json);
        }
        TestProcedureAction.prototype.createIdObservable = function () {
            if (!this.id) {
                this.id = ko.observable("");
            }
        };
        TestProcedureAction.prototype.createDescriptionObservable = function () {
            if (!this.description) {
                this.description = ko.observable("");
            }
        };
        TestProcedureAction.prototype.createNameObservable = function () {
            if (!this.name) {
                this.name = ko.observable("");
            }
            return this.name;
        };
        TestProcedureAction.prototype._get = function (feature) {
            switch (feature) {
                case "id":
                    return this.id();
                    break;
                case "name":
                    return this.name();
                    break;
                case "description":
                    return this.description();
                    break;
                default: throw new Error("TestProcedureAction: Feature not defined: " + feature);
            }
        };
        TestProcedureAction.prototype._set = function (feature, value) {
            switch (feature) {
                case "id":
                    this.id(value);
                    break;
                case "name":
                    this.name(value);
                    break;
                case "description":
                    this.description(value);
                    break;
                default:
                    throw new Error("Feature not defined or not writable: " + feature);
            }
        };
        TestProcedureAction.prototype._getMetaData = function () {
            return {
                getClassName: function () {
                    return "TestProcedureAction";
                },
                getAttributes: function () {
                    return ["id", "name", "description"];
                },
                getNsUri: function () {
                    return "http://allianz.com/20151023_05/model/testprocedures";
                },
                getReferences: function () {
                    return [];
                }
            };
        };
        return TestProcedureAction;
    })(model.SpecmateModelObject);
    model.TestProcedureAction = TestProcedureAction;
})(model || (model = {}));
/*Singleton*/ var EditorsRegistry;
(function (EditorsRegistry) {
    function getEditorTemplate(className) {
        switch (className) {
            case "Blueprint":
            case "Action": return "blueprint-editor-template";
            case "SliceProcedure": return "slice-procedure-editor-template";
            default: return "";
        }
    }
    EditorsRegistry.getEditorTemplate = getEditorTemplate;
    function getMetaDataEditorTemplate(className) {
        switch (className) {
            case "Slice": return "slice-meta-editor-template";
            default: return "";
        }
    }
    EditorsRegistry.getMetaDataEditorTemplate = getMetaDataEditorTemplate;
})(EditorsRegistry || (EditorsRegistry = {}));
var components;
(function (components) {
    ko.components.register('editors', {
        viewModel: { createViewModel: function (params) {
                return params.editorItem;
            } },
        template: '\
    <div class="specmate-editors-meta-data">\
        <div class="specmate-column-header">&nbsp;</div>\
        <div class="specmate-editors-meta-data-content">\
            <!-- <div class="specmate-editor-meta-data-breadcrumb">...&nbsp;&nbsp;/&nbsp;&nbsp;Level 1&nbsp;&nbsp;/&nbsp;&nbsp;Level 3&nbsp;&nbsp;/&nbsp;&nbsp;Level 4</div> -->\
            <div class="specmate-editor-panel">\
                <span class="specmate-editor-label">Name:</span><br/>\
                <input class="specmate-editor-meta-data-name" type="text" class="specmate-editors-title" data-bind="value:name" />\
            </div>\
            <div class="specmate-editor-panel">\
                <span class="specmate-editor-label">Description:</span><br/>\
                <textarea class="specmate-editor-meta-data-description" data-bind="value:description" />\
            </div>\
            <!-- ko if: typeof optional !== "undefined" -->\
            <div class="specmate-editor-panel">\
                <span class="specmate-editor-label">Other:</span><br/>\
                <input type="checkbox" data-bind="checked:optional" id="cb_optional"/><label class="specmate-editor-desc-label" for="cb_optional">optional</label>\
            </div>\
            <!-- /ko -->\
            <!-- ko template:{name:EditorsRegistry.getMetaDataEditorTemplate(_getMetaData().getClassName()), data:$data} -->\
            <!-- /ko -->\
        </div>\
    </div>\
    <div class="specmate-editors-content">\
        <!-- ko template:{name:EditorsRegistry.getEditorTemplate(_getMetaData().getClassName()), data:$data} -->\
        <!-- /ko -->\
    </div>'
    });
})(components || (components = {}));
var components;
(function (components) {
    ko.components.register("project-explorer", {
        viewModel: {
            createViewModel: function (params) {
                return {
                    resource: params.resource,
                    selectionObservable: params.selectionObservable,
                    secondLevel: params.secondLevel
                };
            }
        },
        template: '\
<script type="text/html" id="title-template">\
   <li  class="truncate"><a class="specmate-project-explorer-open-indicator" data-bind="click:function(){$component.secondLevel?toggleOpen2():toggleOpen();}">\
    <img src="images/triangle-open.png" width="10px" data-bind="visible:$component.secondLevel?opened2():opened()" />\
    <img src="images/triangle-closed.png" height="10px" data-bind="visible:!($component.secondLevel?opened2():opened())" />\
    </a>\
    <!-- ko if:($data.iconClass) --><i data-bind="css: iconClass"></i><!-- /ko --> \
    <a href="" class="specmate-project-explorer-link" data-bind="text: name, click:$component.selectionObservable, style:{fontWeight: ($component.selectionObservable() && $component.selectionObservable().url==$data.url) ? \'bold\' : \'normal\'}"></a>\
</script>\
<script type="text/html" id="nav-template">\
    <!-- ko template:"title-template" --><!-- /ko -->\
    <!-- ko if:($component.secondLevel?opened2():opened()) -->\
    <ul>\
        <!-- ko if:($data.contents) -->\
            <!-- ko template: {name: \'nav-template\', foreach: contents} --> <!-- /ko -->\
        <!-- /ko -->\
    </ul>\
    <!-- /ko -->\
    </li>\
</script>\
\
<div class="specmate-project-explorer-content">\
<ul class="specmate-project-explorer-root">\
    <!-- ko template: {name: \'nav-template\', foreach: resource.contents} --> <!-- /ko -->\
</ul>\
</div>'
    });
})(components || (components = {}));
var components;
(function (components) {
    ko.components.register("action-bar", {
        viewModel: {
            createViewModel: function (params) {
                return {
                    selectionObservable: params.selectionObservable,
                };
            }
        },
        template: '\
<div class="specmate-column-header">\
    <div class="specmate-column-header-menu">\
    <a class="specmate-action-link" href="" data-bind="click:model.modelFactory.newProject"><i class="fa fa-plus-circle" /> Project</a>\
    <!-- ko if:(selectionObservable() && selectionObservable()._getMetaData().getClassName()=="BaseModelNode") -->\
        <a class="specmate-action-link" href="" data-bind="click:function(){model.modelFactory.newBaseModelNode(selectionObservable())}"><i class="fa fa-plus-circle" /> Folder</a>\
        <a class="specmate-action-link" href="" data-bind="click:function(){model.modelFactory.newBlueprint(selectionObservable())}"><i class="fa fa-plus-circle" /> Blueprint</a>\
        <a class="specmate-action-link" href="" data-bind="click:function(){model.modelFactory.newTestSpecification(selectionObservable())}"><i class="fa fa-plus-circle" /> Test Specification</a>\
    <!-- /ko -->\
    <!-- ko if:(selectionObservable() && selectionObservable()._getMetaData().getClassName()=="TestSpecification") -->\
        <a class="specmate-action-link" href="" data-bind="click:function(){model.modelFactory.newSlice(selectionObservable())}"><i class="fa fa-plus-circle" /> Slice</a>&nbsp;\
    <!-- /ko -->\
    <!-- ko if:(selectionObservable()) -->\
        <a class="specmate-action-link" href="" data-bind="click:function(){console.log(\'delete\');selectionObservable().delete();}"><i class="fa fa-trash" /> Delete</a>&nbsp;\
    <!-- /ko -->\
    </div>\
</div>'
    });
})(components || (components = {}));
//# sourceMappingURL=specmate.js.map