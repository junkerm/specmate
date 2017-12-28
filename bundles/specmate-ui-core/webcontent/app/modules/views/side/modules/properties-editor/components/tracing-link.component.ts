import { Input, OnInit, Component } from '@angular/core';
import { ISpecmateModelObject } from '../../../../../../model/ISpecmateModelObject';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { IContainer } from '../../../../../../model/IContainer';
import { Proxy } from '../../../../../../model/support/proxy';

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

 /** constructor */
    public constructor(private dataService: SpecmateDataService) {
    }

    /** Sets a new trace link proxy to be displayed */
    @Input()
    set traceProxy(traceProxy: Proxy) {
        this._traceProxy = traceProxy;
    }

    ngOnInit() {
        this.dataService.readElement(this._traceProxy.url, true).then(trace =>
        this.trace = trace);
    }
}
