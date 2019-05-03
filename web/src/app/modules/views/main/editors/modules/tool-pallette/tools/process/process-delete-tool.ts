import { Process } from '../../../../../../../../model/Process';
import { DeleteToolBase } from '../delete-tool-base';

export class ProcessDeleteTool extends DeleteToolBase {
    protected modelType: { className: string; } = Process;
}
