import { LowerCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { saveAs } from 'file-saver';
import { TestSpecification } from '../../../../../model/TestSpecification';
import { TestSpecificationSkeleton } from '../../../../../model/TestSpecificationSkeleton';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { IContainer } from '../../../../../model/IContainer';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-export-button',
    templateUrl: 'test-export-button.component.html',
    styleUrls: ['test-export-button.component.css']
})

export class TestExportButton {

    private _element: IContainer;

    private _lang: string;

    private static UTF8_BOM = '\ufeff';

    @Input()
    public set element(element: IContainer) {
        if (!element) {
            return;
        }
        this._element = element;
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

        const data: TestSpecificationSkeleton = await this.dataService.performQuery(this._element.url, 'export',
            { language: new LowerCasePipe().transform(this._lang)});

        if (data === undefined) {
            throw new Error('Could not load test specification skeleton for ' + this._lang);
        }

        saveAs(new Blob([TestExportButton.UTF8_BOM + data.code], {type: 'text/plain;charset=utf-8'}), data.name);
    }

    public get language(): string {
        return this._lang;
    }

    public get enabled(): boolean {
        if (this._element === undefined) {
            return false;
        }

        return true;
    }
}
