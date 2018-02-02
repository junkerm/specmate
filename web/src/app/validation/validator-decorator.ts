import 'reflect-metadata';
import { ElementValidatorBase } from './element-validator-base';
import { ISpecmateModelObject } from '../model/ISpecmateModelObject';
import { IContainer } from '../model/IContainer';
import { IContentElement } from '../model/IContentElement';
import { Injector, ReflectiveInjector } from '@angular/core';
import { ValidationService } from '../modules/forms/modules/validation/services/validation.service';
import { ValidationResult } from './validation-result';
import { CEGModel } from '../model/CEGModel';
import { DuplicateNodeValidator } from './ceg/duplicate-node-validator';

export function Validator() {
    return function(validatorTarget: any) {
        const injector: Injector = ReflectiveInjector.resolveAndCreate([ValidationService]);
        const validationService: ValidationService = injector.get(ValidationService);

        if (!(validatorTarget.prototype instanceof ElementValidatorBase)) {
            throw new Error('When you are using the @Validator-decorator, you must directly extend ElementValidatorBase');
        }

        console.log(Reflect.getMetadata('design:type', validatorTarget.prototype.validate));

        validationService.registerElementValidator(CEGModel, validatorTarget);
    };
}
