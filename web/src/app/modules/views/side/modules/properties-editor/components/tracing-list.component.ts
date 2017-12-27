import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { Input, OnInit, ChangeDetectorRef, IterableDiffers } from '@angular/core';
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
    selector: 'tracing-list',
    templateUrl: 'tracing-list.component.html'
})
export class TracingList {

    /**  */
    _traceProxies: Proxy[];

    traces: IContainer[];

    differ: any;

    /** constructor */
    public constructor(private dataService: SpecmateDataService,  differs: IterableDiffers) {
        this.differ = differs.find([]).create(null);
    }

    /** Sets a new object to be edited */
    @Input()
    set traceProxies(traceProxies: Proxy[]) {
        this._traceProxies = traceProxies;
    }

    /** formats a specmate object. called by typeahead */
    formatter(toFormat: ISpecmateModelObject): string {
        return toFormat.name;
    }

    /** Resolves the trace references */
   ngDoCheck() {
        let change = this.differ.diff(this._traceProxies);
        if (change) {
            let promises = this._traceProxies.map(proxy =>
                this.dataService.readElement(proxy.url, true)
            );
            Promise.all(promises).then(result => this.traces = result);
        }
    }

}
