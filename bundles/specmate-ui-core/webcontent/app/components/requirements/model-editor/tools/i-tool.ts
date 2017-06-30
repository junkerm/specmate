import { IContainer } from "../../../../model/IContainer";

export interface ITool<T extends IContainer> {
    name: string;
    icon: string;
    color: string;
    cursor: string;

    done: boolean;

    selectedElements: T[];

    activate(): void;
    deactivate(): void;

    click(event: MouseEvent): Promise<void>;
    select(element: T): Promise<void>;
}