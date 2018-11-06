import {ProcessStep} from '../../../../../../model/ProcessStep';
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
import { UserToken } from '../../../../main/authentication/base/user-token';

@Injectable()
export class AdditionalInformationService {

    public element: IContainer;
    private parents: IContainer[];
    private _testSpecifications: TestSpecification[];

    public informationLoaded: EventEmitter<void>;

    constructor(private dataService: SpecmateDataService, navigator: NavigatorService, private auth: AuthenticationService) {
        this.informationLoaded = new EventEmitter<void>();
        navigator.hasNavigated.subscribe((element: IContainer) => {
            this.element = element;
            this.loadParents()
                .then(() => this.loadTestSpecifications())
                .then(() => this.informationLoaded.emit());
        });
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
            return Promise.resolve();
        }
        let parentUrls: string[] = [];
        let url: string = this.element.url;
        url = Url.parent(url);

        while (!Url.isRoot(url, this.auth.token.project)) {
            parentUrls.push(url);
            url = Url.parent(url);
        }

        this.parents = [];
        for (let i = 0; i < parentUrls.length; i++) {
            let currentUrl: string = parentUrls[i];
            const element = await this.dataService.readElement(currentUrl);
            this.parents.push(element);
        }
    }

    public get hasAdditionalInformation(): boolean {
        return this.requirement !== undefined || this.model !== undefined || this.testSpecification !== undefined;
    }

    public get model(): IContainer {
        if (!this.parents) {
            return undefined;
        }
        const parentModel = this.parents.find((element: IContainer) => this.isModel(element));
        if (parentModel !== undefined) {
            return parentModel;
        }
        if (this.isModel(this.element)) {
            return this.element;
        }
        return undefined;
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
        return this.element && this.isModel(this.element);
    }

    public get canAddTestSpecifications(): boolean {
        return Type.is(this.element, Requirement);
    }

    public get canExportTestprocedure(): boolean {
        return Type.is(this.element, TestProcedure);
    }

    private isModel(element: IContainer): boolean {
        return Type.is(element, CEGModel) || Type.is(element, Process);
    }
}
