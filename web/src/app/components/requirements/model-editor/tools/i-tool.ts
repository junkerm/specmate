import { IContainer } from "../../../../model/IContainer";

export interface ITool<T extends IContainer> {
    name: string;
    icon: string;
    color: string;

    selectedElements: T[];

    activate(): void;
    deactivate(): void;

    click(event: MouseEvent): void;
    select(element: T): void;
}