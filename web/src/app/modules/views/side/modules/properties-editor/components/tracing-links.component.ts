import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { Input, OnInit } from '@angular/core';
import { ISpecmateModelObject } from '../../../../../../model/ISpecmateModelObject';
import { Component } from '@angular/core';
import { Requirement } from '../../../../../../model/Requirement';
import { IContainer } from '../../../../../../model/IContainer';
import { TestSpecification } from '../../../../../../model/TestSpecification';
import { Observable } from 'rxjs/Rx';
import { of } from 'rxjs/observable/of';
import { NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import 'rxjs/add/operator/map';
import { Proxy } from '../../../../../../model/support/proxy';
import { Id } from '../../../../../../util/id';

@Component({
    moduleId: module.id.toString(),
    selector: 'tracing-links',
    templateUrl: 'tracing-links.component.html'
})
export class TracingLinks  {

    /** The specmate object for which the traces are edited */
    private _model: ISpecmateModelObject;

    /** The current traces of the objevt */
    traces: IContainer[];

    /** The object that is added as new trace */
    newTrace: ISpecmateModelObject;

    /** constructor */
    public constructor(private  dataService: SpecmateDataService) {}

    /** Sets a new object to be edited */
    @Input()
    set model(model: ISpecmateModelObject) {
        this._model = model;
        this.updateTraces();
    }

    /** getter */
    get model() {
        return this._model;
    }

    /** formats a specmate object. called by typeahead */
    formatter(toFormat: ISpecmateModelObject): string {
        return toFormat.name;
    }

    /** Resolves the trace references */
    updateTraces() {
        let tracePromises = this.model.tracesTo.map(
            proxy =>  this.dataService.readElement(proxy.url)
        );
        Promise.all(tracePromises).then(
            traces => this.traces = traces
        );
    }

    /** called when an item is selected in the typeahead */
    selectItem(event: NgbTypeaheadSelectItemEvent, reqtypeahead: any) {
        event.preventDefault();
        let trace: Proxy = new Proxy();
        trace.url = event.item.url;
        this.model.tracesTo.push(trace);
        this.dataService.updateElement(this.model, true, Id.uuid).then(
            () => this.updateTraces()
        );
        reqtypeahead.value = '';
    }

    /** searches suggestions based on the typed text */
    search = (text$: Observable<string>) => {
       return text$
        .debounceTime(300)
        .distinctUntilChanged()
        .switchMap(term =>
            this.dataService.search(term)
            .catch(() => {
                return of([]);
            }));
    }
}
