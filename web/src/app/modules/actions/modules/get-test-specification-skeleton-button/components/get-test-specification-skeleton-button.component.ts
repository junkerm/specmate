import { Component, Input } from '@angular/core';
import { IContainer } from '../../../../../model/IContainer';
import { CEGModel } from '../../../../../model/CEGModel';
import { Process } from '../../../../../model/Process';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { Id } from '../../../../../util/id';
import { Url } from '../../../../../util/url';
import { Config } from '../../../../../config/config';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { ValidationResult } from '../../../../../validation/validation-result';
import { TranslateService } from '@ngx-translate/core';
import { ElementFactoryBase } from '../../../../../factory/element-factory-base';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { saveAs } from 'file-saver';
import { LowerCasePipe } from '@angular/common';

@Component({
    moduleId: module.id.toString(),
    selector: 'get-test-specification-skeleton-button',
    templateUrl: 'get-test-specification-skeleton-button.component.html',
    styleUrls: ['get-test-specification-skeleton-button.component.css']
})

export class GetTestSpecificationSkeletonButton {

    private _testspecification: TestSpecification;

    private _lang: string;

    @Input()
    public set testspecification(testspecification: TestSpecification) {
        if (!testspecification) {
            return;
        }
        this._testspecification = testspecification;
    }

    @Input()
    public set language(lang: string) {
      this._lang = lang;
    }

    constructor(private dataService: SpecmateDataService,
        private modal: ConfirmationModal,
        private navigator: NavigatorService,
        private validator: ValidationService,
        private logger: LoggingService,
        private translate: TranslateService) { }

    public async getskeleton(): Promise<void> {
        if (!this.enabled) {
            return;
        }

        const data = await this.dataService.performQuery(this._testspecification.url, 'generateTestSkeleton',
            { language: new LowerCasePipe().transform(this._lang)});

        if (data === undefined) {
            throw new Error('Could not load test specification skeleton for ' + this._lang);
        }

        saveAs(new Blob([JSON.stringify(data)], {type: 'text/json;charset=utf-8'}), this.filename);
    }

    public get language(): string {
        return this._lang;
    }

    public get filename(): string {
        return this._testspecification.name + '_' + new LowerCasePipe().transform(this._lang) + '.json';
    }

    public get enabled(): boolean {
        if (this._testspecification === undefined) {
            return false;
        }

        return true;
    }
}
