import { IContainer } from '../model/IContainer';
import { SpecmateDataService } from '../modules/data/modules/data-service/services/specmate-data.service';
import { Type } from '../util/type';

export abstract class ElementFactoryBase<T extends IContainer> {

    protected contents: IContainer[];

    constructor(protected dataService: SpecmateDataService) { }

    public abstract create(parent: IContainer, commit: boolean, compoundId?: string): Promise<T>;

    protected loadContents(parent: IContainer): Promise<IContainer[]> {
        return this.dataService.readContents(parent.url, true).then((contents: IContainer[]) => this.contents = contents);
    }

    protected getContentsOfType(type: {className: string}): IContainer[] {
        return this.contents.filter((element: IContainer) => Type.is(element, type));
    }
}