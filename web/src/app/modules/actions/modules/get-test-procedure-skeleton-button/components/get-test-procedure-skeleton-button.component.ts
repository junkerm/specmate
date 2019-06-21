import { LowerCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { saveAs } from 'file-saver';
import { TestProcedure } from '../../../../../model/TestProcedure';
import { TestProcedureSkeleton } from '../../../../../model/TestProcedureSkeleton';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'get-test-procedure-skeleton-button',
    templateUrl: 'get-test-procedure-skeleton-button.component.html',
    styleUrls: ['get-test-procedure-skeleton-button.component.css']
})

export class GetTestProcedureSkeletonButton {

    private _testprocedure: TestProcedure;

    private _lang: string;

    private static UTF8_BOM = '\ufeff';

    @Input()
    public set testprocedure(testprocedure: TestProcedure) {
        if (!testprocedure) {
            return;
        }
        this._testprocedure = testprocedure;
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

        const data: TestProcedureSkeleton = await this.dataService.performQuery(this._testprocedure.url, 'generateTestSkeleton',
            { language: new LowerCasePipe().transform(this._lang)});

        if (data === undefined) {
            throw new Error('Could not load test procedure skeleton for ' + this._lang);
        }

        saveAs(new Blob([GetTestProcedureSkeletonButton.UTF8_BOM + data.code], {type: 'text/plain;charset=utf-8'}), data.name);
    }

    public get language(): string {
        return this._lang;
    }

    public get enabled(): boolean {
        if (this._testprocedure === undefined) {
            return false;
        }

        return true;
    }
}
