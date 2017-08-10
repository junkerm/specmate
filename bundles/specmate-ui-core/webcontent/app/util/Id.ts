import { Config } from '../config/config';
import { IContainer } from "../model/IContainer";
import { UUID } from 'angular2-uuid';

export class Id {
    /** Generates a new id (we hope we do not get duplicates. We do not check for that.) */
    public static get uuid(): string {
        return UUID.UUID();
    }
}