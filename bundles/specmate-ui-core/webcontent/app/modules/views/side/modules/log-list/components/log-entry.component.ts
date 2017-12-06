import { Component, Input } from "@angular/core";
import { LogElement } from "../services/log-element";
import { LogPresentation } from "../base/log-presentation";

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
