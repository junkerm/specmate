import { CEGNode } from '../../../../model/CEGNode';
import { CEGConnection } from '../../../../model/CEGConnection';
import { IContainer } from "../../../../model/IContainer";

export interface ITool {
    name: string;
    icon: string;
    color: string;

    selectedElements: (CEGNode | CEGConnection)[];

    activate(): void;
    deactivate(): void;

    click(event: MouseEvent): void;
    select(element: CEGNode | CEGConnection): void;
}