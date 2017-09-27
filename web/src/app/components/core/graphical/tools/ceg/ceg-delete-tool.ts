import { DeleteToolBase } from "../delete-tool-base";
import { IContainer } from "../../../../../model/IContainer";
import { CEGModel } from "../../../../../model/CEGModel";

export class CEGDeleteTool extends DeleteToolBase {
    protected modelType: { className: string; } = CEGModel;
}