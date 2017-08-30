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

    public debug(message: string, url?: string): void {
        this.log(message, ELogSeverity.DEBUG, url);
    }

    public info(message: string, url?: string): void {
        this.log(message, ELogSeverity.INFO, url);
    }

    public warn(message: string, url?: string): void {
        this.log(message, ELogSeverity.WARN, url);
    }

    public error(message: string, url?: string): void {
        this.log(message, ELogSeverity.ERROR, url);
    }

    private log(message: string, severity: ELogSeverity, url?: string): void {
        this.logHistory.push(new LogElement(message, severity, new Date(), url));
    }
}
