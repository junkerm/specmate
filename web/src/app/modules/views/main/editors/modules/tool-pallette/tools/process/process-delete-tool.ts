import { DeleteToolBase } from '../delete-tool-base';
import { Process } from '../../../../../../../../model/Process';

export class ProcessDeleteTool extends DeleteToolBase {
    protected modelType: { className: string; } = Process;
}
