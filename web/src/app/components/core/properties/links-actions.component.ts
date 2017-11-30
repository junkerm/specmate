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

    constructor(private dataService: SpecmateDataService, private navigator: NavigatorService) {
        navigator.hasNavigated.subscribe((element: IContainer) => {
            if(Type.is(element, CEGModel) || Type.is(element, Process)) {
                this._model = element;
                this.dataService.readContents(this._model.url).then((contents: IContainer[]) => this._contents = contents);
                this.dataService.readElement(Url.parent(this._model.url)).then((element: IContainer) => this._requirement = element as Requirement);
            }
        });
    }

    public get model(): IContainer {
        return this._model;
    }

    public get requirement(): Requirement {
        return this._requirement;
    }

    public get testSpecifications(): TestSpecification[] {
        return this._contents.filter((element: IContainer) => Type.is(element, TestSpecification));
    }

}
