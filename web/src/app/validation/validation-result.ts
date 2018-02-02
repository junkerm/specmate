export class ValidationResult {
    constructor(public message: string, public isValid: boolean) { }
    public static VALID: ValidationResult = new ValidationResult('', true);
}
