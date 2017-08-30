import { IContainer } from '../../model/IContainer';
import { Injectable,Inject, forwardRef } from '@angular/core';
import { LogElement } from './log-element';
import { ELogSeverity } from './e-log-severity';

@Injectable()
export class LoggingService {
    private logHistory: LogElement[] = [];

    public get logs(): LogElement[] {
        return this.logHistory.reverse();
    }

    public debug(message: string, element?: IContainer): void {
        this.log(message, ELogSeverity.DEBUG, element);
    }

    public info(message: string, element?: IContainer): void {
        this.log(message, ELogSeverity.INFO, element);
    }

    public warn(message: string, element?: IContainer): void {
        this.log(message, ELogSeverity.WARN, element);
    }

    public error(message: string, element?: IContainer): void {
        this.log(message, ELogSeverity.ERROR, element);
    }

    private log(message: string, severity: ELogSeverity, element?: IContainer): void {
        this.logHistory.push(new LogElement(message, severity, new Date(), element));
    }
}
