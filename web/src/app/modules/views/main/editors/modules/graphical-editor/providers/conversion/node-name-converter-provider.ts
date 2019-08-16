import { ProviderBase } from '../properties/provider-base';
import { ConverterBase } from '../../converters/converter-base';
import { VariableConditionToNameConverter } from '../../converters/variable-condition-name-converter';

export class NodeNameConverterProvider extends ProviderBase {
    public get nodeNameConverter(): ConverterBase<any, string> {
        if (this.isCEGModel) {
            return new VariableConditionToNameConverter();
        } else {
            return undefined;
        }
    }
}
