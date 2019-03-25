import { Injectable, EventEmitter } from '@angular/core';
import { Requirement } from '../../../../../../model/Requirement';
import { IContainer } from '../../../../../../model/IContainer';
import { TestSpecification } from '../../../../../../model/TestSpecification';
import { SpecmateDataService } from '../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../navigation/modules/navigator/services/navigator.service';
import { Sort } from '../../../../../../util/sort';
import { Url } from '../../../../../../util/url';
import { Type } from '../../../../../../util/type';
import { TestProcedure } from '../../../../../../model/TestProcedure';
import { CEGModel } from '../../../../../../model/CEGModel';
import { Process } from '../../../../../../model/Process';
import { AuthenticationService } from '../../../../main/authentication/modules/auth/services/authentication.service';

@Injectable()
export class AdditionalInformationService {

    public element: IContainer;
    private parents: IContainer[];
    private _testSpecifications: TestSpecification[];

    constructor(private dataService: SpecmateDataService, navigator: NavigatorService, private auth: AuthenticationService) {
        navigator.hasNavigated.subscribe((element: IContainer) => {
            this.element = element;
            this.load();
        });
        auth.authChanged.subscribe(() => this.reset());
    }

    private reset(): void {
        this.element = undefined;
        this.parents = undefined;
        this._testSpecifications = undefined;
    }

    private async load(): Promise<void> {
        await this.loadParents();
        await this.loadTestSpecifications();
    }

    private async loadTestSpecifications(): Promise<void> {
        if (!this.canHaveTestSpecifications || !this.requirement) {
            return;
        }
        let baseUrl = '';
        if (this.isModel(this.element)) {
            baseUrl = this.element.url;
        } else {
            baseUrl = this.requirement.url;
        }

        const testSpecifications = await this.dataService.performQuery(baseUrl, 'listRecursive', { class: TestSpecification.className });
        this._testSpecifications = Sort.sortArray(testSpecifications);
    }

    private async loadParents(): Promise<void> {
        if (!this.auth.isAuthenticated) {
            return;
        }
        let parentUrls: string[] = [];
        let url: string = Url.parent(this.element.url);

        while (!Url.isRoot(url, this.auth.token.project)) {
            parentUrls.push(url);
            url = Url.parent(url);
        }

        this.parents = await Promise.all(parentUrls.map(url => this.dataService.readElement(url)));
    }

    public get hasAdditionalInformation(): boolean {
        return this.requirement !== undefined || this.model !== undefined || this.testSpecification !== undefined;
    }

    public get model(): IContainer {
        if (!this.parents) {
            return undefined;
        }
        return this.parents.find((element: IContainer) => this.isModel(element));
    }

    public get requirement(): Requirement {
        if (!this.parents) {
            return undefined;
        }
        return this.parents.find((element: IContainer) => Type.is(element, Requirement)) as Requirement;
    }

    public get testSpecification(): TestSpecification {
        if (!this.parents) {
            return undefined;
        }
        return this.parents.find((element: IContainer) => Type.is(element, TestSpecification));
    }

    public get testSpecifications(): TestSpecification[] {
        return this._testSpecifications;
    }

    public get canHaveTestSpecifications(): boolean {
        return Type.is(this.element, Requirement) || this.isModel(this.element);
    }

    public get canGenerateTestSpecifications(): boolean {
        return this.element && ((this.isModel(this.element) && this.requirement !== undefined) || Type.is(this.element, Requirement));
    }

    public get canAddTestSpecifications(): boolean {
        return Type.is(this.element, Requirement);
    }

    public get canExportTestprocedure(): boolean {
        return Type.is(this.element, TestProcedure);
    }

    public get canExportTestspecification(): boolean {
        return Type.is(this.element, TestSpecification);
    }

    public get canGenerateCEGModel(): boolean {
        return Type.is(this.element, CEGModel);
    }

    private isModel(element: IContainer): boolean {
        return Type.is(element, CEGModel) || Type.is(element, Process);
    }
}
