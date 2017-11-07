import { TestSpecificationGenerator } from './test-specification-generator';
import { Component, Input } from '@angular/core';
import { CEGModel } from '../../../model/CEGModel';
import { Process } from '../../../model/Process';

@Component({
    moduleId: module.id,
    selector: 'test-specification-generator-button',
    templateUrl: 'test-specification-generator-button.component.html',
    styleUrls: ['test-specification-generator-button.component.css']
})

export class TestSpecificationGeneratorButton {

    @Input()
    public generator: TestSpecificationGenerator;

    @Input()
    public model: CEGModel | Process;

    public generate(): void {
        this.generator.generateTestSpecification(this.model);
    }
    
    public get enabled(): boolean {
        return this.generator.canCreateTestSpecification(this.model);
    }

    public get errors(): string[] {
        return this.generator.getErrors(this.model);
    }
}
