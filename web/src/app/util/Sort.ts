import { IContainer } from '../model/IContainer';
import { Requirement } from '../model/Requirement';

abstract class Comparer {

    public static isNumeric(value: string | number): boolean {
        return !isNaN(+value);
    }
    public abstract canCompare(element1: IContainer, element2: IContainer): boolean;

    public compare(element1: IContainer, element2: IContainer): number {
        if (this.canCompare(element1, element2)) {
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
        return element1[this.sortBy] !== undefined && element2[this.sortBy] !== undefined;
    }

    protected compareElements(element1: IContainer, element2: IContainer): number {
        if (typeof element1[this.sortBy] === 'number' && typeof element2[this.sortBy] === 'number') {
            return element1[this.sortBy] - element2[this.sortBy];
        }
        if (Comparer.isNumeric(element1[this.sortBy]) && Comparer.isNumeric(element2[this.sortBy])) {
            return parseInt(element1[this.sortBy], 10) - parseInt(element2[this.sortBy], 10);
        }
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
        return super.canCompare(element1, element2) &&
            element1.className === this.classToCompare.className &&
            element2.className === this.classToCompare.className;
    }

    private get className(): string {
        return this.classToCompare.className;
    }
}

export class Sort {

    private static comparers: Comparer[] = [
        new ClassAwareComparer(Requirement, 'extId'),
        new FieldComparer('position'),
        new FieldComparer('name'),
        new FieldComparer('url'),
        new FieldComparer('id')
    ]

    private static compareElements<T extends IContainer>(element1: T, element2: T): number {
        let comparer: Comparer = Sort.comparers.find((comp: Comparer) => comp.canCompare(element1, element2));
        if (comparer) {
            return comparer.compare(element1, element2);
        }
        throw new Error('Could not find comparer!');
    }

    public static sortArray<T extends IContainer>(elements: T[]): T[] {
        if (!elements) {
            return elements;
        }
        return elements.sort((e1: T, e2: T) => Sort.compareElements(e1, e2));
    }

    public static sortArrayInPlace<T extends IContainer>(array: T[]): void {
        if (!array) {
            return;
        }
        let sorted: T[] = Sort.sortArray(array);
        sorted.forEach((element: T, index: number) => {
            if (array[index] !== element) {
                array[index] = element;
            }
        });
    }

    public static insert<T extends IContainer>(element: T, elements: T[]): void {
        if (elements.indexOf(element) >= 0) {
            return;
        }
        let index: number = elements
            .findIndex((containedElement: T) =>
                containedElement.className === element.className &&
                Sort.compareElements(containedElement, element) > 0);

        if (index >= elements.length || index < 0) {
            elements.push(element);
            return;
        }
        elements.splice(index, 0, element);
    }
}
