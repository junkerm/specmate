import { DeleteToolBase } from "../delete-tool-base";
import { IContainer } from "../../../../../model/IContainer";
import { CEGModel } from "../../../../../model/CEGModel";
import { Process } from "../../../../../model/Process";

export class ProcessDeleteTool extends DeleteToolBase {
    protected modelType: { className: string; } = Process;
}