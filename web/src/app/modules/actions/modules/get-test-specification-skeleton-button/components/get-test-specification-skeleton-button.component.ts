import { LowerCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { saveAs } from 'file-saver';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { TestSpecificationSkeleton } from '../../../../../model/TestSpecificationSkeleton';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'get-test-specification-skeleton-button',
    templateUrl: 'get-test-specification-skeleton-button.component.html',
    styleUrls: ['get-test-specification-skeleton-button.component.css']
})

export class GetTestSpecificationSkeletonButton {

    private _testspecification: TestSpecification;

    private _lang: string;

    private static UTF8_BOM = '\ufeff';

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

        saveAs(new Blob([GetTestSpecificationSkeletonButton.UTF8_BOM + data.code], {type: 'text/plain;charset=utf-8'}), data.name);
    }

    public get language(): string {
        return this._lang;
    }

    public get enabled(): boolean {
        if (this._testspecification === undefined) {
            return false;
        }

        return true;
    }
}
