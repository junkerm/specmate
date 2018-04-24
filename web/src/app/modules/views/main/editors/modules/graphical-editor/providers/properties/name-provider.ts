import { ProviderBase } from './provider-base';
import { IContainer } from '../../../../../../../../model/IContainer';
import { TranslateService } from '@ngx-translate/core';

export class NameProvider extends ProviderBase {
    constructor(model: IContainer, private translate: TranslateService) {
        super(model);
    }

    public get name(): string {
        if (this.isCEGModel) {
            return this.translate.instant('causeEffectGraph');
        } else if (this.isProcessModel) {
            return this.translate.instant('processModel');
        }
    }
}
