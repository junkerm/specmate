import { ELogSeverity } from '../services/e-log-severity';
import { Config } from '../../../../../../config/config';

export class LogPresentation {

    public static ERROR: string = 'Error';
    public static WARN: string = 'Warning';
    public static INFO: string = 'Info';
    public static DEBUG: string = 'Debug';
    

    public static iconForStr(severity: string): string {
        return LogPresentation.icon(LogPresentation.getSeverityForString(severity));
    }

    public static colorForStr(severity: string): string {
        return LogPresentation.color(LogPresentation.getSeverityForString(severity));
    }

    public static color(severity: ELogSeverity): string {
        if(severity == ELogSeverity.ERROR) {
            return 'danger';
        }
        if(severity == ELogSeverity.WARN) {
            return 'warning';
        }
        if(severity == ELogSeverity.INFO) {
            return 'primary';
        }
        if(severity == ELogSeverity.DEBUG) {
            return 'success';
        }
        return Config.LOG_DEFAULT_COLOR;
    }
    
    public static icon(severity: ELogSeverity): string {
        if(severity == ELogSeverity.ERROR) {
            return 'warning';
        }
        if(severity == ELogSeverity.WARN) {
            return 'warning';
        }
        if(severity == ELogSeverity.INFO) {
            return 'info-circle';
        }
        if(severity == ELogSeverity.DEBUG) {
            return 'bug';
        }
        return Config.LOG_DEFAULT_ICON;
    }

    public static getSeverityForString(severity: string): ELogSeverity {
        if(severity === LogPresentation.ERROR) {
            return ELogSeverity.ERROR;
        }
        if(severity === LogPresentation.WARN) {
            return ELogSeverity.WARN;
        }
        if(severity === LogPresentation.INFO) {
            return ELogSeverity.INFO;
        }
        if(severity === LogPresentation.DEBUG) {
            return ELogSeverity.DEBUG;
        }
        return undefined;
    }

    public static getStringForSeverity(severity: ELogSeverity): string {
        if(severity === ELogSeverity.ERROR) {
            return LogPresentation.ERROR;
        }
        if(severity === ELogSeverity.WARN) {
            return LogPresentation.WARN;
        }
        if(severity === ELogSeverity.INFO) {
            return LogPresentation.INFO;
        }
        if(severity === ELogSeverity.DEBUG) {
            return LogPresentation.DEBUG;
        }
        return undefined;
    }
}