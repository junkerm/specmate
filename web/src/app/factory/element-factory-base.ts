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

    protected getContentsOfType(type: { className: string }): IContainer[] {
        return this.contents.filter((element: IContainer) => Type.is(element, type));
    }

    public static getDateStr(): string {
        const now = new Date();
        const year = ElementFactoryBase.fillPreceedingZeros(now.getFullYear(), 4);
        const month = ElementFactoryBase.fillPreceedingZeros(now.getMonth() + 1, 2);
        const day = ElementFactoryBase.fillPreceedingZeros(now.getDate(), 2);
        const hour = ElementFactoryBase.fillPreceedingZeros(now.getHours(), 2);
        const minute = ElementFactoryBase.fillPreceedingZeros(now.getMinutes(), 2);
        const seconds = ElementFactoryBase.fillPreceedingZeros(now.getSeconds(), 2);

        return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + seconds;
    }

    private static fillPreceedingZeros(num: number, desiredLength: number): string {
        let numStr = num + '';
        while (desiredLength > numStr.length) {
            numStr = '0' + numStr;
        }
        return numStr;
    }
}
