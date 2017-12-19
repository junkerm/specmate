import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { Input, OnInit } from '@angular/core';
import { ISpecmateModelObject } from '../../../../../../model/ISpecmateModelObject';
import { Component } from '@angular/core';
import { Requirement } from '../../../../../../model/Requirement';
import { IContainer } from '../../../../../../model/IContainer';
import { TestSpecification } from '../../../../../../model/TestSpecification';

@Component({
    moduleId: module.id.toString(),
    selector: 'tracing-links',
    templateUrl: 'tracing-links.component.html'
})
export class TracingLinks implements OnInit {
    @Input()
    model: ISpecmateModelObject;

    traces: IContainer[];

    public constructor(private  dataService: SpecmateDataService) {}

    ngOnInit() {
        let tracePromises = this.model.tracesTo.map(
            proxy =>  this.dataService.readElement(proxy.url)
        );
        Promise.all(tracePromises).then(
            traces => this.traces = traces
        );
    }
}
