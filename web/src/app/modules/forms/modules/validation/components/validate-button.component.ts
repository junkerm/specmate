import { Component, Input } from '@angular/core';
import { ValidationService } from '../services/validation.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'validate-button',
    templateUrl: 'validate-button.component.html',
    styleUrls: ['validate-button.component.css']
})

export class ValidateButton {

    @Input()
    public textEnabled = true;

    constructor(private validationService: ValidationService) { }

    public validate(): Promise<void> {
        return this.validationService.validateCurrent();
    }

    public cancelEvent(event: Event): void {
        event.preventDefault();
        event.stopPropagation();
    }
}
