import { Component, Input, OnInit } from '@angular/core';
import { IContainer } from '../../../../../../model/IContainer';
import { Requirement } from '../../../../../../model/Requirement';
import { Proxy } from '../../../../../../model/support/proxy';
import { Type } from '../../../../../../util/type';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';

/** This component displays a trace link. It takes a trace link proxy as input. */
@Component({
    moduleId: module.id.toString(),
    selector: 'tracing-link',
    templateUrl: 'tracing-link.component.html'
})
export class TracingLink implements OnInit {

    /** The trace link proxy */
    private _traceProxy: Proxy;

    /** The resolved trace link */
    trace: IContainer;
    extId: string;

    /** constructor */
    public constructor(private dataService: SpecmateDataService) {
    }

    /** Sets a new trace link proxy to be displayed */
    @Input()
    set traceProxy(traceProxy: Proxy) {
        this._traceProxy = traceProxy;
    }

    ngOnInit() {
        this.dataService.readElement(this._traceProxy.url, true).then(trace => {
        this.trace = trace;
        if (Type.is(this.trace, Requirement)) {
          this.extId = (trace as Requirement).extId;
        }
      });
    }
}
