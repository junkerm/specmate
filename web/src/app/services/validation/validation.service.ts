import { Injectable } from "@angular/core";
import { IContainer } from "../../model/IContainer";
import { Type } from "../../util/Type";
import { CEGNode } from "../../model/CEGNode";
import { ValidatorBase } from "./validator-base";
import { CEGConnection } from "../../model/CEGConnection";
import { ProcessStep } from "../../model/ProcessStep";
import { ProcessStart } from "../../model/ProcessStart";
import { ProcessEnd } from "../../model/ProcessEnd";
import { ProcessDecision } from "../../model/ProcessDecision";
import { ProcessConnection } from "../../model/ProcessConnection";
import { CEGModel } from "../../model/CEGModel";
import { Process } from "../../model/Process";
import { RequiredFieldsValidator } from "./required-fields-validator";
import { FieldMetaItem, MetaInfo } from "../../model/meta/field-meta";

@Injectable()
export class ValidationService {

    private validatorMap: {[className: string]: ValidatorBase<IContainer>};

    public isValid(element: IContainer): boolean {
        return this.getValidator(element).isValid(element);
    }

    public allValid(contents: IContainer[]): boolean {
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
