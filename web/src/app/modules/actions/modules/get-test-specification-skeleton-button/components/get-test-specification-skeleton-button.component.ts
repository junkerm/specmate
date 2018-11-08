import { Component, Input } from '@angular/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { ConfirmationModal } from '../../../../notification/modules/modals/services/confirmation-modal.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { ValidationService } from '../../../../forms/modules/validation/services/validation.service';
import { TranslateService } from '@ngx-translate/core';
import { LoggingService } from '../../../../views/side/modules/log-list/services/logging.service';
import { saveAs } from 'file-saver';
import { LowerCasePipe } from '@angular/common';
import { TestSpecificationSkeleton } from '../../../../../model/TestSpecificationSkeleton';

@Component({
    moduleId: module.id.toString(),
    selector: 'get-test-specification-skeleton-button',
    templateUrl: 'get-test-specification-skeleton-button.component.html',
    styleUrls: ['get-test-specification-skeleton-button.component.css']
})

export class GetTestSpecificationSkeletonButton {

    private _testspecification: TestSpecification;

    private _lang: string;

    private _extension: string;

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

    @Input()
    public set extension(ext: string) {
        this._extension = ext;
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

        const data: TestSpecificationSkeleton = await this.dataService.performQuery(this._testspecification.url, 'generateTestSkeleton',
            { language: new LowerCasePipe().transform(this._lang)});

        if (data === undefined) {
            throw new Error('Could not load test specification skeleton for ' + this._lang);
        }

        saveAs(new Blob([data.code], {type: 'text/plain;charset=utf-8'}), this.filename);
    }

    public get language(): string {
        return this._lang;
    }

    public get filename(): string {
        return this._testspecification.name + '.' + this._extension;
    }

    public get enabled(): boolean {
        if (this._testspecification === undefined) {
            return false;
        }

        return true;
    }
}
