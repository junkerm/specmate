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

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService, private auth: AuthenticationService) {
        this.informationLoaded = new EventEmitter<void>();
        navigator.hasNavigated.subscribe((element: IContainer) => {
            this.element = element;
            this.loadParents()
                .then(() => this.loadTestSpecifications())
                .then(() => this.informationLoaded.emit());
        });
    }

    private loadTestSpecifications(): Promise<void> {
        if (!this.canHaveTestSpecifications || !this.requirement) {
            return Promise.resolve();
        }
        let baseUrl = '';
        if (this.isModel(this.element)) {
            baseUrl = this.element.url;
        } else {
            baseUrl = this.requirement.url;
        }
        return this.dataService.performQuery(baseUrl, 'listRecursive', { class: TestSpecification.className })
            .then((testSpecifications: TestSpecification[]) => this._testSpecifications = Sort.sortArray(testSpecifications))
            .then(() => Promise.resolve());
    }

    private loadParents(): Promise<void> {
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
        let readParentsTask: Promise<number> = Promise.resolve(0);
        this.parents = [];
        for (let i = 0; i < parentUrls.length; i++) {
            let currentUrl: string = parentUrls[i];
            readParentsTask = readParentsTask.then(() => {
                return this.dataService.readElement(currentUrl)
                    .then((element: IContainer) => this.parents.push(element));
            });
        }
        return readParentsTask.then(() => Promise.resolve());
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
        return this.element && this.isModel(this.element);
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

    private isModel(element: IContainer): boolean {
        return Type.is(element, CEGModel) || Type.is(element, Process);
    }
}
