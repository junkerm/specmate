import { Requirement } from '../model/Requirement';
import { IContainer } from '../model/IContainer';

abstract class Comparer {
    public abstract canCompare(element1: IContainer, element2: IContainer): boolean;
    public compare(element1: IContainer, element2: IContainer): number {
        if(this.canCompare(element1, element2)) {
            return this.compareElements(element1, element2);
        }
        throw new Error('Cannot compare elements!');
    }

    protected abstract compareElements(element1: IContainer, element2: IContainer): number;
}

class FieldComparer extends Comparer {
    constructor(protected sortBy: string) {
        super();
    }

    public canCompare(element1: IContainer, element2: IContainer): boolean {
        return element1[this.sortBy] && element2[this.sortBy];
    }

    protected compareElements(element1: IContainer, element2: IContainer): number {
        return element1[this.sortBy].localeCompare(element2[this.sortBy]);
    }
}

interface IContainerClassBuilder<T extends IContainer> {
    className: string;
}

class ClassAwareComparer<T extends IContainer> extends FieldComparer {
    private classToCompare: IContainerClassBuilder<T>;

    constructor(classToCompare: IContainerClassBuilder<T>, sortBy: string) {
        super(sortBy);
        this.classToCompare = classToCompare;
    }

    public canCompare(element1: T, element2: T): boolean {
        return super.canCompare(element1, element2) && element1.className === this.classToCompare.className && element2.className === this.classToCompare.className;
    }

    private get className(): string {
        return this.classToCompare.className;
    }
}

export class Sort {

    private static comparers: Comparer[] = [
        new ClassAwareComparer(Requirement, 'extId'),
        new FieldComparer('name'),
        new FieldComparer('url'),
        new FieldComparer('id')
    ]

    private static compareElements(element1: IContainer, element2: IContainer): number {
        let comparer: Comparer = Sort.comparers.find((comparer: Comparer) => comparer.canCompare(element1, element2));
        if(comparer) {
            return comparer.compare(element1, element2);
        }
        throw new Error('Could not find comparer!');
    }

    public static sortArray(elements: IContainer[]): IContainer[] {
        return elements.sort((e1: IContainer, e2: IContainer) => Sort.compareElements(e1, e2));
    }

    public static insert(element: IContainer, elements: IContainer[]): void {
        if(elements.indexOf(element) >= 0) {
            return;
        }
        let index: number = elements.findIndex((containedElement: IContainer) => containedElement.className === element.className && Sort.compareElements(containedElement, element) > 0);
        if(index >= elements.length || index < 0) {
            elements.push(element);
            return;
        }
        elements.splice(index, 0, element);
    }
}
