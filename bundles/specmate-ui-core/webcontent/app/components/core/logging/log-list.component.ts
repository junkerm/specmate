import { LoggingService } from '../../../services/logging/logging.service';
import { LogElement } from '../../../services/logging/log-element';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from "@angular/forms";
import { ELogSeverity } from "../../../services/logging/e-log-severity";
import { Arrays } from "../../../util/Arrays";
import { LogPresentation } from "./log-presentation";
import { ViewControllerService } from "../../../services/view/view-controller.service";

@Component({
    moduleId: module.id,
    selector: 'log-list',
    templateUrl: 'log-list.component.html'
})
export class LogList {

    public errorName = LogPresentation.ERROR;
    public warnName = LogPresentation.WARN;
    public infoName = LogPresentation.INFO;
    public debugName = LogPresentation.DEBUG;

    public severityNames: string[] = [this.errorName, this.warnName, this.infoName, this.debugName];

    public checkboxGroupForm: FormGroup;

    public get log(): LogElement[] {
        return this.logger.logs.filter((logElement: LogElement) => this.isVisible(logElement));
    }

    constructor(private logger: LoggingService, private viewController: ViewControllerService, private formBuilder: FormBuilder) {
        let formGroupObj: any = {};
        this.severityNames.forEach((severityName: string) => formGroupObj[severityName] = true);
        this.checkboxGroupForm = this.formBuilder.group(formGroupObj);
    }

    public closeLog(): void {
        this.viewController.hideLoggingOutput();
    }

    private isChecked(severityName: string): boolean {
        return this.checkboxGroupForm.value[severityName];
    }

    private isSeverityActive(severity: ELogSeverity): boolean {
        return this.isChecked(LogPresentation.getStringForSeverity(severity));
    }

    private isVisible(logElement: LogElement): boolean {
        return this.isSeverityActive(logElement.severity);
    }

    public icon(severityName: string): string {
        return LogPresentation.iconForStr(severityName);
    }

    public color(severityName: string): string {
        return LogPresentation.colorForStr(severityName);
    }
}
