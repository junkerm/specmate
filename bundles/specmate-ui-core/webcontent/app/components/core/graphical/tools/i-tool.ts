import { ISpecmateModelObject } from "../../../../model/ISpecmateModelObject";

export interface ITool<T extends ISpecmateModelObject> {
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