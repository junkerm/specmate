import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { Config } from '../../../../../../config/config';
import { ELogSeverity } from './e-log-severity';
import { LogElement } from './log-element';

@Injectable()
export class LoggingService {
    private logHistory: LogElement[] = [];

    private logSubject: BehaviorSubject<LogElement>;
    public logObservable: Observable<LogElement>;

    constructor() {
        this.logSubject = new BehaviorSubject<LogElement>(new LogElement(Config.LOG_START_MESSAGE, ELogSeverity.INFO, new Date()));
        this.logObservable = this.logSubject.asObservable();
    }

    public get logs(): LogElement[] {
        return this.logHistory;
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
        let logElement: LogElement = new LogElement(message, severity, new Date(), url);
        this.logHistory.unshift(logElement);
        if (this.logHistory.length > Config.LOG_LENGTH) {
            this.logHistory = this.logHistory.slice(0, Config.LOG_LENGTH);
        }
        this.logSubject.next(logElement);
    }
}
