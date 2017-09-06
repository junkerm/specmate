import { LogElement } from '../../../services/logging/log-element';
import { Component, Input } from '@angular/core';
import { LogPresentation } from "./log-presentation";

@Component({
    moduleId: module.id,
    selector: '[log-entry]',
    templateUrl: 'log-entry.component.html'
})
export class LogEntry {

    @Input()
    public logElement: LogElement;

    constructor() { }
    
    public get icon(): string {
        return LogPresentation.icon(this.logElement.severity);
    }

    public get color(): string {
        return LogPresentation.color(this.logElement.severity);
    }
}
