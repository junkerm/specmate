import { ISpecmateModelObject } from "../../../../model/ISpecmateModelObject";
import { IContainer } from "../../../../model/IContainer";

export interface ITool {
    name: string;
    icon: string;
    color: string;
    cursor: string;

    done: boolean;

    selectedElements: IContainer[];

    activate(): void;
    deactivate(): void;

    click(event: MouseEvent, zoom: number): Promise<void>;
    select(element: IContainer): Promise<void>;
}