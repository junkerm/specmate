import { Component } from "@angular/core";
import { NavigatorService } from "../../../services/navigation/navigator.service";
import { IContainer } from "../../../model/IContainer";
import { Type } from "../../../util/Type";
import { Requirement } from "../../../model/Requirement";
import { TestSpecification } from "../../../model/TestSpecification";
import { CEGModel } from "../../../model/CEGModel";
import { Process } from "../../../model/Process";
import { SpecmateDataService } from "../../../services/data/specmate-data.service";
import { Url } from "../../../util/Url";
import { IConnection } from "../../../model/IConnection";
import { Sort } from "../../../util/Sort";
import { TestProcedure } from "../../../model/TestProcedure";


@Component({
    moduleId: module.id,
    selector: 'links-actions',
    templateUrl: 'links-actions.component.html',
    styleUrls: ['links-actions.component.css']
})
export class LinksActions {

    private _requirement: Requirement;
    private _model: IContainer;
    private _contents: IContainer[];
    private element: IContainer;
    private parents: IContainer[];
    private _testSpecifications: TestSpecification[];

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) {
        navigator.hasNavigated.subscribe((element: IContainer) => {
            this.element = element;
            this.loadParents()
                .then(() => this.loadTestSpecifications());
        });
    }

    private loadTestSpecifications(): Promise<void> {
        if(!this.canHaveTestSpecifications) {
            return Promise.resolve();
        }
        return this.dataService.performQuery(this.requirement.url, 'listRecursive', { class: TestSpecification.className })
            .then((testSpecifications: TestSpecification[]) => this._testSpecifications = Sort.sortArray(testSpecifications))
            .then(() => Promise.resolve());
    }

    private loadParents(): Promise<void> {
        let parentUrls: string[] = [];
        let url: string = this.element.url;
        while(!Url.isRoot(url)) {
            url = Url.parent(url);
            parentUrls.push(url);
        }
        let readParentsTask: Promise<number> = Promise.resolve(0);
        this.parents = [];
        for(let i = 0; i < parentUrls.length; i++) {
            let currentUrl: string = parentUrls[i];
            readParentsTask = readParentsTask.then(() => {
                return this.dataService.readElement(currentUrl)
                    .then((element: IContainer) => this.parents.push(element));
            });
        }
        return readParentsTask.then(() => Promise.resolve());
    }

    private clear(): void {
        this._model = undefined;
        this._requirement = undefined;
        this._contents = undefined;
    }

    public get model(): IContainer {
        if(!this.parents) {
            return undefined;
        }
        return this.parents.find((element: IContainer) => Type.is(element, CEGModel) || Type.is(element, Process));
    }

    public get requirement(): Requirement {
        if(!this.parents) {
            return undefined;
        }
        return this.parents.find((element: IContainer) => Type.is(element, Requirement)) as Requirement;
    }

    public get testSpecification(): TestSpecification {
        if(!this.parents) {
            return undefined;
        }
        return this.parents.find((element: IContainer) => Type.is(element, TestSpecification));
    }

    public get testSpecifications(): TestSpecification[] {
        return this._testSpecifications;
    }

    public get canHaveTestSpecifications(): boolean {
        return Type.is(this.element, Requirement) || Type.is(this.element, CEGModel) || Type.is(this.element, Process);
    }

    public get canGenerateTestSpecifications(): boolean {
        return this.element && (Type.is(this.element, CEGModel) || Type.is(this.element, Process));
    }

    public get canAddTestSpecifications(): boolean {
        return Type.is(this.element, Requirement);
    }

    public get canExportToALM(): boolean {
        return Type.is(this.element, TestProcedure);
    }
}
