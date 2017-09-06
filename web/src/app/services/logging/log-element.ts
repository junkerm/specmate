import {IContainer} from '../../model/IContainer';
import { ELogSeverity } from './e-log-severity'

export class LogElement {
    constructor(public message: string, public severity: ELogSeverity, public time: Date, public url?: string) { }

    public get isError(): boolean {
        return this.isSeverity(ELogSeverity.ERROR);
    }

    public get isWarn(): boolean {
        return this.isSeverity(ELogSeverity.WARN);
    }

    public get isInfo(): boolean {
        return this.isSeverity(ELogSeverity.INFO);
    }

    public get isDebug(): boolean {
        return this.isSeverity(ELogSeverity.DEBUG);
    }

    private isSeverity(severity: ELogSeverity): boolean {
        return this.severity === severity;
    }
}