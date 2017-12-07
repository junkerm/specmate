import { Injectable } from '@angular/core';
import { IContainer } from '../../../../../model/IContainer';
import { FieldMetaItem, MetaInfo } from '../../../../../model/meta/field-meta';
import { ValidatorBase } from '../base/validator-base';
import { RequiredFieldsValidator } from '../base/required-fields-validator';

@Injectable()
export class ValidationService {

    private validatorMap: {[className: string]: ValidatorBase<IContainer>};

    public isValid(element: IContainer): boolean {
        let valid: boolean = this.getValidator(element).isValid(element);
        return valid;
    }

    public allValid(contents: IContainer[]): boolean {
        if(!contents) {
            return true;
        }
        return !contents.some((element: IContainer) => !this.getValidator(element).isValid(element));
    }

    private getValidator(element: IContainer): ValidatorBase<IContainer> {
        if(!this.validatorMap) {
            this.validatorMap = {};
        }
        let type: string = element.className;
        if(!this.validatorMap[type]) {
            let fieldMetaInfo: FieldMetaItem[] = MetaInfo[type];
            let requiredFields: string[] = [];
            fieldMetaInfo.forEach((metaItem: FieldMetaItem) => {
                if(metaItem.required) {
                    requiredFields.push(metaItem.name);
                }
            });
            this.validatorMap[type] = new RequiredFieldsValidator(requiredFields);
        }
        
        return this.validatorMap[type];
    }
}
