import {IContainer} from '../../model/IContainer';
import { ELogSeverity } from './e-log-severity'

export class LogElement {
    constructor(public message: string, public severity: ELogSeverity, public time: Date, public element?: IContainer) { }
}