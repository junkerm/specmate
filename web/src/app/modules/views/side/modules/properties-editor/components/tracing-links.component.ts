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
export class TracingLinks  {

    private _model: ISpecmateModelObject;

    @Input()
    set model(model: ISpecmateModelObject) {
        this._model = model;
        this.updateTraces();
    }

    get model() {
        return this._model;
    }

    traces: IContainer[];

    public constructor(private  dataService: SpecmateDataService) {}

    updateTraces() {
        let tracePromises = this.model.tracesTo.map(
            proxy =>  this.dataService.readElement(proxy.url)
        );
        Promise.all(tracePromises).then(
            traces => this.traces = traces
        );
    }
}
