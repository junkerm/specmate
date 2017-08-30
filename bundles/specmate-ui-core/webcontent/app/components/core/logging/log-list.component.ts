import { LoggingService } from '../../../services/logging/logging.service';
import { LogElement } from '../../../services/logging/log-element';
import { Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'log-list',
    templateUrl: 'log-list.component.html'
})
export class LogList {

    public get log(): LogElement[] {
        return this.logger.logs;
    }

    constructor(private logger: LoggingService) { }
}
