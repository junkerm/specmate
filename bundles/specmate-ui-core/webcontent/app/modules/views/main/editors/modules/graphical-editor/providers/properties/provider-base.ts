import { Type } from '../../../../../../../../util/type';
import { CEGModel } from '../../../../../../../../model/CEGModel';
import { Process } from '../../../../../../../../model/Process';

export abstract class ProviderBase {
    constructor(protected modelType: {className: string}) { }

    protected get isCEGModel(): boolean {
        return Type.is(this.modelType, CEGModel);
    }

    protected get isProcessModel(): boolean {
        return Type.is(this.modelType, Process);
    }
}
