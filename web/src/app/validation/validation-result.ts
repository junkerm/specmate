import { IContainer } from '../model/IContainer';

export class ValidationResult {
    constructor(public message: string, public isValid: boolean, public elements: IContainer[]) { }
    public static VALID: ValidationResult = new ValidationResult('', true, []);
}
