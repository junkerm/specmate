import { MetaInfo } from "../model/meta/field-meta";
import { ValidationResult } from "./validation-result";

export class ValidationUtil {
    /** Validates if a name matches the rules described in the INamed meta property */
    public static isValidName(name: string): boolean{
        if (MetaInfo.INamed === undefined || MetaInfo.INamed[0] === undefined) {
            return true;
        }
        let validPattern = MetaInfo.INamed[0].allowedPattern;
        if (validPattern === undefined) {
            return true;
        }
        let validName: RegExp = new RegExp(validPattern);
        if (name === undefined) {
            return true;
        }
        if (!name.match(validName)) {
            return false;
        }
        return true;
    }
}